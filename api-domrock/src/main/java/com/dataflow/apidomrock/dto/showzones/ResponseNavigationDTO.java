package com.dataflow.apidomrock.dto.showzones;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record ResponseNavigationDTO(@JsonAlias("email") String email,
                                    @JsonAlias("acessoLanding") boolean acessoLanding,
                                    @JsonAlias("acessoBronze") boolean acessoBronze,
                                    @JsonAlias("acessoSilver") boolean acessoSilver) {
}
