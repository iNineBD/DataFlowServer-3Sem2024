package com.dataflow.apidomrock.dto.getmetadadostotepara;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.swagger.v3.oas.annotations.media.Schema;

@JsonIgnoreProperties(ignoreUnknown = true)
@Schema(name = "ResponseMetaToDePara", description = "Response para buscar metadados de um de/para")
public record ResponseMetaToDePara(

        @JsonAlias("email") @Schema(description = "email do responsável pelo arquivo em questão", example = "user@gmail.com") String email,
        @JsonAlias("arquivo") @Schema(description = "nome do arquivo em questão", example = "arquivo01") String arquivo,
        @JsonAlias("cnpj") @Schema(description = "cnpj da organização responsável pelo arquivo em questão", example = "99.205.190/0001-40") String cnpj,
        @JsonAlias("metadados") @Schema(description = "lista de metadados") List<MetadadosDePara> listMetadados) {
}
