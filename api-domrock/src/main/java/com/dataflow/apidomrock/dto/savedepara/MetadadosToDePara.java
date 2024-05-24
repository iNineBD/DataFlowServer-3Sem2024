package com.dataflow.apidomrock.dto.savedepara;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.swagger.v3.oas.annotations.media.Schema;

@JsonIgnoreProperties(ignoreUnknown = true)
@Schema(description = "DTO para requisição de metadados dePara de cada arquivo")
public record MetadadosToDePara(
        @Schema(description = "Nome do arquivo", example = "Arquivo01'") @JsonAlias("nome") String nome,
        @Schema(description = "Lista de metadados deParas do arquivo em questão") @JsonAlias("deParas") List<DeParas> deParas) {
}
