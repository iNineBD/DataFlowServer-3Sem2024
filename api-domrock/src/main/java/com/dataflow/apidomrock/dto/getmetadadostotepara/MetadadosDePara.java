package com.dataflow.apidomrock.dto.getmetadadostotepara;

import com.dataflow.apidomrock.entities.database.Metadata;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.swagger.v3.oas.annotations.media.Schema;

@JsonIgnoreProperties(ignoreUnknown = true)
@Schema(name = "MetadadosDePara", description = "Metadados de um de/para de um campo de um arquivo de metadados")
public record MetadadosDePara(@JsonAlias("nome") String nome) {

    public MetadadosDePara(Metadata metadata) {
        this(metadata.getNome());
    }
}
