package com.dataflow.apidomrock.dto.getmetadados;

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
@Schema(description = "DTO para requisição de metadados")
public class RequestBodyGetMetadadosDTO {
    @Schema(description = "Nome do usuário em questão", example = "usuario")
    private String usuario;
    @Schema(description = "Nome do arquivo em questão", example = "arquivo")
    private String nomeArquivo;
    @Schema(description = "Cnpj referente a organização do arquivo em questão", example = "99.205.190/0001-40")
    private String cnpjFile;
}