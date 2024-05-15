package com.dataflow.apidomrock.dto.gethash;

import com.dataflow.apidomrock.entities.database.Metadata;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record ResponseNomeMetadataDTO(@JsonAlias("nome") String nome) {

    public ResponseNomeMetadataDTO(Metadata metadata){
        this(metadata.getNome());
    }
}
