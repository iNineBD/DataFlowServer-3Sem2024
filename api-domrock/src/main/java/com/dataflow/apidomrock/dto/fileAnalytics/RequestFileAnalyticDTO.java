package com.dataflow.apidomrock.dto.fileAnalytics;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RequestFileAnalyticDTO {
    private String fileName;
    private String orgName;
}
