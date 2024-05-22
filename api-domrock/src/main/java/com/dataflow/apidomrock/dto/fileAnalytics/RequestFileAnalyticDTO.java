package com.dataflow.apidomrock.dto.fileAnalytics;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "DTO para requisição de arquivos")
public class RequestFileAnalyticDTO {
    @Schema(description = "Tipo de busca", example = "cnpj")
    private String searchType;
    @Schema(description = "Valor da busca", example = "99.205.190/0001-40")
    private String cnpj;
}
