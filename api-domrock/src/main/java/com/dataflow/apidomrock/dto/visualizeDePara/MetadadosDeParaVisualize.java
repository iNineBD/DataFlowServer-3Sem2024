package com.dataflow.apidomrock.dto.visualizeDePara;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record MetadadosDeParaVisualize(@JsonAlias("nome") String nome,
                                       @JsonAlias("deParas") List<DeParasVisualize> deParas) {

    public MetadadosDeParaVisualize(List<DeParasVisualize> dePara, String nomeMetadado){
        this(nomeMetadado, dePara);
    }
}
