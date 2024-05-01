package com.dataflow.apidomrock.controllers;

import com.dataflow.apidomrock.controllers.exceptions.CustomException;
import com.dataflow.apidomrock.dto.customresponse.ResponseCustomDTO;
import com.dataflow.apidomrock.dto.registerdto.AutenticacaoDTO;
import com.dataflow.apidomrock.dto.registerdto.ResponseLoginDTO;
import com.dataflow.apidomrock.dto.registerdto.UsuarioDTO;
import com.dataflow.apidomrock.dto.registerdto.ValidacaoDTO;
import com.dataflow.apidomrock.entities.database.Usuario;
import com.dataflow.apidomrock.services.RegisterServices;
import com.dataflow.apidomrock.services.utils.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.security.NoSuchAlgorithmException;

@RestController
@RequestMapping(value = "/register")
@CrossOrigin("*")
public class RegisterController {
    @Autowired
    RegisterServices registerServices;
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    private TokenService tokenService;


    @PostMapping("/cadastro")
    public ResponseEntity<ResponseCustomDTO<String>> registerUserInDataBase(@RequestBody UsuarioDTO usuarioDTO) throws CustomException {
        registerServices.registerInDatabase(usuarioDTO);
        return ResponseEntity.ok().body(new ResponseCustomDTO<>("Processamento efetuado com sucesso", null));
    }

    @PostMapping("/validacao")
    public ResponseEntity<ResponseCustomDTO<String>> finishingRegisterUserInDataBase(@RequestBody ValidacaoDTO validacaoDTO) throws CustomException, NoSuchAlgorithmException {
        registerServices.FirstLogin(validacaoDTO);
        return ResponseEntity.ok().body(new ResponseCustomDTO<>("Processamento efetuado com sucesso", null));
    }

    @PostMapping("/login")
    public ResponseEntity<ResponseCustomDTO<ResponseLoginDTO>> userLogin(@RequestBody @Validated AutenticacaoDTO autenticacaoDTO) throws CustomException, CustomException {
        var usernamePassword = new UsernamePasswordAuthenticationToken(autenticacaoDTO.getLogin(), autenticacaoDTO.getSenha());
        var auth = this.authenticationManager.authenticate(usernamePassword);
        Usuario u = (Usuario) auth.getPrincipal();
        var token = tokenService.generateToken((Usuario) auth.getPrincipal());

        return ResponseEntity.ok().body(new ResponseCustomDTO<>("Processamento efetuado com sucesso", new ResponseLoginDTO(token, u.getNome(), u.getEmail(), (u.getNiveisAcesso().get(0).getNivel().equals("MASTER")))));
    }
}
