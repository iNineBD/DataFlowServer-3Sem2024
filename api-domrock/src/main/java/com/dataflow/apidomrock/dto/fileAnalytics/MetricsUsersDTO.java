package com.dataflow.apidomrock.dto.fileAnalytics;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Schema(description = "DTO para requisição de métricas de usuários")
public class MetricsUsersDTO {
    @Schema(description = "Nome da organização", example = "Empresa 1")
    private String organizacao;
    @Schema(description = "Quantidade de usuários", example = "10")
    private Integer qtd;
}
