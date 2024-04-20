package com.dataflow.apidomrock.dto.homedados;

import com.dataflow.apidomrock.entities.database.Arquivo;
import com.fasterxml.jackson.annotation.JsonAlias;

public record ResponseArquivosDTO(@JsonAlias("nomeArquivo")String nome,

                                  @JsonAlias("organizacao") String organizacao,
                                  @JsonAlias("status") String status) {

    public ResponseArquivosDTO(Arquivo arquivo){
        this(arquivo.getNomeArquivo(),arquivo.getOrganizacao().getNome(), arquivo.getStatus());
    }
}
