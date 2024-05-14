package com.dataflow.apidomrock.dto.editdepara;

import com.dataflow.apidomrock.dto.savedepara.MetadadosToDePara;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record RequestEditDePara(@JsonAlias("email") String email,
                                @JsonAlias("arquivo") String nomeArquivo,
                                @JsonAlias("cnpj") String cnpj,
                                @JsonAlias("metadados") List<MetadadosToDePara> metadados) {
}
