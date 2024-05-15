package com.dataflow.apidomrock.dto.excluirdepara;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record MetadadosExcluirDePara(@JsonAlias("nome") String nome,
                                     @JsonAlias("deParas") List<DeParasToExcluir> deParas) {
}
