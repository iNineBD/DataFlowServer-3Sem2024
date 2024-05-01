package com.dataflow.apidomrock.services;

import com.dataflow.apidomrock.controllers.exceptions.CustomException;
import com.dataflow.apidomrock.dto.entitiesdto.MetadataDTO;
import com.dataflow.apidomrock.dto.getmetadados.ResponseBodyGetMetadadosDTO;
import com.dataflow.apidomrock.entities.database.*;
import com.dataflow.apidomrock.entities.enums.Acao;
import com.dataflow.apidomrock.entities.enums.Estagio;
import com.dataflow.apidomrock.entities.enums.NivelAcessoEnum;
import com.dataflow.apidomrock.entities.enums.StatusArquivo;
import com.dataflow.apidomrock.repository.ArquivoRepository;
import com.dataflow.apidomrock.repository.LogRepository;
import com.dataflow.apidomrock.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class GlobalServices {

    @Autowired
    ArquivoRepository arquivoRepository;

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    LogRepository logRepository;

    @Transactional(rollbackFor = CustomException.class)
    public ResponseBodyGetMetadadosDTO getMetadadosInDatabase(String user, String nomeArquivo) throws CustomException {
        //CONFERE SE O USUARIO QUE SUBIU O JSON JA EXISTE NA BASE
        Optional<Usuario> userBD = usuarioRepository.findByEmailCustom(user);
        //SE NÃO EXISTIR, ELE SOLTA ESTA "CRITICA"
        if (userBD.isEmpty()) {
            throw new CustomException("Usuário [" + user + "] não existe", HttpStatus.NOT_FOUND);
        }

        boolean havePermission = false;
        for (NivelAcesso n : userBD.get().getNiveisAcesso()){
            if (n.getNivel().equals(NivelAcessoEnum.LZ.toString()) || n.getNivel().equals(NivelAcessoEnum.MASTER.toString()) || n.getNivel().equals(NivelAcessoEnum.FULL.toString())){
                havePermission = true;
            }
        }

        if (!havePermission) {
            throw new CustomException("Você não tem permissão para acessar o recurso", HttpStatus.UNAUTHORIZED);
        }

        //CONFERE SE O ARQUIVO QUE SUBIU O JSON JA EXISTE NA BASE
        Optional<Arquivo> arqBD = arquivoRepository.findByNameAndOrganization(nomeArquivo, userBD.get().getOrganizacao().getCnpj());
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
    @Transactional(rollbackFor = CustomException.class)
    public String getLastObs(Integer arquivoID, Estagio estagio, Acao acao) throws CustomException {

        Optional<Log> register = logRepository.findLastObsInFile(arquivoID, estagio.getDescricao(), acao.toString());

        if (register.isPresent()) {
            return register.get().getObservacao();
        }
        return "Sem observações recentes";
    }
}
