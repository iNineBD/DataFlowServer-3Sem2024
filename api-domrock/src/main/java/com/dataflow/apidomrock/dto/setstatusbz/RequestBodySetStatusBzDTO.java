package com.dataflow.apidomrock.dto.setstatusbz;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record RequestBodySetStatusBzDTO(@JsonAlias("usuario") String usuario,
                                        @JsonAlias("arquivo") String arquivo,
                                        @JsonAlias("observacao") String obs,
                                        @JsonAlias("aprovado") boolean salvar,
                                        @JsonAlias("cnpjFile") String cnpjFile) {
}
