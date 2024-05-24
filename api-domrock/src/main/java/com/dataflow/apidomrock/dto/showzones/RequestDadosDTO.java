package com.dataflow.apidomrock.dto.showzones;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.swagger.v3.oas.annotations.media.Schema;

@JsonIgnoreProperties(ignoreUnknown = true)
@Schema(description = "DTO para requisição de dados de cada zona")
public record RequestDadosDTO(
        @JsonAlias("email") @Schema(description = "email do usuário", example = "user@gmail.com") String usuario,
        @JsonAlias("arquivo") @Schema(description = "Nome do arquivo em questão", example = "Arquivo01") String nomeArquivo,
        @JsonAlias("cnpjFile") @Schema(description = "CNPJ referente a organização responsável pelo arquivo", example = "99.205.190/0001-40") String cnpjFile) {
}
