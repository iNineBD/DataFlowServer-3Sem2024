package com.dataflow.apidomrock.dto.deleteFile;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class RequestBodyDeleteFileDTO {
    private String usuario;
    private String nomeArquivo;
}
