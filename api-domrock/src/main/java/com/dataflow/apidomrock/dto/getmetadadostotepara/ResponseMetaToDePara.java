package com.dataflow.apidomrock.dto.getmetadadostotepara;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record ResponseMetaToDePara(@JsonAlias("email") String email,
                                   @JsonAlias("arquivo") String arquivo,
                                   @JsonAlias("cnpj") String cnpj,
                                   @JsonAlias("metadados") List<MetadadosDePara> listMetadados) {
}
