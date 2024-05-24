package com.dataflow.apidomrock.dto.savemetadado;

import java.util.List;

import com.dataflow.apidomrock.entities.database.Restricao;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description="DTO de requisição para salvamento de metadados")
public class RequestBodySaveMetadadoDTO {
    @Schema(description = "Boleano de se o arquivo está ativo", example = "true")
    private Boolean ativo;
    @Schema(description = "Nome do arquivo", example = "Arquivo01")
    private String nome;
    @Schema(description = "Tipo de dado", example = "String")
    private String valorPadrao;
    @Schema(description = "Descrição do arquivo", example = "Arquivo de teste")
    private String descricao;
    @Schema(description = "Lista de restrições", example = "Restrição01, Restrição02") //todo verificar com a ana
    private List<Restricao> restricoes;

}
