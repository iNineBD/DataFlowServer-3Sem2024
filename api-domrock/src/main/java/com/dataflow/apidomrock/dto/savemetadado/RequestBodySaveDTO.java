package com.dataflow.apidomrock.dto.savemetadado;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description="DTO de requisição para salvamento de metadados")
public class RequestBodySaveDTO {
    @Schema(description = "Nome do arquivo", example = "arquivo01")
    private String nomeArquivo;
    @Schema(description = "Usuário que está salvando os metadados", example = "user01")
    private String usuario;
    @Schema(description = "Lista de metadados a serem salvos", example = "Coluna01, Coluna02, Coluna03")
    private List<RequestBodySaveMetadadoDTO> metadados;

}
