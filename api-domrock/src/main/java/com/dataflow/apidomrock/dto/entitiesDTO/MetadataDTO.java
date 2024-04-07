package com.dataflow.apidomrock.dto.entitiesDTO;

import com.dataflow.apidomrock.entities.database.Arquivo;
import com.dataflow.apidomrock.entities.database.Metadata;
import com.dataflow.apidomrock.entities.database.Restricao;
import com.dataflow.apidomrock.entities.database.Tipo;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class MetadataDTO {

    private Integer ID;
    private String nome;
    private String valorPadrao;
    private String descricao;
    private Boolean ativo;

    private String arquivo;

    private List<RestricaoDTO> restricoes;

    private String nomeTipo;

    public MetadataDTO(Metadata entity) {
        this.ID = entity.getID();
        this.ativo = entity.getAtivo();
        this.nome = entity.getNome();
        this.valorPadrao = entity.getValorPadrao();
        this.descricao = entity.getDescricao();
        this.arquivo = entity.getArquivo().getNomeArquivo();
        this.restricoes = new ArrayList<>();
        List<Restricao> list = entity.getRestricoes();
        for (Restricao restricao : list) {
            this.restricoes.add(new RestricaoDTO(restricao.getId(), restricao.getNome(), restricao.getValor()));
        }
        this.nomeTipo = entity.getNomeTipo().getNomeTipo();
    }
}
