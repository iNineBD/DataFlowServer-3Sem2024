package com.dataflow.apidomrock.dto.uploadsilver;
import java.util.List;

import com.dataflow.apidomrock.dto.visualizeDePara.DeParasVisualize;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.swagger.v3.oas.annotations.media.Schema;

@JsonIgnoreProperties(ignoreUnknown = true)
@Schema(description = "DTO para visualização de metadados deParas")
public record MetadadosDeParaVisualizeSilver(
        @JsonAlias("nome") @Schema(description = "Nome do arquivo em questão", example = "Arquivo01") String nome,
        @JsonAlias("deParas") @Schema(description = "Lista de deParas do arquivo em questão", example = "coluna01, coluna02") List<DeParasVisualize> dePara) {

    public MetadadosDeParaVisualizeSilver(List<DeParasVisualize> dePara, String nomeMetadado) {
        this(nomeMetadado, dePara);
    }
}
