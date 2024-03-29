package com.dataflow.apidomrock.models;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Arquivo(@JsonAlias("nomeArquivo") String nomeArquivo) {
}
