package com.dataflow.apidomrock.dto.editarhash;

import com.dataflow.apidomrock.dto.savehash.RequestMetadadoDTO;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record RequestEditHashDTO(@JsonAlias("nomeArquivo") String nomeArquivo,
                                 @JsonAlias("usuario") String usuario,
                                 @JsonAlias("metadados") List<RequestMetadadoDTO> metadados,
                                 @JsonAlias("cnpj") String cnpj) {
}
