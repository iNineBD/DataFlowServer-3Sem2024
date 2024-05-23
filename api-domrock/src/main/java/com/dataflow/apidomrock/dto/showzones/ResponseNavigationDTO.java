package com.dataflow.apidomrock.dto.showzones;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.swagger.v3.oas.annotations.media.Schema;

@JsonIgnoreProperties(ignoreUnknown = true)
@Schema(description = "DTO para response de navegação entre as zonas")
public record ResponseNavigationDTO(
        @JsonAlias("email") @Schema(description = "email do usuário navegando as zonas", example = "user@gmail.com") String email,
        @JsonAlias("acessoLanding") @Schema(description = "boleano para resposta de acesso a navegação do usuário para landing zone", example = "true") boolean acessoLanding,
        @JsonAlias("acessoBronze") @Schema(description = "boleano para resposta de acesso a navegação do usuário para bronze zone", example = "false") boolean acessoBronze,
        @JsonAlias("acessoSilver") @Schema(description = "boleano para resposta de acesso a navegação do usuário para silver zone", example = "false") boolean acessoSilver) {
}
