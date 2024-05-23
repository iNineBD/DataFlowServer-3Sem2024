package com.dataflow.apidomrock.dto.savehash;

import com.dataflow.apidomrock.entities.database.Metadata;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.swagger.v3.oas.annotations.media.Schema;

@JsonIgnoreProperties(ignoreUnknown = true)
@Schema(description = "DTO responsável pela requisição de metadados de arquivo")
public record RequestMetadadoDTO(
        @JsonAlias("nome") @Schema(description = "Nome da coluna", example = "coluna01") String nome,
        @JsonAlias("valorPadrao") @Schema(description = "valor padrão que a coluna recebe", example = "Números") String valorPadrao) {

    public RequestMetadadoDTO(Metadata metadata) {
        this(metadata.getNome(), metadata.getValorPadrao());
    }
}
