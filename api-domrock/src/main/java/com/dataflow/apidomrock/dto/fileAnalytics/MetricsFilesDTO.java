package com.dataflow.apidomrock.dto.fileAnalytics;

import java.util.Map;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Schema(description = "DTO para requisição de métricas de arquivos")
public class MetricsFilesDTO {
    @Schema(description = "Nome da organização", example = "Empresa 1")
    private String organizacao;
    @Schema(description = "Listagem de etapas de cada organização", example = "10")
    private Map<String, Integer> etapas;
}
