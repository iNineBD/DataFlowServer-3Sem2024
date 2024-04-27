package com.dataflow.apidomrock.services;

import com.dataflow.apidomrock.controllers.exceptions.CustomException;
import com.dataflow.apidomrock.dto.showzones.RequestDadosDTO;
import com.dataflow.apidomrock.dto.showzones.ResponseNavigationDTO;
import com.dataflow.apidomrock.entities.database.Arquivo;
import com.dataflow.apidomrock.entities.database.NivelAcesso;
import com.dataflow.apidomrock.entities.enums.NivelAcessoEnum;
import com.dataflow.apidomrock.entities.enums.StatusArquivo;
import com.dataflow.apidomrock.repository.ArquivoRepository;
import com.dataflow.apidomrock.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class NavigationServices {

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    ArquivoRepository arquivoRepository;


    public ResponseNavigationDTO searchAndShowAccess (RequestDadosDTO request) throws CustomException {

        NivelAcesso usuario = usuarioRepository.getNivelUsuario(request.usuario());

        Arquivo arquivo = arquivoRepository.findByNomeArquivo(request.nomeArquivo());

        boolean acessoLz;
        boolean acessoBz;
        boolean acessoSz;
        if(arquivo != null) {
            if (arquivo.getStatus().equals(StatusArquivo.AGUARDANDO_APROVACAO_SILVER.getDescricao()) || arquivo.getStatus().equals(StatusArquivo.SILVER_ZONE.getDescricao())) {
                if (usuario.getNivel().equals(NivelAcessoEnum.MASTER.toString()) || usuario.getNivel().equals(NivelAcessoEnum.FULL.toString())) {
                    acessoLz = true;
                    acessoBz = true;
                    acessoSz = true;
                } else if (usuario.getNivel().equals(NivelAcessoEnum.S.toString())) {
                    acessoLz = false;
                    acessoBz = false;
                    acessoSz = true;
                } else if(usuario.getNivel().equals(NivelAcessoEnum.B.toString())){
                    acessoLz = false;
                    acessoBz = true;
                    acessoSz = false;
                }else {
                    acessoLz = false;
                    acessoBz = false;
                    acessoSz = false;
                }
            } else if (arquivo.getStatus().equals(StatusArquivo.NAO_APROVADO_PELA_SILVER.getDescricao()) || arquivo.getStatus().equals(StatusArquivo.AGUARDANDO_APROVACAO_BRONZE.getDescricao()) || arquivo.getStatus().equals(StatusArquivo.BRONZE_ZONE.getDescricao())) {
                if (usuario.getNivel().equals(NivelAcessoEnum.MASTER.toString()) || usuario.getNivel().equals(NivelAcessoEnum.FULL.toString())) {
                    acessoLz = true;
                    acessoBz = true;
                    acessoSz = false;
                } else if (usuario.getNivel().equals(NivelAcessoEnum.B.toString())) {
                    acessoLz = false;
                    acessoBz = true;
                    acessoSz = false;
                }else if(usuario.getNivel().equals(NivelAcessoEnum.LZ.toString())){
                    acessoLz = true;
                    acessoBz = false;
                    acessoSz = false;
                }
                else {
                    acessoLz = false;
                    acessoBz = false;
                    acessoSz = false;
                }
            } else {
                if (usuario.getNivel().equals(NivelAcessoEnum.MASTER.toString()) || usuario.getNivel().equals(NivelAcessoEnum.FULL.toString())) {
                    acessoLz = true;
                    acessoBz = false;
                    acessoSz = false;
                } else if (usuario.getNivel().equals(NivelAcessoEnum.LZ.toString())) {
                    acessoLz = true;
                    acessoBz = false;
                    acessoSz = false;
                } else {
                    acessoLz = false;
                    acessoBz = false;
                    acessoSz = false;
                }
            }

            return new ResponseNavigationDTO(request.usuario(), acessoLz, acessoBz, acessoSz);
        }else {
            throw new CustomException("O arquivo ["+ request.nomeArquivo() + "] não existe", HttpStatus.BAD_REQUEST);
        }

    }
}
