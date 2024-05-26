package com.dataflow.apidomrock.dto.getmetadadostotepara;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.swagger.v3.oas.annotations.media.Schema;

@JsonIgnoreProperties(ignoreUnknown = true)
@Schema(name = "RequestMetaToDePara", description = "Request para buscar metadados de um de/para")
public record RequestMetaToDePara(
        @Schema(description = "email do responsável pelo arquivo", example = "user@gmail.com") @JsonAlias("email") String email,
        @Schema(description = "Nome do arquivo em questão", example = "arquivo01") @JsonAlias("arquivo") String arquivo,
        @Schema(description = "Cnpj da organização em responsável pelo arquivo", example = "99.205.190/0001-40") @JsonAlias("cnpj") String cnpj,
        @Schema(description = "lista de metadados referentes ao arqivo", example = "coluna01,coluna02") @JsonAlias("metadados") List<MetadadosDePara> listMetadados) {
}
