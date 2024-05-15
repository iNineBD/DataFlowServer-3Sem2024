package com.dataflow.apidomrock.dto.excluirdepara;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DeParasToExcluir(@JsonAlias("de") String de,
                               @JsonAlias("para") String para) {
}
