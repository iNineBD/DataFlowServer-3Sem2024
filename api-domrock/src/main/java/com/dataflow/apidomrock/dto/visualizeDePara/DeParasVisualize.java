package com.dataflow.apidomrock.dto.visualizeDePara;

import com.dataflow.apidomrock.entities.database.DePara;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.swagger.v3.oas.annotations.media.Schema;

@JsonIgnoreProperties(ignoreUnknown = true)
@Schema(description = "DTO para visualização de deParas")
public record DeParasVisualize(
        @JsonAlias("de") @Schema(description = "Metadados pré tratamento", example = "coluna01") String de,
        @JsonAlias("para") @Schema(description = "metados pós tratamento", example = "coluna02") String para) {

    public DeParasVisualize(DePara dePara) {
        this(dePara.getDe(), dePara.getPara());
    }
}
