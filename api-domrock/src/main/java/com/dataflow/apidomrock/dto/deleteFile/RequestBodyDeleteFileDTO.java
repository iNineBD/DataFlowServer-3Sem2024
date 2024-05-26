package com.dataflow.apidomrock.dto.deleteFile;

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
@Schema(description = "DTO para requisição de exclusão de arquivo")
public class RequestBodyDeleteFileDTO {
    @Schema(description = "usuário", example = "User")
    private String usuario;
    @Schema(description = "nome do arquivo", example = "Arquivo 1")
    private String nomeArquivo;
    @Schema(description = "Cnpj da instituição responsável pelo arquivo", example = "99.205.190/0001-40")
    private String usuarioOrg;
}
