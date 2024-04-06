package com.dataflow.apidomrock.dto;

import com.dataflow.apidomrock.entities.database.Arquivo;
import com.dataflow.apidomrock.entities.database.NivelAcesso;
import com.dataflow.apidomrock.entities.database.Usuario;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record HomeResponseDTO(@JsonAlias("nivelAcesso") String nivel,
                              @JsonAlias("landing") List<ArquivoDTO> arquivosLanding,
                              @JsonAlias("bronze") List<ArquivoDTO> arquivosBronze,
                              @JsonAlias("silver") List<ArquivoDTO> arquivosSilver) {

}
