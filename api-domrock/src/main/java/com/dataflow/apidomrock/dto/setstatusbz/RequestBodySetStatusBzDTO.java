package com.dataflow.apidomrock.dto.setstatusbz;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record RequestBodySetStatusBzDTO(@JsonAlias("email") String email,
                                        @JsonAlias("arquivo") String arquivo,
                                        @JsonAlias("aprovado") boolean salvar) {
}
