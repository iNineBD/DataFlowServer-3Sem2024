package com.dataflow.apidomrock.dto.visualizeDePara;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record RequestDadosToDePara(@JsonAlias("email") String email,
                                   @JsonAlias("arquivo") String arquivo,
                                   @JsonAlias("cnpj") String cnpj) {
}
