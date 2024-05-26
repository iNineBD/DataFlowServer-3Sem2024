package com.dataflow.apidomrock.dto.log;

import com.dataflow.apidomrock.entities.database.Log;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;


@JsonIgnoreProperties(ignoreUnknown = true)
public record ResponseLog(@JsonAlias("organizacao") String organizacao,
                          @JsonAlias("nomeArquivo") String nomeArquivo,
                          @JsonAlias ("dataHora") String dataHora,
                          @JsonAlias("observacao") String observacao,
                          @JsonAlias("estagio")String estagio,
                          @JsonAlias("acao")String acao,
                          @JsonAlias("nomeUsuario") String nomeUsuario,
                          @JsonAlias("emailUsuario")String emailUsuario) {
}