package com.dataflow.apidomrock.dto.setstatussz;


import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record RequestBodySetStatusSz(@JsonAlias("usuario") String usuario,
                                     @JsonAlias("arquivo") String arquivo,
                                     @JsonAlias("observacao") String obs,
                                     @JsonAlias("aprovado") boolean salvar) {
}
