package com.dataflow.apidomrock.dto.processuploadcsv;

import com.dataflow.apidomrock.entities.database.Metadata;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class ResponseUploadCSVDTO {
    private String fileName;
    private Double fileSize;
    private List<Metadata> columns;
}
