package com.dataflow.apidomrock.services;

import com.dataflow.apidomrock.controllers.exceptions.CustomException;
import com.dataflow.apidomrock.dto.registerdto.UsuarioDTO;
import com.dataflow.apidomrock.dto.registerdto.ValidacaoDTO;
import com.dataflow.apidomrock.entities.database.Organizacao;
import com.dataflow.apidomrock.entities.database.Usuario;
import com.dataflow.apidomrock.repository.OrganizacaoRepository;
import com.dataflow.apidomrock.repository.UsuarioRepository;
import com.dataflow.apidomrock.services.mail.MailService;
import com.dataflow.apidomrock.services.utils.Encrypt;
import com.dataflow.apidomrock.services.utils.Validate;
import com.dataflow.apidomrock.services.utils.ValidateNivelAcesso;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.NoSuchAlgorithmException;
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
        if (!Validate.validarCNPJ(register.getCnpj())){
            throw new CustomException("O CNPJ inserido é inválido", HttpStatus.BAD_REQUEST);
        }
        Optional<Organizacao> organizacaoBD = organizacaoRepository.findById(register.getCnpj());
        if (organizacaoBD.isEmpty()) {
            Organizacao organizacao = new Organizacao();
            organizacao.setCnpj(register.getCnpj());
            organizacao.setNome(register.getOrganizacao());
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
                mailService.sendToken(register.getEmailUsuario(), register.getOrganizacao(), token);
            } catch (MessagingException e) {
                throw new CustomException("Não foi possivel encaminhar o token para o email", HttpStatus.SERVICE_UNAVAILABLE);
            }
            usuario.setToken(token);
            usuarioRepository.save(usuario);
        } else {
            throw new CustomException("Usuário [" + register.getEmailUsuario() + "] ja existe", HttpStatus.BAD_REQUEST);
        }
    }

    @Transactional(rollbackFor = CustomException.class)
    public void FirstLogin(ValidacaoDTO completionRegister) throws CustomException, NoSuchAlgorithmException {
        Optional<Usuario> usuarioBD = usuarioRepository.findByEmail(completionRegister.getEmailUsuario());
        if (usuarioBD.isPresent()) {
            if (usuarioBD.get().getSenha() == null) {
                Usuario usuario = new Usuario();
                if (usuarioBD.get().getToken().equals(completionRegister.getToken())) {
                    usuarioBD.get().setSenha(Encrypt.encrypt(completionRegister.getSenha()));
                    usuarioBD.get().setNome(completionRegister.getNome());
                    usuarioRepository.save(usuarioBD.get());
                } else {
                    throw new CustomException("Token não foi encontrado", HttpStatus.NOT_FOUND);
                }
            } else {
                throw new CustomException("Este usuario ja possui cadastro", HttpStatus.NOT_FOUND);
            }
        } else {
            throw new CustomException("Usuario " + completionRegister.getEmailUsuario() + "não foi cadastrado previamente", HttpStatus.NOT_FOUND);
        }
    }
}
