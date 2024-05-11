package com.dataflow.apidomrock.services;


import com.dataflow.apidomrock.controllers.exceptions.CustomException;
import com.dataflow.apidomrock.dto.gethash.ResquestHashToSilverDTO;
import com.dataflow.apidomrock.dto.setstatusbz.RequestBodySetStatusBzDTO;
import com.dataflow.apidomrock.entities.database.Arquivo;
import com.dataflow.apidomrock.entities.database.Metadata;
import com.dataflow.apidomrock.entities.database.Usuario;
import com.dataflow.apidomrock.entities.enums.Acao;
import com.dataflow.apidomrock.entities.enums.Estagio;
import com.dataflow.apidomrock.entities.enums.StatusArquivo;
import com.dataflow.apidomrock.repository.ArquivoRepository;
import com.dataflow.apidomrock.repository.UsuarioRepository;
import com.dataflow.apidomrock.services.utils.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SilverZoneService {

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    ArquivoRepository arquivoRepository;

    @Autowired
    Logger logger;

    public void updateStatus (RequestBodySetStatusBzDTO request) throws CustomException {

        Optional<Usuario> user = usuarioRepository.findByEmailCustom(request.usuario());

        if (request.salvar()) {
            Arquivo arq =  arquivoRepository.findByNomeArquivo(request.arquivo());
            arq.setStatus(StatusArquivo.SILVER_ZONE.getDescricao());
            arquivoRepository.save(arq);
            logger.insert(user.get().getId(),arq.getId(),request.obs(), Estagio.S, Acao.APROVAR);
        } else {
            if(!request.obs().isEmpty()){
                Arquivo arq =  arquivoRepository.findByNomeArquivo(request.arquivo());
                arq.setStatus(StatusArquivo.NAO_APROVADO_PELA_SILVER.getDescricao());
                arquivoRepository.save(arq);
                logger.insert(user.get().getId(),arq.getId(),request.obs(), Estagio.S, Acao.REPROVAR);
            }else{
                throw new CustomException("Você não pode reprovar sem o preenchimento da observação", HttpStatus.BAD_REQUEST);
            }
        }
    }

    public List<String> getMetadadosNoHash(ResquestHashToSilverDTO request) throws CustomException {

        Optional<Usuario> usuario = usuarioRepository.findByEmailCustom(request.usuario());

        Optional<Arquivo> arquivo = arquivoRepository.findByNameAndOrganization(request.nomeArquivo(),usuario.get().getOrganizacao().getCnpj());

        if(usuario.isEmpty()){
            throw new CustomException("Ocorreu um erro inesperado ao buscar o usuario", HttpStatus.BAD_REQUEST);
        }else{

            if(arquivo.isEmpty()){
                throw new CustomException("Ocorreu um erro inesperado ao buscar o arquivo", HttpStatus.BAD_REQUEST);
            }else {
                List<Metadata> metadadosHash = arquivoRepository.findByMetadataHash(arquivo.get().getId());

                List<String> metadadosNoHash = new ArrayList<>();
                int qtdMetadadosHash = metadadosHash.size();

                for(int i = 0; i < qtdMetadadosHash; i++){
                    metadadosNoHash.add(metadadosHash.get(i).getNome());
                }

                return metadadosNoHash;
            }

        }
    }


}
