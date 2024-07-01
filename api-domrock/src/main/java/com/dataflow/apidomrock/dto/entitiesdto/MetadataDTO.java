package com.dataflow.apidomrock.dto.entitiesdto;

import java.util.ArrayList;
import java.util.List;

import com.dataflow.apidomrock.entities.database.Metadata;
import com.dataflow.apidomrock.entities.database.Restricao;
import com.fasterxml.jackson.annotation.JsonInclude;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "DTO para a entidade metadados")
public class MetadataDTO {

    @Schema(description = "ID do metadado", example = "123e4567-e89b-12d3-a456-426614174000")
    private Integer ID;
    @Schema(description = "Nome do metadado", example = "Nome do metadado")
    private String nome;
    @Schema(description = "Valor padrão do metadado", example = "Valor padrão")
    private String valorPadrao;
    @Schema(description = "Descrição do metadado", example = "Descrição do metadado")
    private String descricao;

    public MetadataDTO(Integer ID, String nome, String valorPadrao, String descricao, String sampleValue, Boolean ativo,
            String arquivo, List<RestricaoDTO> restricoes, String nomeTipo) {
        this.ID = ID;
        this.nome = nome;
        this.valorPadrao = valorPadrao;
        this.descricao = descricao;

        if (sampleValue.length() > 50) {
            sampleValue = sampleValue.substring(0, 50);
        }
        this.sampleValue = sampleValue;
        this.ativo = ativo;
        this.arquivo = arquivo;
        this.restricoes = restricoes;
        this.nomeTipo = nomeTipo;
    }

    @Schema(description = "Exemplo do metadado", example = "Exemplo do metadado")
    private String sampleValue;
    @Schema(description = "Metadado ativo", example = "true")
    private Boolean ativo;
    @Schema(description = "Nome do arquivo", example = "Nome do arquivo")
    private String arquivo;
    @Schema(description = "Restrições do metadado", example = "Restrições do metadado")
    private List<RestricaoDTO> restricoes;
    @Schema(description = "Nome do tipo do metadado", example = "Nome do tipo")
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

    @Schema(description = "Construtor para popular metadado com strings nulas")
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
