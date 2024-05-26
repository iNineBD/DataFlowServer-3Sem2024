package com.dataflow.apidomrock.dto.homedados;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.swagger.v3.oas.annotations.media.Schema;

@JsonIgnoreProperties(ignoreUnknown = true)
@Schema(name = "RequestBodyUsuarioDTO", description = "Request para buscar dados de um usuário")
public record RequestBodyUsuarioDTO(
        @JsonAlias("email") @Schema(description = "e-mail do usuário", example = "user@gmail.com") String email) {
}
