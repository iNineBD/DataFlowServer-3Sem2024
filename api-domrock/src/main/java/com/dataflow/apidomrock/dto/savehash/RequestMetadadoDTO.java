package com.dataflow.apidomrock.dto.savehash;


import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record RequestMetadadoDTO(@JsonAlias("nome") String nome,
                                 @JsonAlias("valorPadrao") String valorPadrao) {
}
