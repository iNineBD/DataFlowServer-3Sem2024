package com.dataflow.apidomrock.dto.getmetadados;

import java.util.List;

import com.dataflow.apidomrock.dto.entitiesdto.MetadataDTO;

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
@Schema(description = "DTO para resposta de metadados")
public class ResponseBodyGetMetadadosDTO {
    @Schema(description = "Nome do usuário em questão", example = "usuario")
    private String usuario;
    @Schema(description = "Nome do arquivo em questão", example = "arquivo")
    private String nomeArquivo;
    @Schema(description = "Id do arquivo em questão", example = "123e4567-e89b-12d3-a456-426614174000")
    private Integer idArquivo;
    @Schema(description = "Metadados do arquivo", example = "coluna1,coluna2,coluna3")
    private List<MetadataDTO> metadados;
}
