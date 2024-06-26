package com.dataflow.apidomrock.services;

import com.dataflow.apidomrock.controllers.exceptions.CustomException;
import com.dataflow.apidomrock.dto.registerdto.AutenticacaoDTO;
import com.dataflow.apidomrock.dto.registerdto.UsuarioDTO;
import com.dataflow.apidomrock.dto.registerdto.ValidacaoDTO;
import com.dataflow.apidomrock.dto.userlogout.LogoutDTO;
import com.dataflow.apidomrock.entities.database.Log;
import com.dataflow.apidomrock.entities.database.Organizacao;
import com.dataflow.apidomrock.entities.database.Usuario;
import com.dataflow.apidomrock.entities.enums.Acao;
import com.dataflow.apidomrock.entities.enums.Estagio;
import com.dataflow.apidomrock.entities.enums.NivelAcessoEnum;
import com.dataflow.apidomrock.repository.LogRepository;
import com.dataflow.apidomrock.repository.OrganizacaoRepository;
import com.dataflow.apidomrock.repository.UsuarioRepository;
import com.dataflow.apidomrock.services.mail.MailService;
import com.dataflow.apidomrock.services.utils.*;
import jakarta.mail.MessagingException;
import jakarta.mail.SendFailedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.NoSuchAlgorithmException;
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

    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    private TokenService tokenService;

    @Autowired
    Logger logger;
    @Autowired
    private LogRepository logRepository;

    @Transactional(rollbackFor = CustomException.class)
    public void registerInDatabase(UsuarioDTO register) throws CustomException {

        List<String> nvl = register.getNivelAcesso();
        if (nvl.isEmpty()) {
            throw new CustomException("Nenhum nível de acesso foi selecionado", HttpStatus.BAD_REQUEST);
        }
        boolean isFull = false;
        for (String nivelAcesso : nvl) {
            if (nivelAcesso.equalsIgnoreCase(NivelAcessoEnum.FULL.toString())) {
                isFull = true;
            }
        }

        if (isFull){
            Optional<Usuario> master = usuarioRepository.findByEmailCustom("master@gmail.com");
            if (master.isEmpty()){
                throw new CustomException("Organização MASTER não identificada", HttpStatus.BAD_REQUEST);
            }

            register.setCnpj(master.get().getOrganizacao().getCnpj());

        } else {
            if (!Validate.validarCNPJ(register.getCnpj())){
                throw new CustomException("O CNPJ inserido é inválido", HttpStatus.BAD_REQUEST);
            }
        }


        String cnpj = register.getCnpj().replaceAll("[^0-9]", "");
        Optional<Organizacao> organizacaoBD = organizacaoRepository.findById(cnpj);
        if (organizacaoBD.isEmpty()) {
            Organizacao organizacao = new Organizacao();
            organizacao.setCnpj(cnpj);
            organizacao.setNome(register.getOrganizacao());
            organizacaoRepository.save(organizacao);
            organizacaoBD = organizacaoRepository.findById(cnpj);
        }
        Optional<Usuario> userBD = usuarioRepository.findByEmailCustom(register.getEmailUsuario());
        if (userBD.isEmpty()) {
            Usuario usuario = new Usuario();
            usuario.setEmail(register.getEmailUsuario());
            usuario.setOrganizacao(organizacaoBD.get());
            usuario.setNiveisAcesso(validateNivelAcesso.nivelAcessoList(register.getNivelAcesso()));
            String token = UUID.randomUUID().toString();
            try {
                mailService.sendToken(register.getEmailUsuario(), register.getOrganizacao(), token);
            } catch (SendFailedException e) {
                throw new CustomException("Ops, parece que há algum problema com o email inserido.", HttpStatus.BAD_REQUEST);
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
        Optional<Usuario> usuarioBD = usuarioRepository.findByEmailCustom(completionRegister.getEmailUsuario());
        if (usuarioBD.isPresent()) {
            if (usuarioBD.get().getSenha() == null) {
                Usuario usuario = new Usuario();
                if (usuarioBD.get().getToken().equals(completionRegister.getToken())) {
                    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
                    usuarioBD.get().setSenha(passwordEncoder.encode(completionRegister.getSenha()));
                    usuarioBD.get().setNome(completionRegister.getNome());
                    usuarioRepository.save(usuarioBD.get());
                } else {
                    throw new CustomException("Token não foi encontrado", HttpStatus.NOT_FOUND);
                }
            } else {
                throw new CustomException("Este usuario ja possui cadastro", HttpStatus.NOT_FOUND);
            }
        } else {
            throw new CustomException("Usuário " + completionRegister.getEmailUsuario() + " não foi cadastrado previamente", HttpStatus.NOT_FOUND);
        }
    }

    @Transactional(rollbackFor = CustomException.class)
    public String login(AutenticacaoDTO autenticacaoDTO){
        var usernamePassword = new UsernamePasswordAuthenticationToken(autenticacaoDTO.getLogin(), autenticacaoDTO.getSenha());
        var auth = this.authenticationManager.authenticate(usernamePassword);
        var token = tokenService.generateToken((Usuario) auth.getPrincipal());
        return token;
    }

    public Usuario getUsuario(AutenticacaoDTO autenticacaoDTO) throws CustomException {
        var usernamePassword = new UsernamePasswordAuthenticationToken(autenticacaoDTO.getLogin(), autenticacaoDTO.getSenha());
        var auth = this.authenticationManager.authenticate(usernamePassword);
        Usuario u = (Usuario) auth.getPrincipal();
        Log logout = logRepository.findLastActionByUsuario(u.getId());
        if(logout == null){
            logger.insert(u.getId(), null, null,Estagio.loginLogout, Acao.LOGIN);
        }else {
            if(logout.getAcao().equalsIgnoreCase(Acao.LOGIN.toString())){
                logger.insertToLogout(u.getId(), null, null, Estagio.loginLogout, Acao.LOGOUT,logout);
            }
            logger.insert(u.getId(), null, null, Estagio.loginLogout, Acao.LOGIN);
        }
        return u;
    }

    @Transactional(rollbackFor = CustomException.class)
    public void logout(LogoutDTO request) throws CustomException {
        Optional<Usuario> usuario = usuarioRepository.findByEmailCustom(request.email());
        if(usuario.isEmpty()) {
            throw new CustomException("Usuário não encontrado, erro ao sair da aplicação",HttpStatus.BAD_REQUEST);
        }
        logger.insert(usuario.get().getId(), null, null, Estagio.loginLogout, Acao.LOGOUT);
    }
}
