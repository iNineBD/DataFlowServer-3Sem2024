package com.dataflow.apidomrock.dto;

import com.dataflow.apidomrock.entities.database.Metadata;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class UploadCSVResponseDTO {
    private String fileName;
    private Double fileSize;
    private List<Metadata> columns;
}
