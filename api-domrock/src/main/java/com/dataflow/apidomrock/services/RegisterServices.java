package com.dataflow.apidomrock.services;

import com.dataflow.apidomrock.controllers.exceptions.CustomException;
import com.dataflow.apidomrock.dto.registerdto.UsuarioDTO;
import com.dataflow.apidomrock.entities.database.NivelAcesso;
import com.dataflow.apidomrock.entities.database.Organizacao;
import com.dataflow.apidomrock.entities.database.Usuario;
import com.dataflow.apidomrock.repository.NivelAcessoRepository;
import com.dataflow.apidomrock.repository.OrganizacaoRepository;
import com.dataflow.apidomrock.repository.UsuarioRepository;
import org.apache.poi.sl.draw.geom.GuideIf;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class RegisterServices {
    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    NivelAcessoRepository nivelAcessoRepository;

    @Transactional(rollbackFor = CustomException.class)
    public void registerInDatabase(UsuarioDTO register) throws CustomException {
        Optional<NivelAcesso> nivelAcessoBD = nivelAcessoRepository.findByTipo();
        if (nivelAcessoBD.isPresent()){

            Optional<Usuario> userBD = usuarioRepository.findByEmail(register.getEmailUsuario());

            if (userBD.isEmpty()) {
                Usuario usuario = new Usuario();
                usuario.setEmail(register.getEmailUsuario());
                usuario.setOrganizacao(userBD.get().getOrganizacao());
                usuario.setNiveisAcesso(userBD.get().getNiveisAcesso());
                usuario.setToken(register.getToken());
            }
            throw new CustomException("Usuário [" + register.getEmailUsuario() + "] ja existe", HttpStatus.NOT_ACCEPTABLE);
            }
        usuarioRepository.save(new Usuario());


    }
}
