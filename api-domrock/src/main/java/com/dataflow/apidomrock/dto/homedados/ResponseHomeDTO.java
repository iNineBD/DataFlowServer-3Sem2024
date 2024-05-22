package com.dataflow.apidomrock.dto.homedados;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.swagger.v3.oas.annotations.media.Schema;

@JsonIgnoreProperties(ignoreUnknown = true)
@Schema(description = "Response para buscar uma lista de todos os arquivos")
public record ResponseHomeDTO(@JsonAlias("arquivos") List<ResponseArquivosDTO> allArquivos) {
}
