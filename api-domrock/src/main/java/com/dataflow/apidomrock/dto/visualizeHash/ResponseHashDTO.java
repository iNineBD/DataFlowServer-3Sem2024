package com.dataflow.apidomrock.dto.visualizeHash;

import com.dataflow.apidomrock.dto.editarhash.RequestEditHashDTO;
import com.dataflow.apidomrock.dto.savehash.RequestMetadadoDTO;
import com.dataflow.apidomrock.entities.database.Metadata;
import com.dataflow.apidomrock.entities.database.Usuario;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record ResponseHashDTO(@JsonAlias("nomeArquivo") String nomeArquivo,
                              @JsonAlias("usuario") String usuario,
                              @JsonAlias("metadados") List<RequestMetadadoDTO> metadados,
                              @JsonAlias("observacao") String observacao) {

}
