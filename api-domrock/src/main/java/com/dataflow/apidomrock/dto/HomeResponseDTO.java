package com.dataflow.apidomrock.dto;

import com.dataflow.apidomrock.dto.ExibiHome.ArquivoDTO;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record HomeResponseDTO(@JsonAlias("nivelAcesso") String nivel,
                              @JsonAlias("landing") List<ArquivoDTO> arquivosLanding,
                              @JsonAlias("bronze") List<ArquivoDTO> arquivosBronze,
                              @JsonAlias("silver") List<ArquivoDTO> arquivosSilver) {

}
