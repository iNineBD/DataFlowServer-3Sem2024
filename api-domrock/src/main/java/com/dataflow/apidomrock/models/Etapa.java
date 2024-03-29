package com.dataflow.apidomrock.models;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Etapa(@JsonAlias("etapa") String nomeEtapa,
                    @JsonAlias("arquivos")List<Arquivo> arquivos) {
}
