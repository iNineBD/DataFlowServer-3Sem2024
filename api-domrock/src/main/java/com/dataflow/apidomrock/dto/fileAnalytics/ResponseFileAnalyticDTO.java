package com.dataflow.apidomrock.dto.fileAnalytics;

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
@Schema(description = "DTO para resposta de análise de arquivos")
public class ResponseFileAnalyticDTO<T> {
    @Schema(description = "Métricas de arquivos")
    private List<T> metricas;

}
