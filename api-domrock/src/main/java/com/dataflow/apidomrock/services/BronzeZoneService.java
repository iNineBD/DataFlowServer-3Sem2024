package com.dataflow.apidomrock.services;

import com.dataflow.apidomrock.controllers.exceptions.CustomException;
import com.dataflow.apidomrock.dto.createHash.RequestArquivoDTO;
import com.dataflow.apidomrock.dto.createHash.ResponseMetadadoDTO;
import com.dataflow.apidomrock.dto.setstatusbz.RequestBodySetStatusBzDTO;
import com.dataflow.apidomrock.entities.database.Arquivo;
import com.dataflow.apidomrock.entities.database.Usuario;
import com.dataflow.apidomrock.entities.enums.Acao;
import com.dataflow.apidomrock.entities.enums.Estagio;
import com.dataflow.apidomrock.entities.enums.StatusArquivo;
import com.dataflow.apidomrock.repository.ArquivoRepository;
import com.dataflow.apidomrock.repository.UsuarioRepository;
import com.dataflow.apidomrock.services.utils.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BronzeZoneService {

    @Autowired
    UsuarioRepository usuarioRepository;
    @Autowired
    ArquivoRepository arquivoRepository;

    @Autowired
    Logger logger;

    public void updateStatus (RequestBodySetStatusBzDTO request) throws CustomException {

        Optional<Usuario> user = usuarioRepository.findByEmail(request.usuario());

        if (request.salvar()) {
            Arquivo arq =  arquivoRepository.findByNomeArquivo(request.arquivo());
            arq.setStatus(StatusArquivo.BRONZE_ZONE.getDescricao());
            arquivoRepository.save(arq);
            logger.insert(user.get().getId(),arq.getId(),request.obs(), Estagio.B, Acao.APROVAR);
        } else {
            Arquivo arq =  arquivoRepository.findByNomeArquivo(request.arquivo());
            arq.setStatus(StatusArquivo.NAO_APROVADO_PELA_BRONZE.getDescricao());
            arquivoRepository.save(arq);
            logger.insert(user.get().getId(),arq.getId(),request.obs(), Estagio.B, Acao.REPROVAR);
        }
    }

    public ResponseMetadadoDTO createHash(RequestArquivoDTO request) throws CustomException{



    }
}
