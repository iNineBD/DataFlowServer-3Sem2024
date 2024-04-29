package com.dataflow.apidomrock.dto.showzones;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record RequestDadosDTO(@JsonAlias("email") String usuario,
                              @JsonAlias("arquivo") String nomeArquivo) {
}
