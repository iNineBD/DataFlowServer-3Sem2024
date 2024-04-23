package com.dataflow.apidomrock.controllers;

import com.dataflow.apidomrock.dto.customresponse.ResponseCustomDTO;
import com.dataflow.apidomrock.services.mail.MailService;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping(value = "/healthcheck")
@CrossOrigin("*")
public class HealthCheckController {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private MailService mail;

    @GetMapping
    public ResponseEntity<ResponseCustomDTO<String>> healthCheck (){
        try {
            jdbcTemplate.execute("SELECT 1");
            return ResponseEntity.ok().body(new ResponseCustomDTO<>("A API está em pé", null));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ResponseCustomDTO<>("Problema com conexão ao banco. Consultar o setor de suporte!", null));
        }

    }

    @PostMapping("/mail")
    public ResponseEntity<ResponseCustomDTO<String>> sendMail() throws MessagingException {

        mail.sendToken("anaraquelysmachado29@gmail.com", "iNine",UUID.randomUUID().toString());

        return ResponseEntity.ok().body(new ResponseCustomDTO<>("Email enviado", null));
    }

}
