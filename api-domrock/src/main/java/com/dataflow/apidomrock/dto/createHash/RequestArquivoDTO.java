package com.dataflow.apidomrock.dto.createHash;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record RequestArquivoDTO(@JsonAlias("usuario") String usuario,
                                @JsonAlias("arquivo") String nomeArquivo,
                                @JsonAlias("cnpj") String cnpj) {
}
