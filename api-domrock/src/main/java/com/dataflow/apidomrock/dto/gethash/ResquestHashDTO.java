package com.dataflow.apidomrock.dto.gethash;

import com.dataflow.apidomrock.entities.database.Usuario;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record ResquestHashDTO(@JsonAlias("usuario") String usuario,
                              @JsonAlias("arquivo") String nomeArquivo,
                              @JsonAlias("organizacao") int cnpjOrg) {
}
