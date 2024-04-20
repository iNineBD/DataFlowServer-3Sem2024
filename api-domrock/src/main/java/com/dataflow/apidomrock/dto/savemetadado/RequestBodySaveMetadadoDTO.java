package com.dataflow.apidomrock.dto.savemetadado;

import com.dataflow.apidomrock.entities.database.Restricao;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RequestBodySaveMetadadoDTO {
    private Boolean ativo;
    private String nome;
    private String valorPadrao;
    private String descricao;
    private List<Restricao> restricoes;

}
