package com.dataflow.apidomrock.controllers.exceptions;

//internal imports
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.dataflow.apidomrock.dto.customresponse.ResponseCustomDTO;

import io.swagger.v3.oas.annotations.Hidden;

/*
* Essa classe implementa a ErrorController e mapeia erros do tipo: "url digitada errada..."
* */
@RestController
@Hidden
public class CustomErrorController implements ErrorController {

    private static final String PATH = "/error";

    @RequestMapping(value = PATH)
    public ResponseEntity<ResponseCustomDTO<Object>> error() {
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Página não encontrada");
    }

}