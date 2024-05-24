package com.dataflow.apidomrock.dto.gethash;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.swagger.v3.oas.annotations.media.Schema;

@JsonIgnoreProperties(ignoreUnknown = true)
@Schema(description = "DTO para resposta de hash para silver")
public record ResponseHashToSilverDTO(
        @Schema(description = "Nome do arquivo", example = "arquivo1.txt") @JsonAlias("nomeArquivo") String nomeArquivo,
        @Schema(description = "usuario responsável pelo arquivo", example = "usuario") @JsonAlias("usuario") String usuario,
        @Schema(description = "Lista de metadados para hash", example = "metadados") @JsonAlias("metadados") List<ResponseNomeMetadataDTO> metadadosNoHash) {
}
