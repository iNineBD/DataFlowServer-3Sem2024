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
@Schema(name = "validacaoDTO", description = "DTO para realização da validação do Login")
public class ValidacaoDTO {
    @Schema(description = "e-mail do usuário", example = "user@gmail.com")
    private String emailUsuario;
    @Schema(description = "Nome do usuário", example = "usu ario")
    private String nome;
    @Schema(description = "Token gerado do usuário", example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJ1c2VyIiwiaWF")
    private String token;
    @Schema(description = "Senha do usuário em questão", example = "user123")
    private String senha;

}
