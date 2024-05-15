package com.dataflow.apidomrock.dto.excluirdepara;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record RequestExcluirDePara(@JsonAlias("email") String email,
                                   @JsonAlias("arquivo") String nomeArquivo,
                                   @JsonAlias("cnpj") String cnpj,
                                   @JsonAlias("metadados")List<MetadadosExcluirDePara> metadados) {
}
