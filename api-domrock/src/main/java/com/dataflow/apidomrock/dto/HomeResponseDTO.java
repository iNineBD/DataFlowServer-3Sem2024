package com.dataflow.apidomrock.dto;

import com.dataflow.apidomrock.entities.database.Arquivo;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record HomeResponseDTO(@JsonAlias("landing") List<Arquivo> arquivosLanding,
                              @JsonAlias("bronze") List<Arquivo> arquivosBronze,
                              @JsonAlias("silver") List<Arquivo> arquivosSilver) {
}
