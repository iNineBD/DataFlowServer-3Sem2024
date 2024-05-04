package com.dataflow.apidomrock.services;

import com.dataflow.apidomrock.controllers.exceptions.CustomException;
import com.dataflow.apidomrock.dto.entitiesdto.MetadataDTO;
import com.dataflow.apidomrock.dto.entitiesdto.RestricaoDTO;
import com.dataflow.apidomrock.dto.getmetadados.ResponseBodyGetMetadadosDTO;
import com.dataflow.apidomrock.dto.processuploadcsv.ResponseUploadCSVDTO;
import com.dataflow.apidomrock.dto.updatemetados.RequestBodyUpdateMetaDTO;
import com.dataflow.apidomrock.entities.database.*;
import com.dataflow.apidomrock.entities.enums.Acao;
import com.dataflow.apidomrock.entities.enums.Estagio;
import com.dataflow.apidomrock.entities.enums.NivelAcessoEnum;
import com.dataflow.apidomrock.entities.enums.StatusArquivo;
import com.dataflow.apidomrock.repository.*;
import com.dataflow.apidomrock.services.utils.Logger;
import com.dataflow.apidomrock.services.utils.ProcessFiles;
import com.dataflow.apidomrock.services.utils.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
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
    RestricaoRepository restricaoRepository;

    @Autowired
    Logger logger;

    @Transactional(readOnly = false, rollbackFor = CustomException.class)
    public ResponseUploadCSVDTO processUploadCSV(MultipartFile multipartFile, String delimiter, boolean header, String email) throws IOException, CustomException {

        //realiza validacoes nos parametros da request (se o arquivo existe, está ok...)
        //Se estiver ruim, internamente é lançada uma exceção que o controller trata pelo advice

        Optional<Usuario> userBD =  usuarioRepository.findByEmailCustom(email);
        if (userBD.isEmpty()) {
            throw new CustomException("O usuário ["+email+"] não existe.", HttpStatus.UNAUTHORIZED);
        }

        boolean havePermision = false;
        for (NivelAcesso nvl : userBD.get().getNiveisAcesso()){
            if (nvl.getNivel().equals(NivelAcessoEnum.LZ.toString()) || nvl.getNivel().equals(NivelAcessoEnum.MASTER.toString()) || nvl.getNivel().equals(NivelAcessoEnum.FULL.toString())){havePermision = true;}
        }

        if (!havePermision) {
            throw new CustomException("O usuário ["+email+"] não tem permissão para realizar ação", HttpStatus.UNAUTHORIZED);
        }
        Boolean isCSV = Validate.validateprocessUploadCSV(multipartFile, delimiter);
        if (isCSV) {
            if (header){
                return ProcessFiles.processCSVFileWithHeader(multipartFile, delimiter, header);
            }
            return ProcessFiles.processCSVFileNotHeader(multipartFile, delimiter, header);
        } else {
            if (header){
                return ProcessFiles.processExcelFileWithHeader(multipartFile);
            }
            return ProcessFiles.processExcelFileWithOutHeader(multipartFile);
        }
    }

    @Transactional(rollbackFor = CustomException.class)
    public void updateMetadadosInDatabase(RequestBodyUpdateMetaDTO request) throws CustomException {

        //CONFERE SE O USUARIO QUE SUBIU O JSON JA EXISTE NA BASE
        Optional<Usuario> userBD = usuarioRepository.findByEmailCustom(request.getUsuario());

        //SE NÃO EXISTIR, ELE SOLTA ESTA "CRITICA"
        if (userBD.isEmpty()) {
            throw new CustomException("Usuário [" + request.getUsuario() + "] não existe", HttpStatus.NOT_FOUND);
        }

        //CONFERE SE O ARQUIVO QUE SUBIU O JSON JA EXISTE NA BASE
        Optional<Arquivo> arqBD = arquivoRepository.findByNameAndOrganization(request.getNomeArquivo(), userBD.get().getOrganizacao().getCnpj());
        if (arqBD.isEmpty()) {
            Arquivo arquivo = new Arquivo();
            arquivo.setNomeArquivo(request.getNomeArquivo());
            arquivo.setStatus(StatusArquivo.AGUARDANDO_APROVACAO_BRONZE.getDescricao());
            arquivo.setOrganizacao(userBD.get().getOrganizacao());
            arquivo.setAtivo(true);
            arquivoRepository.save(arquivo);
            arqBD = arquivoRepository.findByNameAndOrganization(request.getNomeArquivo(), userBD.get().getOrganizacao().getCnpj());
            logger.insert(userBD.get().getId(), arqBD.get().getId(), "Insert file", Estagio.LZ, Acao.INSERIR);
        }
        else {
            logger.insert(userBD.get().getId(), arqBD.get().getId(), "Update file", Estagio.LZ, Acao.ALTERAR);
        }
        arqBD.get().setStatus(StatusArquivo.AGUARDANDO_APROVACAO_BRONZE.getDescricao());
        arqBD.get().setAtivo(true);
        metadataRepository.deleteAllByArquivo(arqBD.get());

        boolean todos_metados_com_vlr_padrao = true;

        HashMap<String, Boolean> nomes_colunas = new HashMap<String, Boolean>();
        //METADADO "CAPTURADO" PELO JSON, ELE JOGA AS INFORMAÇÕES NO OBJETO METADADO
        for (MetadataDTO metadadoJson : request.getMetadados()) {
            Metadata newMetadado = new Metadata();

            //SE O METADADO NÃO EXISTIR, ELE PEGA TODOS OS METADADOS CAPTURADOS E INSERE NA BASE
            newMetadado.setNome(metadadoJson.getNome());
            newMetadado.setArquivo(arqBD.get());
            newMetadado.setIsAtivo(metadadoJson.getAtivo());
            newMetadado.setExemplo(metadadoJson.getSampleValue());
            if (!metadadoJson.getAtivo()) {
                metadataRepository.save(newMetadado);
                continue;
            }
            if (nomes_colunas.get(metadadoJson.getNome()) != null){
                throw new CustomException("O nome do metadado ["+metadadoJson.getNome()+"] já está sendo usado!", HttpStatus.BAD_REQUEST);
            }
            nomes_colunas.put(metadadoJson.getNome(), Boolean.TRUE);
            newMetadado.setDescricao(metadadoJson.getDescricao());
            if (metadadoJson.getValorPadrao() == null || metadadoJson.getValorPadrao().equals("")) todos_metados_com_vlr_padrao = false;
            newMetadado.setValorPadrao(metadadoJson.getValorPadrao());

            // SE O CAMPO "TIPO" DO METADADO FOR NULO, ELE ESTOURA ESTA "CRITICA"
            if (metadadoJson.getNomeTipo() == null || metadadoJson.getNomeTipo().isEmpty()){
                throw new CustomException("O tipo do metadado ["+metadadoJson.getNome()+"] não pode ser nulo", HttpStatus.BAD_REQUEST);
            }

            if (metadadoJson.getNomeTipo().equals("Booleano") && (metadadoJson.getValorPadrao() != null && !metadadoJson.getValorPadrao().equals(""))){
                if (!metadadoJson.getValorPadrao().equalsIgnoreCase("true") && !metadadoJson.getValorPadrao().equalsIgnoreCase("false")){
                    throw new CustomException("O campo ["+metadadoJson.getNome()+"], por ser BOOLEANO, não pode ter seu Valor Padrão diferente de \"true\" e \"false\"", HttpStatus.BAD_REQUEST);
                }
            }

            newMetadado.setTipo(metadadoJson.getNomeTipo());
            List<Restricao> newRestricoes = new ArrayList<>();

            //SE O METADADO FOR DO TIPO BOOLENAO, DATA, HORA, DATA E HORA, A COLUNA TAMANHO MAXIMO NÃO DEVERA SER PREENCHIDA
            for (RestricaoDTO restricaoJson : metadadoJson.getRestricoes()) {
                if (metadadoJson.getNomeTipo().equals("Hora") || metadadoJson.getNomeTipo().equals("Data") || metadadoJson.getNomeTipo().equals("Data e Hora")  || metadadoJson.getNomeTipo().equals("Booleano") || metadadoJson.getNomeTipo().equals("Decimal") || metadadoJson.getNomeTipo().equals("Inteiro")){
                    if (restricaoJson.getNome().equals("tamanhoMaximo")) {
                        continue;
                    }
                }
                if (restricaoJson.getNome().equals("tamanhoMaximo") && !Validate.isInteger(restricaoJson.getValor())){
                    throw new CustomException("O campo [Tamanho Máximo] do metadado ["+ metadadoJson.getNome() + "] precisa ser um número inteiro", HttpStatus.BAD_REQUEST);
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

        if (todos_metados_com_vlr_padrao){
            throw new CustomException("Todos os metadados estão com \"Valor Padrão\" definidos. Isso não é permitido", HttpStatus.BAD_REQUEST);
        }
    }

    @Transactional(rollbackFor = CustomException.class)
    public ResponseBodyGetMetadadosDTO getMetadados(String user, String nomeArquivo) throws CustomException {
        //CONFERE SE O USUARIO QUE SUBIU O JSON JA EXISTE NA BASE
        Optional<Usuario> userBD = usuarioRepository.findByEmailCustom(user);
        //SE NÃO EXISTIR, ELE SOLTA ESTA "CRITICA"
        if (userBD.isEmpty()) {
            throw new CustomException("Usuário [" + user + "] não existe", HttpStatus.NOT_FOUND);
        }

        boolean havePermission = false;
        boolean isFullAndMaster = false;
        for (NivelAcesso n : userBD.get().getNiveisAcesso()){
            if (n.getNivel().equals(NivelAcessoEnum.LZ.toString()) || n.getNivel().equals(NivelAcessoEnum.MASTER.toString()) || n.getNivel().equals(NivelAcessoEnum.FULL.toString())){
                havePermission = true;
            }
            if (n.getNivel().equals(NivelAcessoEnum.MASTER.toString()) || n.getNivel().equals(NivelAcessoEnum.FULL.toString())){
                isFullAndMaster = true;
            }
        }

        if (!havePermission) {
            throw new CustomException("Você não tem permissão para acessar o recurso", HttpStatus.UNAUTHORIZED);
        }

        Optional<Arquivo> arqBD = Optional.empty();
        if (isFullAndMaster){
            List<Arquivo> temp = arquivoRepository.findAllByNomeArquivo(nomeArquivo);
            if (!temp.isEmpty()){
                arqBD = Optional.of(temp.getFirst());
            }
        } else {
            //CONFERE SE O ARQUIVO QUE SUBIU O JSON JA EXISTE NA BASE
            arqBD = arquivoRepository.findByNameAndOrganization(nomeArquivo, userBD.get().getOrganizacao().getCnpj());
        }


        if (arqBD.isEmpty()) {
            //SE NÃO EXISTIR, ELE SOLTA ESTA "CRITICA"
            throw new CustomException("Arquivo [" + nomeArquivo + "] não encontrado para a organização [" + userBD.get().getOrganizacao().getNome() + "]", HttpStatus.NOT_FOUND);
        }

        List<MetadataDTO> temp = new ArrayList<>();
        for (Metadata metadata : arqBD.get().getMetadados()) {
            temp.add(new MetadataDTO(metadata));
        }
        return new ResponseBodyGetMetadadosDTO(user, nomeArquivo, arqBD.get().getId(), temp);
    }
}
