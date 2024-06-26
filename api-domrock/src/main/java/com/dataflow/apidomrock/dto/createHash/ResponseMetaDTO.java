package com.dataflow.apidomrock.dto.createHash;

import com.dataflow.apidomrock.entities.database.Metadata;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.swagger.v3.oas.annotations.media.Schema;

@JsonIgnoreProperties(ignoreUnknown = true)
@Schema(description = "DTO para criação de objeto metadado")
public record ResponseMetaDTO(@JsonAlias("nome") String nome,
        @JsonAlias("valorPadrao") String valorPadrao,
        @JsonAlias("nomeTipo") String tipo,
        @JsonAlias("descricao") String descricao,
        @JsonAlias("ativo") boolean ativo) {

    public ResponseMetaDTO(Metadata meta) {
        this(meta.getNome(), meta.getValorPadrao(), meta.getTipo(), meta.getDescricao(), meta.getIsAtivo());
    }
}
