package com.dataflow.apidomrock.controllers.exceptions;

//external imports
import org.springframework.http.HttpStatus;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Tag(name = "CustomException", description = "Classe para customizar exceções")
public class CustomException extends Exception {
    @Schema(description = "Mensagem de erro", example = "Erro ao tentar acessar o banco de dados")
    private String msg;
    @Schema(description = "Status HTTP", example = "500")
    private HttpStatus httpStatus;

    public CustomException(String msg, HttpStatus httpStatus) {
        this.msg = msg;
        this.httpStatus = httpStatus;
    }

}