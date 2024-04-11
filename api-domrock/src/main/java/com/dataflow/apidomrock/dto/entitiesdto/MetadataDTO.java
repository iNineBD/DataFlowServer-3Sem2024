package com.dataflow.apidomrock.dto.entitiesdto;

import com.dataflow.apidomrock.entities.database.Metadata;
import com.dataflow.apidomrock.entities.database.Restricao;
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
        if (entity.getNomeTipo() != null) {
            this.nomeTipo = entity.getNomeTipo().getNomeTipo();
        }

        popularStringsNulas();
    }

    private void popularStringsNulas() {
        if (this.nome == null) {
            this.nome = "";
        }
        if (this.valorPadrao == null) {
            this.valorPadrao = "";
        }
        if (this.descricao == null) {
            this.descricao = "";
        }
        if (this.arquivo == null) {
            this.arquivo = "";
        }
        if (this.nomeTipo == null) {
            this.nomeTipo = "";
        }
    }
}
