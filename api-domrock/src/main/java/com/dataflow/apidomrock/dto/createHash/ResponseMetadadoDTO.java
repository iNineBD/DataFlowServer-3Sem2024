package com.dataflow.apidomrock.dto.createHash;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.swagger.v3.oas.annotations.media.Schema;

@JsonIgnoreProperties(ignoreUnknown = true)
@Schema(description = "DTO para resposta de objetos json para metadados")
public record ResponseMetadadoDTO(@JsonAlias("Metadados") List<ResponseMetaDTO> meta) {
}
