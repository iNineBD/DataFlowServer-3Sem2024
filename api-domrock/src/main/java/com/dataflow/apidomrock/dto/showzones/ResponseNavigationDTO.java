package com.dataflow.apidomrock.dto.showzones;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record ResponseNavigationDTO(@JsonAlias("email") String email,
                                    @JsonAlias("acessoLanding") AcessoZonas acessoLanding,
                                    @JsonAlias("acessoBronze") AcessoZonas acessoBronze,
                                    @JsonAlias("acessoSilver") AcessoZonas acessoSilver) {
}
