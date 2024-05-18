package com.dataflow.apidomrock.services;

import com.dataflow.apidomrock.controllers.exceptions.CustomException;
import com.dataflow.apidomrock.dto.showzones.RequestDadosDTO;
import com.dataflow.apidomrock.dto.showzones.ResponseNavigationDTO;
import com.dataflow.apidomrock.entities.database.Arquivo;
import com.dataflow.apidomrock.entities.database.NivelAcesso;
import com.dataflow.apidomrock.entities.database.Usuario;
import com.dataflow.apidomrock.entities.enums.NivelAcessoEnum;
import com.dataflow.apidomrock.entities.enums.StatusArquivo;
import com.dataflow.apidomrock.repository.ArquivoRepository;
import com.dataflow.apidomrock.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NavigationServices {

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    ArquivoRepository arquivoRepository;


    public ResponseNavigationDTO searchAndShowAccess (RequestDadosDTO request) throws CustomException {

        Optional<Usuario> user = usuarioRepository.findByEmailCustom(request.usuario());
        if (user.isEmpty()) {
            throw new CustomException("Ocorreu um erro inesperado ao buscar o arquivo.", HttpStatus.BAD_REQUEST);
        }

        Optional<Arquivo> arquivoBD = arquivoRepository.findByNameAndOrganization(request.nomeArquivo(), request.cnpjFile());
        if (arquivoBD.isEmpty()){
            throw new CustomException("Ocorreu um erro inesperado ao buscar o arquivo.", HttpStatus.BAD_REQUEST);
        }

        Arquivo arquivo = arquivoBD.get();

        List<NivelAcesso> usuario = usuarioRepository.getNivelUsuario(request.usuario());

        boolean acessoLz = false;
        boolean acessoBz = false;
        boolean acessoSz = false;

        int qtdNivel = usuario.size();
        for(int i = 0; i < qtdNivel; i++) {
            if (arquivo != null) {
                if (arquivo.getStatus().equals(StatusArquivo.AGUARDANDO_APROVACAO_SILVER.getDescricao()) || arquivo.getStatus().equals(StatusArquivo.SILVER_ZONE.getDescricao()) || arquivo.getStatus().equals(StatusArquivo.FINALIZADO.getDescricao())) {
                    if (usuario.get(i).getNivel().equals(NivelAcessoEnum.MASTER.toString()) || usuario.get(i).getNivel().equals(NivelAcessoEnum.FULL.toString())) {
                        acessoLz = true;
                        acessoBz = true;
                        acessoSz = true;
                    } else if (usuario.get(i).getNivel().equals(NivelAcessoEnum.S.toString())) {
                        acessoSz = true;
                    } else if (usuario.get(i).getNivel().equals(NivelAcessoEnum.B.toString())) {
                        acessoBz = true;
                    }else if(usuario.get(i).getNivel().equals(NivelAcessoEnum.LZ.toString())){
                        acessoLz = true;
                    }
                } else if (arquivo.getStatus().equals(StatusArquivo.NAO_APROVADO_PELA_SILVER.getDescricao()) || arquivo.getStatus().equals(StatusArquivo.AGUARDANDO_APROVACAO_BRONZE.getDescricao()) || arquivo.getStatus().equals(StatusArquivo.BRONZE_ZONE.getDescricao())) {
                    if (usuario.get(i).getNivel().equals(NivelAcessoEnum.MASTER.toString()) || usuario.get(i).getNivel().equals(NivelAcessoEnum.FULL.toString())) {
                        acessoLz = true;
                        acessoBz = true;
                        acessoSz = false;
                    } else if (usuario.get(i).getNivel().equals(NivelAcessoEnum.B.toString())) {
                        acessoBz = true;
                    } else if (usuario.get(i).getNivel().equals(NivelAcessoEnum.LZ.toString())) {
                        acessoLz = true;
                    }
                } else {
                    if (usuario.get(i).getNivel().equals(NivelAcessoEnum.MASTER.toString()) || usuario.get(i).getNivel().equals(NivelAcessoEnum.FULL.toString())) {
                        acessoLz = true;
                    } else if (usuario.get(i).getNivel().equals(NivelAcessoEnum.LZ.toString())) {
                        acessoLz = true;
                    }
                }

            } else {
                throw new CustomException("O arquivo [" + request.nomeArquivo() + "] não existe", HttpStatus.BAD_REQUEST);
            }
        }
        return new ResponseNavigationDTO(request.usuario(), acessoLz, acessoBz, acessoSz);
    }
}
