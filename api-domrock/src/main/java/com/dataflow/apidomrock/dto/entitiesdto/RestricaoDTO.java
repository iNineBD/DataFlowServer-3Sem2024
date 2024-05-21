package com.dataflow.apidomrock.dto.entitiesdto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Schema(description = "DTO para a entidade de arquivo restrito")
public class RestricaoDTO {
    @Schema(description = "ID do arquivo", example = "123e4567-e89b-12d3-a456-426614174000")
    private Integer id;
    @Schema(description = "Nome do arquivo", example = "Arquivo01")
    private String nome;
    @Schema(description = "Valor do arquivo", example = "Valor01")
    private String valor;

    public RestricaoDTO(Integer id, String nome, String valor) {
        this.id = id;
        this.nome = nome;
        this.valor = valor;
    }

    private void popularStringsNulas() {
        if (this.nome == null) {
            this.nome = "";
        }
        if (this.valor == null) {
            this.valor = "";
        }
    }
}
