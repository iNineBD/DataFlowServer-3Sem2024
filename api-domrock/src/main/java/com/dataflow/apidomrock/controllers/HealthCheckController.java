package com.dataflow.apidomrock.controllers;

//internal imports
import com.dataflow.apidomrock.dto.customresponse.ResponseCustomDTO;
import com.dataflow.apidomrock.services.mail.MailService;
import jakarta.mail.MessagingException;
//spring imports
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;
//java imports
import java.util.UUID;
//swagger imports
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping(value = "/healthcheck")
@CrossOrigin("*")
@Tag(name = "HealthCheck", description = "Verifica a conexão da API ao banco e email")
public class HealthCheckController {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private MailService mail;

    @GetMapping
    @Operation(summary = "Verifica a conexão da API ao banco", method = "GET")
    @ApiDefaultResponses
    public ResponseEntity<ResponseCustomDTO<String>> healthCheck() {
        try {
            jdbcTemplate.execute("SELECT 1");
            return ResponseEntity.ok().body(new ResponseCustomDTO<>("A API está em pé", null));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(
                    new ResponseCustomDTO<>("Problema com conexão ao banco. Consultar o setor de suporte!", null));
        }

    }

    @PostMapping("/mail")
    @Operation(summary = "Envia um email de teste", method = "POST")
    @ApiDefaultResponses
    public ResponseEntity<ResponseCustomDTO<String>> sendMail() throws MessagingException {

        mail.sendToken("joao.lamao@fatec.sp.gov.br", "iNine", UUID.randomUUID().toString());

        return ResponseEntity.ok().body(new ResponseCustomDTO<>("Email enviado", null));
    }

}
