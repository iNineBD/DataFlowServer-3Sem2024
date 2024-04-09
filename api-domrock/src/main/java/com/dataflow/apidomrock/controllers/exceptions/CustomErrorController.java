package com.dataflow.apidomrock.controllers.exceptions;

import com.dataflow.apidomrock.dto.customResponse.CustomResponseDTO;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

/*
* Essa classe implementa a ErrorController e mapeia erros do tipo: "url digitada errada..."
* */
@RestController
public class CustomErrorController implements ErrorController {
    private static final String PATH = "/error";

    @RequestMapping(value = PATH)
    public ResponseEntity<CustomResponseDTO<Object>> error() {
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Página não encontrada");
    }

}