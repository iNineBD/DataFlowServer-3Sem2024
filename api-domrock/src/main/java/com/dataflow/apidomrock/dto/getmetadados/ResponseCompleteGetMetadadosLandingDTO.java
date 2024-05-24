package com.dataflow.apidomrock.dto.getmetadados;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "DTO para resposta de metadados refernete a Landing zone")
public class ResponseCompleteGetMetadadosLandingDTO {
    @Schema(description = "descrição sobre os metadados do arquivo", example = "O arquivo abaixo devera ser utilizado em (...) ")
    private ResponseBodyGetMetadadosDTO aboutMetadados;
    @Schema(description = "observação de reprova do arquivo em questão", example = "O arquivo foi reprovado por não conter a coluna X")
    private String lastObs;
}
