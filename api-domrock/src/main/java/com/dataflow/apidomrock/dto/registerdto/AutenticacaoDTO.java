package com.dataflow.apidomrock.dto.registerdto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Schema(name = "AutenticacaoDTO", description = "DTO para autenticação de usuário")
public class AutenticacaoDTO {
    @Schema(description = "Login do usuário", example = "user")
    private String login;
    @Schema(description = "Senha do usuário", example = "123456")
    private String senha;

}
