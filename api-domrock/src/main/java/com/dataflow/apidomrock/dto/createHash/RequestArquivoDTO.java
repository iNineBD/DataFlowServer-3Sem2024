package com.dataflow.apidomrock.dto.createHash;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "DTO para requisição de criação de hash")
@JsonIgnoreProperties(ignoreUnknown = true)
public record RequestArquivoDTO(@JsonAlias("usuario") String usuario,
                @JsonAlias("arquivo") String nomeArquivo,
                @JsonAlias("cnpj") String cnpj) {
}
