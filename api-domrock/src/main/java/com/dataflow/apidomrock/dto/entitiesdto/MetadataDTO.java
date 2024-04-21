package com.dataflow.apidomrock.dto.entitiesdto;

import com.dataflow.apidomrock.entities.database.Metadata;
import com.dataflow.apidomrock.entities.database.Restricao;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MetadataDTO {

    private Integer ID;
    private String nome;
    private String valorPadrao;
    private String descricao;

    public MetadataDTO(Integer ID, String nome, String valorPadrao, String descricao, String sampleValue, Boolean ativo, String arquivo, List<RestricaoDTO> restricoes, String nomeTipo) {
        this.ID = ID;
        this.nome = nome;
        this.valorPadrao = valorPadrao;
        this.descricao = descricao;

        if (sampleValue.length() > 40) {
            sampleValue = sampleValue.substring(0, 40);
        }
        this.sampleValue = sampleValue;
        this.ativo = ativo;
        this.arquivo = arquivo;
        this.restricoes = restricoes;
        this.nomeTipo = nomeTipo;
    }

    private String sampleValue;
    private Boolean ativo;
    private String arquivo;
    private List<RestricaoDTO> restricoes;
    private String nomeTipo;


    public MetadataDTO(Metadata entity) {
        this.ID = entity.getID();
        this.ativo = entity.getIsAtivo();
        this.nome = entity.getNome();
        this.sampleValue = entity.getExemplo();
        this.valorPadrao = entity.getValorPadrao();
        this.descricao = entity.getDescricao();
        this.arquivo = entity.getArquivo().getNomeArquivo();
        this.restricoes = new ArrayList<>();
        List<Restricao> list = entity.getRestricoes();
        for (Restricao restricao : list) {
            this.restricoes.add(new RestricaoDTO(restricao.getId(), restricao.getNome(), restricao.getValor()));
        }
        this.nomeTipo = entity.getTipo();

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
        if (this.sampleValue == null) {
            this.sampleValue = "";
        }
    }
}
