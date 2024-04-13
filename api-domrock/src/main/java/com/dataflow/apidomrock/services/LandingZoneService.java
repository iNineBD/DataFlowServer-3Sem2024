package com.dataflow.apidomrock.services;

import com.dataflow.apidomrock.controllers.exceptions.CustomException;
import com.dataflow.apidomrock.dto.entitiesdto.MetadataDTO;
import com.dataflow.apidomrock.dto.entitiesdto.RestricaoDTO;
import com.dataflow.apidomrock.dto.getmetadados.ResponseBodyGetMetadadosDTO;
import com.dataflow.apidomrock.dto.processuploadcsv.ResponseUploadCSVDTO;
import com.dataflow.apidomrock.dto.updatemetados.RequestBodyUpdateMetaDTO;
import com.dataflow.apidomrock.entities.database.*;
import com.dataflow.apidomrock.repository.*;
import com.dataflow.apidomrock.services.utils.ProcessFiles;
import com.dataflow.apidomrock.services.utils.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class LandingZoneService {

    @Autowired
    ArquivoRepository arquivoRepository;

    @Autowired
    MetadataRepository metadataRepository;

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    TipoRepository tipoRepository;

    @Autowired
    RestricaoRepository restricaoRepository;

    @Transactional(readOnly = false, rollbackFor = CustomException.class)
    public ResponseUploadCSVDTO processUploadCSV(MultipartFile multipartFile, String delimiter) throws IOException, CustomException {

        //realiza validacoes nos parametros da request (se o arquivo existe, está ok...)
        //Se estiver ruim, internamente é lançada uma exceção que o controller trata pelo advice
        Boolean isCSV = Validate.validateprocessUploadCSV(multipartFile, delimiter);
        if (isCSV) {
            return ProcessFiles.processCSVFile(multipartFile);
        } else {
            return ProcessFiles.processExcelFile(multipartFile);
        }
    }

    @Transactional(rollbackFor = CustomException.class)
    public ResponseBodyGetMetadadosDTO getMetadadosInDatabase(String user, String nomeArquivo) throws CustomException {
        //CONFERE SE O USUARIO QUE SUBIU O JSON JA EXISTE NA BASE
        Optional<Usuario> userBD = usuarioRepository.findById(user);
        //SE NÃO EXISTIR, ELE SOLTA ESTA "CRITICA"
        if (userBD.isEmpty()) {
            throw new CustomException("Usuário [" + user + "] não existe", HttpStatus.NOT_FOUND);
        }
        //CONFERE SE O ARQUIVO QUE SUBIU O JSON JA EXISTE NA BASE
        Optional<Arquivo> arqBD = arquivoRepository.findByNameAndOrganization(nomeArquivo, userBD.get().getOrganizacao().getNome());
        if (arqBD.isEmpty()) {
            //SE NÃO EXISTIR, ELE SOLTA ESTA "CRITICA"
            throw new CustomException("Arquivo [" + nomeArquivo + "] não encontrado para a organização [" + userBD.get().getOrganizacao().getNome() + "]", HttpStatus.NOT_FOUND);
        }

        List<MetadataDTO> temp = new ArrayList<>();
        for (Metadata metadata : arqBD.get().getMetadados()) {
            temp.add(new MetadataDTO(metadata));
        }
        return new ResponseBodyGetMetadadosDTO(user, nomeArquivo, temp);
    }

    @Transactional(rollbackFor = CustomException.class)
    public void updateMetadadosInDatabase(RequestBodyUpdateMetaDTO request) throws CustomException {

        //CONFERE SE O USUARIO QUE SUBIU O JSON JA EXISTE NA BASE
        Optional<Usuario> userBD = usuarioRepository.findById(request.getUsuario());

        //SE NÃO EXISTIR, ELE SOLTA ESTA "CRITICA"
        if (userBD.isEmpty()) {
            throw new CustomException("Usuário [" + request.getUsuario() + "] não existe", HttpStatus.NOT_FOUND);
        }

        //CONFERE SE O ARQUIVO QUE SUBIU O JSON JA EXISTE NA BASE
        Optional<Arquivo> arqBD = arquivoRepository.findByNameAndOrganization(request.getNomeArquivo(), userBD.get().getOrganizacao().getNome());
        if (arqBD.isEmpty()) {
            Arquivo arquivo = new Arquivo();
            arquivo.setNomeArquivo(request.getNomeArquivo());
            arquivo.setStatus(new Status(2, "AGUARDANDO APROVAÇÃO DA BRONZE"));
            arquivo.setUsuario(userBD.get());
            arquivo.setOrganizacao(userBD.get().getOrganizacao());

            arquivoRepository.save(arquivo);
            arqBD = arquivoRepository.findByNameAndOrganization(request.getNomeArquivo(), userBD.get().getOrganizacao().getNome());
        }
        //TODO METADADO "CAPTURADO" PELO JSON, ELE JOGA AS INFORMAÇÕES NO OBJETO METADADO
        for (MetadataDTO metadadoJson : request.getMetadados()) {
            Optional<Metadata> metaBD = metadataRepository.findByNameAndFile(arqBD.get().getId(), metadadoJson.getNome());
            Metadata newMetadado = new Metadata();

            // SE O METADADO JA EXISTIR, ELE PEGA O VALOR ANTIGO, DELETA E INSERE O NOVO
            if (metaBD.isPresent()) {
                newMetadado.setID(metaBD.get().getID());
                restricaoRepository.deleteAll(metaBD.get().getRestricoes());
            }

            //SE O METADADO NÃO EXISTIR, ELE PEGA TODOS OS METADADOS CAPTURADOS E INSERE NA BASE
            newMetadado.setNome(metadadoJson.getNome());
            newMetadado.setArquivo(arqBD.get());
            newMetadado.setAtivo(metadadoJson.getAtivo());
            if (!metadadoJson.getAtivo()) {
                metadataRepository.save(newMetadado);
                continue;
            }
            newMetadado.setDescricao(metadadoJson.getDescricao());
            newMetadado.setValorPadrao(metadadoJson.getValorPadrao());

            // SE O CAMPO "TIPO" DO METADADO FOR NULO, ELE ESTOURA ESTA "CRITICA"
            if (metadadoJson.getNomeTipo() == null || metadadoJson.getNomeTipo().isEmpty()){
                throw new CustomException("O tipo do metadado ["+metadadoJson.getNome()+"] não pode ser nulo", HttpStatus.BAD_REQUEST);
            }
            Optional<Tipo> tipoDB = tipoRepository.findById(metadadoJson.getNomeTipo());

            // SE O TIPO DO METADADO FOR DIFERENTE DOS JA EXISTENTES NA BASE, ELE ESTOUES ESTA "CRITICA"
            if (tipoDB.isEmpty()) {
                throw new CustomException("O tipo " + metadadoJson.getNomeTipo() + " não existe", HttpStatus.NOT_FOUND);
            }

            newMetadado.setNomeTipo(tipoDB.get());
            List<Restricao> newRestricoes = new ArrayList<>();

            //SE O METADADO FOR DO TIPO BOOLENAO, DATA, HORA, DATA E HORA, A COLUNA TAMANHO MAXIMO NÃO DEVERA SER PREENCHIDA
            for (RestricaoDTO restricaoJson : metadadoJson.getRestricoes()) {
                if (metadadoJson.getNomeTipo().equals("Hora") || metadadoJson.getNomeTipo().equals("Data") || metadadoJson.getNomeTipo().equals("Data e Hora")  || metadadoJson.getNomeTipo().equals("Booleano") || metadadoJson.getNomeTipo().equals("Decimal") || metadadoJson.getNomeTipo().equals("Inteiro")){
                    if (restricaoJson.getNome().equals("tamanhoMaximo")) {
                        continue;
                    }
                }
                if (restricaoJson.getNome().equals("tamanhoMaximo") && !Validate.isInteger(restricaoJson.getValor())){
                    throw new CustomException("O campo \"Tamanho Máximo\" do metadado "+ metadadoJson.getNome() + " precisa ser um número inteiro", HttpStatus.BAD_REQUEST);
                }
                // SE A RESTRIÇÃO ESTIVER VAZIA, O PROGRAMA CONTINUA
                if (restricaoJson.getValor() == null || restricaoJson.getValor().isEmpty()){
                    continue;
                }
                Restricao newRestricao = new Restricao();
                // INSERINDO AS INFORMAÇÕES DO JSON NOS ATRIBUTOS REQUERIDOS PARA RESTRIÇÃO
                newRestricao.setNome(restricaoJson.getNome());
                newRestricao.setValor(restricaoJson.getValor());
                newRestricoes.add(newRestricao);
            }
            //ADICIONANDO AS RESTRIÇOES NO METADADO
            newMetadado.setRestricoes(newRestricoes);
            metadataRepository.save(newMetadado);
        }
    }
}
