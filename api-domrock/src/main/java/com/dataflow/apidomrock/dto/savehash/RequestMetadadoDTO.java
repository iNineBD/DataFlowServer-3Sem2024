package com.dataflow.apidomrock.dto.savehash;


import com.dataflow.apidomrock.entities.database.Metadata;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record RequestMetadadoDTO(@JsonAlias("nome") String nome,
                                 @JsonAlias("valorPadrao") String valorPadrao) {

    public RequestMetadadoDTO(Metadata metadata){
        this(metadata.getNome(), metadata.getValorPadrao());
    }
}
