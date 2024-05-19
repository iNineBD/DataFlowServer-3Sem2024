package com.dataflow.apidomrock.dto.visualizeHash;

import com.dataflow.apidomrock.dto.savehash.RequestMetadadoDTO;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record RequestVisualizeHashDTO(@JsonAlias("nomeArquivo") String nomeArquivo,
                                      @JsonAlias("usuario") String usuario,
                                      @JsonAlias("cnpj") String cnpj) {
}
