package com.dataflow.apidomrock.dto.visualizeHash;

import java.util.List;

import com.dataflow.apidomrock.dto.savehash.RequestMetadadoDTO;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.swagger.v3.oas.annotations.media.Schema;

@JsonIgnoreProperties(ignoreUnknown = true)
@Schema(description = "DTO para response do hash")
public record ResponseHashDTO(
        @JsonAlias("nomeArquivo") @Schema(description = "Nome do arquivo", example = "arquivo01") String nomeArquivo,
        @JsonAlias("usuario") @Schema(description = "nome do usuário", example = "Usu ario") String usuario,
        @JsonAlias("metadados") @Schema(description = "listagem de metadados que compoem o hash", example = "coluna01, coluna02") List<RequestMetadadoDTO> metadadosNoHash,
        @JsonAlias("metadados") @Schema(description = "Listagem de colunas que não compoem o hash ", example = "coluna03, coluna04") List<RequestMetadadoDTO> metadadosForaDoHash,
        @JsonAlias("observacao") String observacao) {

}
