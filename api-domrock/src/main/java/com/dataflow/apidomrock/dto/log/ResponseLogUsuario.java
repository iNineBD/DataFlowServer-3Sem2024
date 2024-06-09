package com.dataflow.apidomrock.dto.log;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record ResponseLogUsuario(@JsonAlias("organizacao") String organizacao,
                          @JsonAlias ("dataHora") String dataHora,
                          @JsonAlias("acao")String acao,
                          @JsonAlias("nomeUsuario") String nomeUsuario,
                          @JsonAlias("emailUsuario")String emailUsuario) {
}