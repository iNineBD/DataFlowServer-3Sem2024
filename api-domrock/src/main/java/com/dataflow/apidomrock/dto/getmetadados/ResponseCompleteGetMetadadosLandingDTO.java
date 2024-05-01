package com.dataflow.apidomrock.dto.getmetadados;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ResponseCompleteGetMetadadosLandingDTO {
    private ResponseBodyGetMetadadosDTO aboutMetadados;
    private String lastObs;
}
