package com.dataflow.apidomrock.dto.visualizeDePara;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.swagger.v3.oas.annotations.media.Schema;

@JsonIgnoreProperties(ignoreUnknown = true)
@Schema(description = "DTO para visualização de metadados deParas")
public record MetadadosDeParaVisualize(
        @JsonAlias("nome") @Schema(description = "Nome do arquivo em questão", example = "Arquivo01") String nome,
        @JsonAlias("deParas") @Schema(description = "Lista de deParas do arquivo em questão", example = "coluna01, coluna02") List<DeParasVisualize> dePara) {

    public MetadadosDeParaVisualize(List<DeParasVisualize> dePara, String nomeMetadado) {
        this(nomeMetadado, dePara);
    }
}
