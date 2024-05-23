package com.dataflow.apidomrock.dto.visualizeDePara;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.swagger.v3.oas.annotations.media.Schema;

@JsonIgnoreProperties(ignoreUnknown = true)
@Schema(description = "DTO para Requisição de dados de arquivo DePara")
public record RequestDadosToDePara(
        @JsonAlias("email") @Schema(description = "E-mail associado ao arquivo", example = "user@gmail.com") String email,
        @JsonAlias("arquivo") @Schema(description = "Nome do arquivo em questão", example = "Arquivo01") String arquivo,
        @JsonAlias("cnpj") @Schema(description = "CNPJ da organização associada ao arquivo", example = "99.205.190/0001-40") String cnpj) {
}
