package com.dataflow.apidomrock.entities.database;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "metadado")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Metadata {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer ID;
    private String nome;
    private String valorPadrao;
    private String descricao;
    private Integer ativo;


    @ManyToMany(targetEntity= Restricao.class, fetch = FetchType.LAZY)
    @JoinTable(name = "detem", // nome da tabela no sql
            joinColumns = @JoinColumn(name = "metadado_id"), // fk do metadado na detem
            inverseJoinColumns = @JoinColumn(name = "restricao_nome") // fk do restricao na detem
    )

    private List<Restricao> restricoes;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "nome_tipo")
    private Tipo nomeTipo;
}
