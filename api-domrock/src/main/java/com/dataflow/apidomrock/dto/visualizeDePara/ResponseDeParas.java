package com.dataflow.apidomrock.dto.visualizeDePara;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record ResponseDeParas(@JsonAlias("email") String email,
                              @JsonAlias("arquivo") String arquivo,
                              @JsonAlias("cnpj") String cnpj,
                              @JsonAlias("metadadsos") List<MetadadosDeParaVisualize> metadados) {
}
