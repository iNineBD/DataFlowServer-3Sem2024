package com.dataflow.apidomrock.dto.fileAnalytics;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ResponseFileAnalyticDTO <T> {

    private List<T> metricas;

}
