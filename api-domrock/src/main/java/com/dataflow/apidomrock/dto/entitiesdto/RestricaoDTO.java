package com.dataflow.apidomrock.dto.entitiesdto;

import lombok.*;

@Getter
@Setter
@ToString
public class RestricaoDTO {
    private Integer id;
    private String nome;
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
