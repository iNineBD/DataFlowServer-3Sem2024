package com.dataflow.apidomrock.dto.editarhash;

import java.util.List;

import com.dataflow.apidomrock.dto.savehash.RequestMetadadoDTO;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.swagger.v3.oas.annotations.media.Schema;

@JsonIgnoreProperties(ignoreUnknown = true)
@Schema(description = "DTO para requisição de edição de hash")
public record RequestEditHashDTO(
        @JsonAlias("nomeArquivo") @Schema(description = "Nome do arquivo", example = "Arquivo01") String nomeArquivo,
        @JsonAlias("usuario") @Schema(description = "Usuário", example = "User") String usuario,
        @JsonAlias("metadados") @Schema(description = "Metadados do arquivo", example = "Coluna X") List<RequestMetadadoDTO> metadados,
        @JsonAlias("cnpj") @Schema(description = "Cnpj da instituição responsável pelo arquivo", example = "99.205.190/0001-40") String cnpj) {
}
