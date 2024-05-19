package com.dataflow.apidomrock.dto.getmetadadostotepara;

import com.dataflow.apidomrock.entities.database.Metadata;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record MetadadosDePara(@JsonAlias("nome") String nome) {

    public MetadadosDePara(Metadata metadata){
        this(metadata.getNome());
    }
}
