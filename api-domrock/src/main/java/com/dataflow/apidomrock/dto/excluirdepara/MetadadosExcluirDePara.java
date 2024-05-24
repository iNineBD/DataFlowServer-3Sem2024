package com.dataflow.apidomrock.dto.excluirdepara;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.swagger.v3.oas.annotations.media.Schema;

@JsonIgnoreProperties(ignoreUnknown = true)
public record MetadadosExcluirDePara(
        @JsonAlias("nome") @Schema(description = "Nome do arquivo", example = "Arquivo01") String nome,
        @JsonAlias("deParas") @Schema(description = "lista contendo todas conversões do arquivo em questão", example = "coluna01,coluna02") List<DeParasToExcluir> deParas) {
}
