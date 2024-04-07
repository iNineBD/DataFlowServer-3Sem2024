package com.dataflow.apidomrock.services;

import com.dataflow.apidomrock.dto.entitiesDTO.MetadataDTO;
import com.dataflow.apidomrock.dto.entitiesDTO.RestricaoDTO;
import com.dataflow.apidomrock.dto.getMetadados.ResponseBodyGetMetadadosDTO;
import com.dataflow.apidomrock.dto.processUploadCSV.UploadCSVResponseDTO;
import com.dataflow.apidomrock.dto.saveMetadado.RequestBodySaveDTO;
import com.dataflow.apidomrock.dto.saveMetadado.RequestBodySaveMetadadoDTO;
import com.dataflow.apidomrock.dto.updateMetados.RequestBodyUpdateMetaDTO;
import com.dataflow.apidomrock.entities.database.*;
import com.dataflow.apidomrock.repository.*;
import com.dataflow.apidomrock.services.utils.ValidateRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
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

    @Transactional(readOnly = false)
    public UploadCSVResponseDTO processUploadCSV(MultipartFile multipartFile, String delimiter) throws IOException {


        //realiza validacoes nos parametros da request (se o arquivo existe, está ok...)
        //Se estiver ruim, internamente é lançada uma exceção que o controller trata pelo advice
        multipartFile = ValidateRequest.validateprocessUploadCSV(multipartFile, delimiter);

        //lendo o arquivo
        BufferedReader rd = new BufferedReader(new InputStreamReader(multipartFile.getInputStream()));

        //pego o cabeçalho (nome das colunas)
        String line = rd.readLine().trim();

        //isso foi feito para minimizar problemas do tipo: CSV não integro
        while (line.endsWith(";")) {
            line = line.substring(0, line.length() - 1);
        }
        String[] headers;
        if (line.contains(",")) {
            //divido o nome das colunas pelo delimitador especificado
            headers = line.split(",");
        } else {
            headers = line.split(";");
        }

        List<Metadata> metadatas = new ArrayList<>();
        //para cada coluna, crio o Metadado equivalente e ja adiciono numa lista
        for (String columName : headers) {
            metadatas.add(new Metadata(null, columName, null, null, null, null, null, null));
        }

        double fileSize = (double) multipartFile.getSize() / (1024 * 1024);

        return new UploadCSVResponseDTO(multipartFile.getOriginalFilename(), fileSize, metadatas);
    }

    @Transactional
    public ResponseBodyGetMetadadosDTO getMetadadosInDatabase(String user, String nomeArquivo) {
        Optional<Usuario> userBD = usuarioRepository.findById(user);
        if (userBD.isEmpty()) {
            throw new RuntimeException("Usuário [" + user + "] não existe");
        }

        Optional<Arquivo> arqBD = arquivoRepository.findByNameAndOrganization(nomeArquivo, userBD.get().getOrganizacao().getNome());
        if (arqBD.isEmpty()) {
            throw new RuntimeException("Arquivo [" + nomeArquivo + "] não encontrado para a organização [" + userBD.get().getOrganizacao().getNome() + "]");
        }

        List<MetadataDTO> temp = new ArrayList<>();
        for (Metadata metadata : arqBD.get().getMetadados()) {
            temp.add(new MetadataDTO(metadata));
        }
        return new ResponseBodyGetMetadadosDTO(user, nomeArquivo, temp);
    }

    @Transactional
    public void updateMetadadosInDatabase(RequestBodyUpdateMetaDTO request) {
        Optional<Usuario> userBD = usuarioRepository.findById(request.getUsuario());
        if (userBD.isEmpty()) {
            throw new RuntimeException("Usuário [" + request.getUsuario() + "] não existe");
        }

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

        for (MetadataDTO metadadoJson : request.getMetadados()) {
            Optional<Metadata> metaBD = metadataRepository.findByNameAndFile(arqBD.get().getId(), metadadoJson.getNome());
            Metadata newMetadado = new Metadata();
            if (metaBD.isPresent()) {
                newMetadado.setID(metaBD.get().getID());
                restricaoRepository.deleteAll(metaBD.get().getRestricoes());
            }
            newMetadado.setNome(metadadoJson.getNome());
            newMetadado.setArquivo(arqBD.get());
            newMetadado.setAtivo(metadadoJson.getAtivo());
            if (!metadadoJson.getAtivo()) {
                metadataRepository.save(newMetadado);
                continue;
            }

            newMetadado.setDescricao(metadadoJson.getDescricao());
            newMetadado.setValorPadrao(metadadoJson.getValorPadrao());

            Optional<Tipo> tipoDB = tipoRepository.findById(metadadoJson.getNomeTipo());
            if (tipoDB.isEmpty()) {
                throw new RuntimeException("O tipo " + metadadoJson.getNomeTipo() + " não existe");
            }

            newMetadado.setNomeTipo(tipoDB.get());

            List<Restricao> newRestricoes = new ArrayList<>();

            for (RestricaoDTO restricaoJson : metadadoJson.getRestricoes()) {
                if (metadadoJson.getNomeTipo().equals("Hora") || metadadoJson.getNomeTipo().equals("Data") || metadadoJson.getNomeTipo().equals("Data e Hora")  || metadadoJson.getNomeTipo().equals("Booleano")){
                    if (restricaoJson.getNome().equals("tamanhoMaximo")) {
                        continue;
                    }
                }
                Restricao newRestricao = new Restricao();
                newRestricao.setNome(restricaoJson.getNome());
                newRestricao.setValor(restricaoJson.getValor());
                newRestricoes.add(newRestricao);
            }

            newMetadado.setRestricoes(newRestricoes);
            metadataRepository.save(newMetadado);
        }
    }
}
