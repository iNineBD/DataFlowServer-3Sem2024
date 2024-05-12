package com.dataflow.apidomrock.services;


import com.dataflow.apidomrock.controllers.exceptions.CustomException;
import com.dataflow.apidomrock.dto.gethash.ResponseNomeMetadataDTO;
import com.dataflow.apidomrock.dto.gethash.ResquestHashToSilverDTO;
import com.dataflow.apidomrock.dto.setstatussz.RequestBodySetStatusSz;
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
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional(rollbackFor = CustomException.class)
    public void updateStatus (RequestBodySetStatusSz request) throws CustomException {

        Optional<Usuario> user = usuarioRepository.findByEmailCustom(request.usuario());
        if(user.isEmpty()){
            throw new CustomException("Ocorreu um erro inesperado ao buscar o usuario", HttpStatus.BAD_REQUEST);
        }else{
            if (request.salvar()) {
                Optional<Arquivo> arq =  arquivoRepository.findByNameAndOrganization(request.arquivo(), request.cnpj());
                arq.get().setStatus(StatusArquivo.SILVER_ZONE.getDescricao());
                arquivoRepository.save(arq.get());
                logger.insert(user.get().getId(),arq.get().getId(),request.obs(), Estagio.S, Acao.APROVAR);
            } else {
                if(!request.obs().isEmpty()){
                    Optional<Arquivo> arq =  arquivoRepository.findByNameAndOrganization(request.arquivo(), request.cnpj());
                    arq.get().setStatus(StatusArquivo.NAO_APROVADO_PELA_SILVER.getDescricao());
                    arquivoRepository.save(arq.get());
                    logger.insert(user.get().getId(),arq.get().getId(),request.obs(), Estagio.S, Acao.REPROVAR);
                }else{
                    throw new CustomException("Você não pode reprovar sem o preenchimento da observação", HttpStatus.BAD_REQUEST);
                }
            }
        }
    }

    public List<ResponseNomeMetadataDTO> getMetadadosNoHash(ResquestHashToSilverDTO request) throws CustomException {

        Optional<Usuario> usuario = usuarioRepository.findByEmailCustom(request.usuario());

        Optional<Arquivo> arquivo = arquivoRepository.findByNameAndOrganization(request.nomeArquivo(), request.cnpjOrg());

        if(usuario.isEmpty()){
            throw new CustomException("Ocorreu um erro inesperado ao buscar o usuario", HttpStatus.BAD_REQUEST);
        }else{

            if(arquivo.isEmpty()){
                throw new CustomException("Ocorreu um erro inesperado ao buscar o arquivo", HttpStatus.BAD_REQUEST);
            }else {
                List<Metadata> metadadosNoHash = arquivoRepository.findByMetadataHash(arquivo.get().getId());

                List<ResponseNomeMetadataDTO> metadados = metadadosNoHash.stream().map(ResponseNomeMetadataDTO::new).toList();

                return metadados;
            }

        }
    }


}
