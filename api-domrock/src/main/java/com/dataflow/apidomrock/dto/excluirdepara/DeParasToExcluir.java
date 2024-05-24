package com.dataflow.apidomrock.dto.excluirdepara;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.swagger.v3.oas.annotations.media.Schema;

@JsonIgnoreProperties(ignoreUnknown = true)
@Schema(description = "DTO para requisição de exclusão de depara")
public record DeParasToExcluir(
        @JsonAlias("de") @Schema(description = "Metadado que será convertido", example = "Coluna01") String de,
        @JsonAlias("para") @Schema(description = "Metadado de saída, pós conversão", example = "ColunaTratada") String para) {
}
