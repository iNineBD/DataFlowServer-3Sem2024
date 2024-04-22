package com.dataflow.apidomrock.dto.savehash;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record RequestHashDTO(@JsonAlias("nomeArquivo") String nomeArquivo,
                             @JsonAlias("usuario") String usuario,
                             @JsonAlias("metadados")List<RequestMetadadoDTO> metadados) {
}
