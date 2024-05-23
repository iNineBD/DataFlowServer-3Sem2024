package com.dataflow.apidomrock.dto.setstatusbz;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.swagger.v3.oas.annotations.media.Schema;

@JsonIgnoreProperties(ignoreUnknown = true)
@Schema(description = "DTO de requisição para definição de status de arquivo da bronze zone")
public record RequestBodySetStatusBzDTO(
        @JsonAlias("usuario") @Schema(description = "nome do usuário", example = "usu ario") String usuario,
        @JsonAlias("arquivo") @Schema(description = "nome do arquivo em questão", example = "Arquivo01") String arquivo,
        @JsonAlias("observacao") @Schema(description = "Observações do arquivo", example = "Arquivo para testes") String obs,
        @JsonAlias("aprovado") @Schema(description = "Booleano para salvamento e aprovação do arquivo", example = "true") boolean salvar,
        @JsonAlias("cnpjFile") @Schema(description = "Cnpj da empresa responsável pelo arquivo", example = "99.205.190/0001-40") String cnpjFile) {
}
