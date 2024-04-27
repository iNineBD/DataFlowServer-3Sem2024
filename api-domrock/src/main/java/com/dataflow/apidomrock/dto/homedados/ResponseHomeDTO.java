package com.dataflow.apidomrock.dto.homedados;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record ResponseHomeDTO(@JsonAlias("nivelAcesso") String nivel,
                              @JsonAlias("arquivos") List<ResponseArquivosDTO> allArquivos) {
}
