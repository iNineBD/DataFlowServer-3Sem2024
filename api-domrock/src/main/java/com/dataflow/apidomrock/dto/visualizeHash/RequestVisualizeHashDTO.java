package com.dataflow.apidomrock.dto.visualizeHash;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "DTO para requisição para visualização de hash")
@JsonIgnoreProperties(ignoreUnknown = true)
public record RequestVisualizeHashDTO(
        @JsonAlias("nomeArquivo") @Schema(description = "nome do arquivo", example = "Arquivo01") String nomeArquivo,
        @JsonAlias("usuario") @Schema(description = "usuário responsável pelo arquivo", example = "usu ario") String usuario,
        @JsonAlias("cnpj") @Schema(description = "cnpj da empresa associada ao arquivo", example = "99.205.190/0001-40") String cnpj) {
}
