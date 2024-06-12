package com.dataflow.apidomrock.dto.log;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record RequestLogUsuario(@JsonAlias("email") String emailUsuario,
                            @JsonAlias("organizacao") String organizacaoUsuario){
}