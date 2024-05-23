package com.dataflow.apidomrock.dto.log;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record RequestLogDTO(@JsonAlias("organizacao") String organizacao,
                            @JsonAlias("nomeArquivo") String nomeArquivo,
                            @JsonAlias("usuario") String email){
}