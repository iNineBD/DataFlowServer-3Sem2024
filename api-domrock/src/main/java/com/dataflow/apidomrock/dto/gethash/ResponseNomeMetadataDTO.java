package com.dataflow.apidomrock.dto.gethash;

import com.dataflow.apidomrock.entities.database.Metadata;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.swagger.v3.oas.annotations.media.Schema;

@JsonIgnoreProperties(ignoreUnknown = true)
@Schema(description = "DTO para resposta de hash para silver")
public record ResponseNomeMetadataDTO(@JsonAlias("nome") String nome) {

    public ResponseNomeMetadataDTO(Metadata metadata) {
        this(metadata.getNome());
    }
}
