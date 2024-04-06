package com.dataflow.apidomrock.dto.GetArquivosUsuario;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record UsuarioDTO(@JsonAlias("email") String email) {
}
