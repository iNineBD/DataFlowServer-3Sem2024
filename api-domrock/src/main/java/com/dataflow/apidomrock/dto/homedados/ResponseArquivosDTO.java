package com.dataflow.apidomrock.dto.homedados;

import com.dataflow.apidomrock.entities.database.Arquivo;
import com.fasterxml.jackson.annotation.JsonAlias;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "ResponseArquivosDTO", description = "Response para buscar arquivos")
public record ResponseArquivosDTO(

        @JsonAlias("nomeArquivo") @Schema(description = "Nome do arquivo", example = "Arquivo01") String nome,
        @JsonAlias("organizacao") @Schema(description = "Nome da organização responsável pelo arquivo", example = "Don Rock") String organizacao,
        @JsonAlias("cnpj") @Schema(description = "Cnpj da organização responsável pelo arquivo", example = "99.205.190/0001-40") String cnpj,
        @JsonAlias("status") @Schema(description = "status do arquivo", example = "ReprovadoPelaBronze") String status) {

    public ResponseArquivosDTO(Arquivo arquivo) {
        this(arquivo.getNomeArquivo(), arquivo.getOrganizacao().getNome(), arquivo.getOrganizacao().getCnpj(),
                arquivo.getStatus());
    }
}
