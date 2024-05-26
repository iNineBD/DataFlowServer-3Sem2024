package com.dataflow.apidomrock.dto.excluirdepara;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.swagger.v3.oas.annotations.media.Schema;

@JsonIgnoreProperties(ignoreUnknown = true)
@Schema(description = "DTO para requisição de exclusão de depara")
public record RequestExcluirDePara(
        @JsonAlias("email") @Schema(description = "email do responsável pelo arquivo", example = "user@gmail.com") String email,
        @JsonAlias("arquivo") @Schema(description = "Arquivo em questão", example = "Arquivo01") String nomeArquivo,
        @JsonAlias("cnpj") @Schema(description = "Cnpj da organização responsável pelo arquivo em questão", example = "99.205.190/0001-40") String cnpj,
        @JsonAlias("metadados") @Schema(description = "Lista de metadados de DePara para exclusão", example = "coluna01, coluna02") List<MetadadosExcluirDePara> metadados) {
}
