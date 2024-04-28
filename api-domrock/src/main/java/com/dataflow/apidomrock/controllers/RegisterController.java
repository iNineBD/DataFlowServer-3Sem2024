package com.dataflow.apidomrock.controllers;

import com.dataflow.apidomrock.controllers.exceptions.CustomException;
import com.dataflow.apidomrock.dto.customresponse.ResponseCustomDTO;
import com.dataflow.apidomrock.dto.registerdto.AutenticacaoDTO;
import com.dataflow.apidomrock.dto.registerdto.UsuarioDTO;
import com.dataflow.apidomrock.dto.registerdto.ValidacaoDTO;
import com.dataflow.apidomrock.services.RegisterServices;
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
    public ResponseEntity<ResponseCustomDTO<String>> userLogin(@RequestBody @Validated AutenticacaoDTO autenticacaoDTO) throws CustomException, CustomException {
        var usernamePassword = new UsernamePasswordAuthenticationToken(autenticacaoDTO.getLogin(), autenticacaoDTO.getSenha());
        var auth = this.authenticationManager.authenticate(usernamePassword);
        return ResponseEntity.ok().body(new ResponseCustomDTO<>("Processamento efetuado com sucesso", null));
    }
}
