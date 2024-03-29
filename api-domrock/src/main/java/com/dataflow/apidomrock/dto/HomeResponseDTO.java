package com.dataflow.apidomrock.dto;

import com.dataflow.apidomrock.entities.database.Arquivo;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record HomeResponseDTO(@JsonAlias("landing") List<ArquivoDTO> arquivosLanding,
                              @JsonAlias("bronze") List<ArquivoDTO> arquivosBronze,
                              @JsonAlias("silver") List<ArquivoDTO> arquivosSilver) {
}
