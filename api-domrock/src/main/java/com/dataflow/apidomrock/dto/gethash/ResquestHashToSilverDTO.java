package com.dataflow.apidomrock.dto.gethash;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.swagger.v3.oas.annotations.media.Schema;

@JsonIgnoreProperties(ignoreUnknown = true)
public record ResquestHashToSilverDTO(
        @JsonAlias("usuario") @Schema(description = "nome do usuário em questão", example = "User") String usuario,
        @JsonAlias("arquivo") @Schema(description = "Nome do Arquivo em questão", example = "Arquivo em questão") String nomeArquivo,
        @JsonAlias("cnpjOrganizacao") @Schema(description = "Cnpj da organização em questão", example = "99.205.190/0001-40") String cnpjOrg) {
}
