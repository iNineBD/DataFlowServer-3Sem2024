package com.dataflow.apidomrock.dto.entitiesdto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class RestricaoDTO {
    private Integer id;
    private String nome;
    private String valor;
    private void popularStringsNulas() {
        if (this.nome == null) {
            this.nome = "";
        }
        if (this.valor == null) {
            this.valor = "";
        }
    }
}
