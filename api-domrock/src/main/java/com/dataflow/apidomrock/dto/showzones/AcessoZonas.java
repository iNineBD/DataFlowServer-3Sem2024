package com.dataflow.apidomrock.dto.showzones;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record AcessoZonas(@JsonAlias("visivel") boolean visivel,
                          @JsonAlias("acesso") boolean acesso) {
}
