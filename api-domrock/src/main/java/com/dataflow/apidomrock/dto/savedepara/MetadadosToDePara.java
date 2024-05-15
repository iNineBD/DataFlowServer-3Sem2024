package com.dataflow.apidomrock.dto.savedepara;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record MetadadosToDePara(@JsonAlias("nome") String nome,
                                @JsonAlias("deParas") List<DeParas> deParas) {
}
