package com.dataflow.apidomrock.dto.processuploadcsv;

import com.dataflow.apidomrock.dto.entitiesdto.MetadataDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ResponseUploadCSVDTO {
    private String fileName;
    private Double fileSize;
    private List<MetadataDTO> columns;
}
