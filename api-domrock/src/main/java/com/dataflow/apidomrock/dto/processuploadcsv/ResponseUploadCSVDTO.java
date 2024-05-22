package com.dataflow.apidomrock.dto.processuploadcsv;

import java.util.List;

import com.dataflow.apidomrock.dto.entitiesdto.MetadataDTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(name = "ResponseUploadCSVDTO", description = "Response para realizar o upload de um arquivo CSV")
public class ResponseUploadCSVDTO {
    @Schema(description = "Nome do arquivo", example = "Arquivo01")
    private String fileName;
    @Schema(description = "Tamanho do arquivo", example = "10mb")
    private Double fileSize;
    @Schema(description = "Listagem de metadados do arquivo", example = "coluna01, coluna02 ...")
    private List<MetadataDTO> columns;
}
