package com.dataflow.apidomrock.dto.gethash;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record ResquestHashToSilverDTO(@JsonAlias("usuario") String usuario,
                                      @JsonAlias("arquivo") String nomeArquivo,
                                      @JsonAlias("cnpjOrganizacao") String cnpjOrg) {
}
