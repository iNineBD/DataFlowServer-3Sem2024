package com.dataflow.apidomrock.dto.updatemetados;

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
@Schema(description = "DTO de requisição de metadados para atualização")
public class RequestBodyUpdateMetaDTO {
    @Schema(description = "Nome do usuário", example = "usu ario")
    private String usuario;
    @Schema(description = "Nome do arquivo em questão", example = "Arquivo01")
    private String nomeArquivo;
    @Schema(description = "Lista de metadados do arquivo em questão", example = "coluna01, coluna02")
    private List<MetadataDTO> metadados;
}
