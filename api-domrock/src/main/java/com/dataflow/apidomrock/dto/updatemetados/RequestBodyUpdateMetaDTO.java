package com.dataflow.apidomrock.dto.updatemetados;

import com.dataflow.apidomrock.dto.entitiesdto.MetadataDTO;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class RequestBodyUpdateMetaDTO {
    private String usuario;
    private String nomeArquivo;
    private List<MetadataDTO> metadados;
}
