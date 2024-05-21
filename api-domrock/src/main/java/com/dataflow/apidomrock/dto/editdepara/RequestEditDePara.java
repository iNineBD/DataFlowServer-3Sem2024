package com.dataflow.apidomrock.dto.editdepara;

import java.util.List;

import com.dataflow.apidomrock.dto.savedepara.MetadadosToDePara;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.swagger.v3.oas.annotations.media.Schema;

@JsonIgnoreProperties(ignoreUnknown = true)
@Schema(description = "DTO para requisição de edição de depara")
public record RequestEditDePara(@JsonAlias("email") String email,
                @JsonAlias("arquivo") @Schema(description = "nome do arquivo", example = "arquivo01") String nomeArquivo,
                @JsonAlias("cnpj") @Schema(description = "cnpj da empresa responsável pelo arquivo", example = "99.205.190/0001-40") String cnpj,
                @JsonAlias("metadados") @Schema(description = "lista de metadados do arquivo", example = "coluna_1;coluna_2") List<MetadadosToDePara> metadados) {
}
