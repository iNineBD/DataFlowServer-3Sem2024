package com.dataflow.apidomrock.dto.fileAnalytics;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
public class MetricsFilesDTO {
    private String organizacao;
    private Map<String, Integer> etapas;
}
