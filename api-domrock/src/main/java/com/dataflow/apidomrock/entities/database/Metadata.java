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
    private Boolean isAtivo;
    private String tipo;
    private String exemplo;

    @ManyToOne
    @JoinColumn(name = "arquivo_id")
    private Arquivo arquivo;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_metadado")
    private List<Restricao> restricoes;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_metadado_dePara")
    private List<DePara> deParas;
}
