package com.dataflow.apidomrock.dto.savehash;

import java.util.List;

import com.dataflow.apidomrock.dto.editarhash.RequestEditHashDTO;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.swagger.v3.oas.annotations.media.Schema;

@JsonIgnoreProperties(ignoreUnknown = true)
@Schema(description = "DTO para requisição de dados para criação de hash")
public record RequestHashDTO(
        @JsonAlias("nomeArquivo") @Schema(description = "Nome do arquivo", example = "Arquivo01") String nomeArquivo,
        @JsonAlias("usuario") @Schema(description = "Nome do usuário", example = "Usu ario") String usuario,
        @JsonAlias("metadados") @Schema(description = "Lista de metadados do arquivo", example = "coluna01, coluna02, ...") List<RequestMetadadoDTO> metadados,
        @JsonAlias("cnpj") @Schema(description = "Cnpj da organização responsável pelo arquivo", example = "99.205.190/0001-40") String cnpj) {

    public RequestHashDTO(RequestEditHashDTO request) {
        this(request.nomeArquivo(), request.usuario(), request.metadados(), request.cnpj());
    }
}
