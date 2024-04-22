package com.dataflow.apidomrock.services;

import com.dataflow.apidomrock.controllers.exceptions.CustomException;
import com.dataflow.apidomrock.dto.registerdto.UsuarioDTO;
import com.dataflow.apidomrock.entities.database.NivelAcesso;
import com.dataflow.apidomrock.entities.database.Organizacao;
import com.dataflow.apidomrock.entities.database.Usuario;
import com.dataflow.apidomrock.repository.NivelAcessoRepository;
import com.dataflow.apidomrock.repository.OrganizacaoRepository;
import com.dataflow.apidomrock.repository.UsuarioRepository;
import com.dataflow.apidomrock.services.mail.MailService;
import com.dataflow.apidomrock.services.utils.ValidateNivelAcesso;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class RegisterServices {
    @Autowired
    UsuarioRepository usuarioRepository;
    @Autowired
    OrganizacaoRepository organizacaoRepository;
    @Autowired
    MailService mailService;
    @Autowired
    ValidateNivelAcesso validateNivelAcesso;

    @Transactional(rollbackFor = CustomException.class)
    public void registerInDatabase(UsuarioDTO register) throws CustomException {
        Optional<Organizacao> organizacaoBD = organizacaoRepository.findById(register.getCnpj());
        if (organizacaoBD.isEmpty()) {
            Organizacao organizacao = new Organizacao();
            organizacao.setCnpj(register.getCnpj());
            organizacao.setNome(register.getOrganização());
            organizacaoRepository.save(organizacao);
            organizacaoBD = organizacaoRepository.findById(register.getCnpj());
        }

        Optional<Usuario> userBD = usuarioRepository.findByEmail(register.getEmailUsuario());

        if (userBD.isEmpty()) {
            Usuario usuario = new Usuario();
            usuario.setEmail(register.getEmailUsuario());
            usuario.setOrganizacao(organizacaoBD.get());
            usuario.setNiveisAcesso(validateNivelAcesso.nivelAcessoList(register.getNivelAcesso()));
            String token = UUID.randomUUID().toString();
            try {
                mailService.sendToken(register.getEmailUsuario(), register.getOrganização(), token);
            } catch (MessagingException e) {
                throw new CustomException("Não foi possivel encaminhar o token para o email", HttpStatus.SERVICE_UNAVAILABLE);
            }
            usuario.setToken(token);
            usuarioRepository.save(usuario);
        } else {
            throw new CustomException("Usuário [" + register.getEmailUsuario() + "] ja existe", HttpStatus.BAD_REQUEST);
        }
    }
}
