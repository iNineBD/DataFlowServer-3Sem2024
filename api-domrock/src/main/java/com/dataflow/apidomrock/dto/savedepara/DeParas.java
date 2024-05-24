package com.dataflow.apidomrock.dto.savedepara;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.swagger.v3.oas.annotations.media.Schema;

@JsonIgnoreProperties(ignoreUnknown = true)
@Schema(description = "DTO para salvamento de deParas ")
public record DeParas(
        @Schema(description = "metadado pré tratamento 'DE' ", example = "coluna01") @JsonAlias("de") String de,
        @Schema(description = "metadado pós tratamento", example = "Coluna02") @JsonAlias("para") String para) {
}
