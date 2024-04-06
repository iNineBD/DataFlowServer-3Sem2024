package com.dataflow.apidomrock.entities.database;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "restricao")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Restricao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String nome;
    private String valor;

    @ManyToOne
    @JoinColumn(name = "id_metadado")
    private Metadata metadata;
}
