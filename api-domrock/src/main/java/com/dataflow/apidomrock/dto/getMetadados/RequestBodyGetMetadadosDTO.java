package com.dataflow.apidomrock.dto.getMetadados;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class RequestBodyGetMetadadosDTO {
    private String usuario;
    private String nomeArquivo;
}