package com.dataflow.apidomrock.controllers;

import com.dataflow.apidomrock.dto.customResponse.CustomResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/healthcheck")
@CrossOrigin("*")
public class HealthCheckController {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @GetMapping
    public ResponseEntity<CustomResponseDTO<String>> healthCheck (){
        try {
            jdbcTemplate.execute("SELECT 1");
            return ResponseEntity.ok().body(new CustomResponseDTO<>("A API está em pé", null));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new CustomResponseDTO<>("Problema com conexão ao banco. Consultar o setor de suporte!", null));
        }

    }

}
