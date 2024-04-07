package com.dataflow.apidomrock.dto.getMetadados;

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
    private List<ResponseBodyMetadadoDTO> metadados;
}

