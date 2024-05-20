package com.dataflow.apidomrock.dto.createHash;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "DTO para requisição de criação de hash")
@JsonIgnoreProperties(ignoreUnknown = true)
public record RequestArquivoDTO(
        @JsonAlias("usuario") @Schema(description = "Nome do usuário", example = "User") String usuario,
        @JsonAlias("arquivo") @Schema(description = "nome do arquivo", example = "Arquivo 1") String nomeArquivo,
        @JsonAlias("cnpj") @Schema(description = "Cnpj da instituição responsável pelo arquivo", example = "99.205.190/0001-40") String cnpj) {
}
