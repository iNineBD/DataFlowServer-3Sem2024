package com.dataflow.apidomrock.dto.getmetadados;

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