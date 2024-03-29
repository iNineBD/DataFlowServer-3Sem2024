package com.dataflow.apidomrock.entities.database;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "arquivo")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Arquivo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private  String nomeArquivo;

    @OneToOne
    private Usuario usuario;

    @ManyToOne
    private Organizacao organizacao;

    @ManyToOne
    @JoinColumn(name = "status")
    private Status status;
}
