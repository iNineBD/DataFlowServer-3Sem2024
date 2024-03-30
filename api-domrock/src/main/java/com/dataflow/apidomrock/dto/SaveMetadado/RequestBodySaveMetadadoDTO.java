package com.dataflow.apidomrock.dto.SaveMetadado;

import com.dataflow.apidomrock.entities.database.Restricao;
import com.dataflow.apidomrock.entities.database.Tipo;
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
    private Integer ativo;
    private String nome;
    private String valorPadrao;
    private String descricao;
    private List<Restricao> restricoes;
    private Tipo tipo;




}
