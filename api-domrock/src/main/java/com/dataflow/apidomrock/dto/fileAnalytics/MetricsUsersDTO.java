package com.dataflow.apidomrock.dto.fileAnalytics;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
public class MetricsUsersDTO {
    private String organizacao;
    private Integer qtd;
}
