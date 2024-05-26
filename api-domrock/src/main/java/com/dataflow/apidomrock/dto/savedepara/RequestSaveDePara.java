package com.dataflow.apidomrock.dto.savedepara;

import java.util.List;

import com.dataflow.apidomrock.dto.editdepara.RequestEditDePara;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.swagger.v3.oas.annotations.media.Schema;

@JsonIgnoreProperties(ignoreUnknown = true)
@Schema(description = "DTO de requisição para salvamento de deParas")
public record RequestSaveDePara(
        @Schema(description = "Email do usuário responsável pelo arquivo", example = "user@gmail.com") @JsonAlias("email") String email,
        @Schema(description = "Nome do arquivo") @JsonAlias("arquivo") String nomeArquivo,
        @Schema(description = "CNPJ da organização responsável pelo arquivo", example = "99.205.190/0001-40") @JsonAlias("cnpj") String cnpj,
        @Schema(description = "metadados do arquivo", example = "coluna01, coluna02, coluna03") @JsonAlias("metadados") List<MetadadosToDePara> metadados) {

    public RequestSaveDePara(RequestEditDePara request) {
        this(request.email(), request.nomeArquivo(), request.cnpj(), request.metadados());
    }
}
