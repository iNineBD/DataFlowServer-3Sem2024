package com.dataflow.apidomrock.controllers;

//internal imports
import com.dataflow.apidomrock.controllers.exceptions.CustomException;
import com.dataflow.apidomrock.dto.customresponse.ResponseCustomDTO;
import com.dataflow.apidomrock.dto.registerdto.AutenticacaoDTO;
import com.dataflow.apidomrock.dto.registerdto.ResponseLoginDTO;
import com.dataflow.apidomrock.dto.registerdto.UsuarioDTO;
import com.dataflow.apidomrock.dto.registerdto.ValidacaoDTO;
import com.dataflow.apidomrock.dto.userlogout.LogoutDTO;
import com.dataflow.apidomrock.entities.database.Usuario;
import com.dataflow.apidomrock.services.RegisterServices;
import com.dataflow.apidomrock.services.utils.TokenService;
//spring imports
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

//swagger imports
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;

//java imports
import java.security.NoSuchAlgorithmException;

@RestController
@RequestMapping(value = "/register")
@CrossOrigin("*")
@Tag(name = "RegisterController", description = "Operações de registro de usuário")
public class RegisterController {
    @Autowired
    RegisterServices registerServices;
    @Autowired
    AuthenticationManager authenticationManager;


    @Operation(summary = "Cadastra usuário no banco de dados", method = "POST")
    @ApiDefaultResponses
    @PostMapping("/cadastro")
    public ResponseEntity<ResponseCustomDTO<String>> registerUserInDataBase(@RequestBody UsuarioDTO usuarioDTO)
            throws CustomException {
        registerServices.registerInDatabase(usuarioDTO);
        return ResponseEntity.ok().body(new ResponseCustomDTO<>("Processamento efetuado com sucesso", null));
    }

    @Operation(summary = "Finaliza cadastro de usuário no banco de dados", method = "POST")
    @ApiDefaultResponses
    @PostMapping("/validacao")
    public ResponseEntity<ResponseCustomDTO<String>> finishingRegisterUserInDataBase(
            @RequestBody ValidacaoDTO validacaoDTO) throws CustomException, NoSuchAlgorithmException {
        registerServices.FirstLogin(validacaoDTO);
        return ResponseEntity.ok().body(new ResponseCustomDTO<>("Processamento efetuado com sucesso", null));
    }

    @Operation(summary = "Login de usuário", method = "POST")
    @ApiDefaultResponses
    @PostMapping("/login")
    public ResponseEntity<ResponseCustomDTO<ResponseLoginDTO>> userLogin( @RequestBody @Validated AutenticacaoDTO autenticacaoDTO) throws CustomException {
        String token = registerServices.login(autenticacaoDTO);
        Usuario u = registerServices.getUsuario(autenticacaoDTO);
        return ResponseEntity.ok().body(new ResponseCustomDTO<>("Processamento efetuado com sucesso", new ResponseLoginDTO(token, u.getNome(), u.getEmail(), (u.getNiveisAcesso().get(0).getNivel().equals("MASTER")))));
    }

    @Operation(summary = "Logout de usuário", method = "POST")
    @ApiDefaultResponses
    @PostMapping("/logout")
    public ResponseEntity<ResponseCustomDTO<String>> userLogout( @RequestBody LogoutDTO request) throws CustomException {
        registerServices.logout(request);
        return ResponseEntity.ok().body(new ResponseCustomDTO<>("Processamento efetuado com sucesso", null));
    }
}
