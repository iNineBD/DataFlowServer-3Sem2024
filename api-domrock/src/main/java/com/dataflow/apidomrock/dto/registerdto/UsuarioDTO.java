package com.dataflow.apidomrock.dto.registerdto;

import java.util.List;

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
@Schema(name = "UsuarioDTO", description = "DTO para cadastro de usuário")
public class UsuarioDTO {
    @Schema(description = "E-mail do usuário", example = "User@gmail.com")
    private String emailUsuario;
    @Schema(description = "Nível de acesso que este usuário tem a aplicação", example = "Full")
    private List<String> nivelAcesso;
    @Schema(description = "Cnpj da organização deste usuário", example = "99.205.190/0001-40")
    private String cnpj;
    @Schema(description = "nome da organização a qual esse usuário pertence", example = "don rock")
    private String organizacao;
}
