package com.dataflow.apidomrock.dto.userlogout;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record LogoutDTO(@JsonAlias("usuario") String email) {
}
