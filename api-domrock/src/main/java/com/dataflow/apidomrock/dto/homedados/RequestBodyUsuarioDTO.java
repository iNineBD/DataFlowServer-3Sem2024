package com.dataflow.apidomrock.dto.homedados;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record RequestBodyUsuarioDTO(@JsonAlias("email") String email) {
}
