package com.dataflow.apidomrock.dto.getMetadados;

import com.dataflow.apidomrock.dto.entitiesDTO.RestricaoDTO;
import com.dataflow.apidomrock.entities.database.Metadata;
import com.dataflow.apidomrock.entities.database.Restricao;
import com.dataflow.apidomrock.entities.database.Tipo;
import lombok.*;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ResponseBodyMetadadoDTO {
    private Boolean ativo;
    private String nome;
    private String valorPadrao;
    private String descricao;
    private List<RestricaoDTO> restricoes;
    private Tipo tipo;

    public ResponseBodyMetadadoDTO(Metadata entity) {
        this.ativo = entity.getAtivo();
        this.nome = entity.getNome();
        this.valorPadrao = entity.getValorPadrao();
        this.descricao = entity.getDescricao();
        this.restricoes = new ArrayList<>();
        List<Restricao> list = entity.getRestricoes();
        for (Restricao restricao : list) {
            this.restricoes.add(new RestricaoDTO(restricao.getId(), restricao.getNome(), restricao.getValor()));
        }
        this.tipo = entity.getNomeTipo();
    }
}
