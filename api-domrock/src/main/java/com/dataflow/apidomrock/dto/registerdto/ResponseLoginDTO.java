package com.dataflow.apidomrock.dto.registerdto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "ResponseLoginDTO", description = "DTO para resposta de autenticação de usuário")
public record ResponseLoginDTO(
        @Schema(description = "Token de autenticação", example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJ1c2VyIiwiaWF") String token,
        @Schema(description = "Nome do usuário", example = "Usu ario") String nomeUsuario,
        @Schema(description = "E-mail do usuário", example = "user@gmail.com") String email,
        @Schema(description = "Usuário é master", example = "false") boolean isMaster) {
}
