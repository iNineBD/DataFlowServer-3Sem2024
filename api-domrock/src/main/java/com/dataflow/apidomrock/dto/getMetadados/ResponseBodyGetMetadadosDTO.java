package com.dataflow.apidomrock.dto.getMetadados;

import com.dataflow.apidomrock.dto.entitiesDTO.MetadataDTO;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ResponseBodyGetMetadadosDTO {
    private String usuario;
    private String nomeArquivo;
    private List<MetadataDTO> metadados;
}

