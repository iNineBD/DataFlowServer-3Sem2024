package com.dataflow.apidomrock.services;

import com.dataflow.apidomrock.dto.getMetadados.ResponseBodyGetMetadadosDTO;
import com.dataflow.apidomrock.dto.getMetadados.ResponseBodyMetadadoDTO;
import com.dataflow.apidomrock.dto.processUploadCSV.UploadCSVResponseDTO;
import com.dataflow.apidomrock.dto.saveMetadado.RequestBodySaveDTO;
import com.dataflow.apidomrock.dto.saveMetadado.RequestBodySaveMetadadoDTO;
import com.dataflow.apidomrock.entities.database.*;
import com.dataflow.apidomrock.repository.ArquivoRepository;
import com.dataflow.apidomrock.repository.MetadataRepository;
import com.dataflow.apidomrock.repository.TipoRepository;
import com.dataflow.apidomrock.repository.UsuarioRepository;
import com.dataflow.apidomrock.services.utils.ValidateRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
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

    @Transactional(readOnly = false)
    public UploadCSVResponseDTO processUploadCSV(MultipartFile request, String delimiter) throws IOException {

        List<Metadata> metadatas = new ArrayList<>();
        MultipartFile multipartFile = request;

        //realiza validacoes nos parametros da request (se o arquivo existe, está ok...)
        //Se estiver ruim, internamente é lançada uma exceção que o controller trata pelo advice
        multipartFile = ValidateRequest.validateprocessUploadCSV(request, delimiter);

        //lendo o arquivo
        InputStream in = multipartFile.getInputStream();
        BufferedReader rd = new BufferedReader(new InputStreamReader(in));

        //pego o cabeçalho (nome das colunas)
        String line = rd.readLine().trim();

        //isso foi feito para minimizar problemas do tipo: CSV não integro
        while (line.endsWith(";")) {
            line = line.substring(0, line.length() - 1);
        }
        String[] headers;
        if(line.contains(",")){
            //divido o nome das colunas pelo delimitador especificado
            headers = line.split(",");
        } else {
            headers = line.split(";");
        }


        //para cada coluna, crio o Metadado equivalente e ja adiciono numa lista
        for (String columName : headers) {
            metadatas.add(new Metadata(null, columName, null, null, null, null, null, null));
        }

        if (multipartFile == null) {
            throw new RuntimeException("Erro ao interpretar o arquivo inserido");
        }

        double fileSize = (double) multipartFile.getSize() / (1024 * 1024);

        return new UploadCSVResponseDTO(multipartFile.getOriginalFilename(), fileSize, metadatas);
    }
    @Transactional
    public void saveMetadadosInDataBase (RequestBodySaveDTO requestBodySaveDTO){

        // recebendo o nome do arquivo referente ao json enviado
        String nomeArquivo = requestBodySaveDTO.getNomeArquivo();

        //recebendo os dados do usuario apartir do email enviado no json
        String usuarioEmail = requestBodySaveDTO.getUsuario();

        //confere se o usuario que subiu o json ja existe no banco de dados
        Optional<Usuario> usuarioBD = usuarioRepository.findById(usuarioEmail);
        if (usuarioBD.isEmpty()){
            //caso não exista, o ele retorna esta "critica"
            throw new RuntimeException("Não foi encontrado nenhum usuario");
        }

        //confere se o arquivo enviado ao json ja existe no banco de dados
        Optional<Arquivo> arquivoBD = arquivoRepository.findByNameAndOrganization(nomeArquivo, usuarioBD.get().getOrganizacao().getNome());
        if (arquivoBD.isPresent()){
            //caso exista, ele retorna esta "critica"
            throw new RuntimeException("Este arquivo já existe para esta organizacao");
        }

        // estamos instanciando a classe arquivo
        Arquivo arquivo = new Arquivo();

        // estamos inserindo os dados nos atributos da instancia arquivo
        arquivo.setNomeArquivo(nomeArquivo);
        arquivo.setStatus(new Status(2, "AGUARDANDO APROVAÇÃO DA BRONZE"));
        arquivo.setUsuario(usuarioBD.get());
        arquivo.setOrganizacao(usuarioBD.get().getOrganizacao());

        // estamos salvando os dados na instancia arquivo
        arquivoRepository.save(arquivo);

        // cria uma lista a partir dos atributos presentes na classe "RequestBodySaveMetadadoDTO"
        List<RequestBodySaveMetadadoDTO> list = requestBodySaveDTO.getMetadados();

        //estamos percorrendo a lista "list" e inserindo na variavel "item" toda vez que o for roda
        for (RequestBodySaveMetadadoDTO item : list){

            // compara se o tipo carregado no json corresponde a algum tipo ja existente no banco de dados
            Optional<Tipo> tipoDB = tipoRepository.findById(item.getTipo().getNomeTipo());

            // se o tipo não existir, ele retorna esta "critica"
            if (tipoDB.isEmpty()){
            throw new RuntimeException("O tipo "+item.getTipo().getNomeTipo()+" não existe");
            }

            // caso contratio, ele insere todos os dados recebidos nos atributos de metadata
            Metadata metadata = new Metadata();
            metadata.setAtivo(item.getAtivo());
            metadata.setNome(item.getNome());
            metadata.setValorPadrao(item.getValorPadrao());
            metadata.setDescricao(item.getDescricao());
            metadata.setRestricoes(item.getRestricoes());
            metadata.setNomeTipo(new Tipo(item.getTipo().getNomeTipo()));

            // envia os dados para arquivos
            metadata.setArquivo(arquivo);
            // salva a instancia metadata com todos os dados que lhe foram atribuidos
            metadataRepository.save(metadata);
        }
    }

    @Transactional
    public ResponseBodyGetMetadadosDTO getMetadadosInDatabase(String user, String nomeArquivo){
        Optional<Usuario> userBD = usuarioRepository.findById(user);
        if (userBD.isEmpty()){
            throw new RuntimeException("Usuário ["+user+"] não existe");
        }

        Optional<Arquivo> arqBD = arquivoRepository.findByNameAndOrganization(nomeArquivo, userBD.get().getOrganizacao().getNome());
        if (arqBD.isEmpty()){
            throw new RuntimeException("Arquivo [" + nomeArquivo + "] não encontrado para a organização [" + userBD.get().getOrganizacao().getNome() + "]");
        }

        List<ResponseBodyMetadadoDTO> temp = new ArrayList<>();
        for (Metadata metadata : arqBD.get().getMetadados()){
            temp.add(new ResponseBodyMetadadoDTO(metadata));
        }
        return new ResponseBodyGetMetadadosDTO(user, nomeArquivo, temp);

    }
}
