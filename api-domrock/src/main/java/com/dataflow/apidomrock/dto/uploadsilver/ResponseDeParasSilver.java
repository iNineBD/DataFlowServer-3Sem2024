package com.dataflow.apidomrock.dto.uploadsilver;

import java.util.List;

import com.dataflow.apidomrock.dto.visualizeDePara.MetadadosDeParaVisualize;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.swagger.v3.oas.annotations.media.Schema;

@JsonIgnoreProperties(ignoreUnknown = true)
@Schema(description = "DTO de response para dados dePara ")
public record ResponseDeParasSilver(
        @JsonAlias("email") @Schema(description = "e-mail associado ao arquivo", example = "user@gmail.com") String email,
        @JsonAlias("arquivo") @Schema(description = "Nome arquivo em questão", example = "Arquivo01") String arquivo,
        @JsonAlias("cnpj") @Schema(description = "Cnpj da organização associada ao arquivo", example = "99.205.190/0001-40") String cnpj,
        @JsonAlias("metadados") @Schema(description = "Listagem de metadados associados ao arquivo", example = "coluna01, coluna02") List<MetadadosDeParaVisualize> metadados,
@JsonAlias("allMetadados") @Schema(description = "Listagem de metadados associados ao arquivo", example = "coluna01, coluna02") List<String> allMetadados){
}
