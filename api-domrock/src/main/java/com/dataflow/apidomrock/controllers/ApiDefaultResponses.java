package com.dataflow.apidomrock.controllers;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Processamento efetuado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Erro no processamento"),
        @ApiResponse(responseCode = "500", description = "Erro interno no servidor"),
        @ApiResponse(responseCode = "401", description = "Não autorizado"),
        @ApiResponse(responseCode = "404", description = "Não encontrado")
})
public @interface ApiDefaultResponses {
}
