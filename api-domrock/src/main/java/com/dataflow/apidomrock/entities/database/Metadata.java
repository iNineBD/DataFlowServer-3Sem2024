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

    @ManyToOne
    @JoinColumn(name = "arquivo_id")
    private Arquivo arquivo;


    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_metadado")
    private List<Restricao> restricoes;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "nome_tipo")
    private Tipo nomeTipo;
}
