package com.dataflow.apidomrock.dto.mapperYaml;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@JsonIgnoreProperties(ignoreUnknown = true)
public record RequestMapperDTO(@JsonAlias("organizacao") String organizacao,
                            @JsonAlias("nomeArquivo") String nomeArquivo,
                            @JsonAlias("usuario") String email){
}