package com.dataflow.apidomrock.dto;

import com.dataflow.apidomrock.models.Etapa;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record HomeResponseDTO(@JsonAlias("response")List<Etapa> listaArquivos) {
}
