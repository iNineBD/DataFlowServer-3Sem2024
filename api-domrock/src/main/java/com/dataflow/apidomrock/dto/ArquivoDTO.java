package com.dataflow.apidomrock.dto;

import com.dataflow.apidomrock.entities.database.Arquivo;
import com.dataflow.apidomrock.entities.database.Status;
import com.fasterxml.jackson.annotation.JsonAlias;

public record ArquivoDTO(@JsonAlias("nomeArquivo")String nome,

                         @JsonAlias("organizacao") String organizacao,
                         @JsonAlias("status") String status) {

    public ArquivoDTO(Arquivo arquivo){
        this(arquivo.getNomeArquivo(),arquivo.getOrganizacao().getNome(), arquivo.getStatus().getStatus());
    }
}