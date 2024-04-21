package com.dataflow.apidomrock.entities.database;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "arquivo")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Arquivo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private  String nomeArquivo;

    @ManyToOne
    @JoinColumn(name = "cnpj_organizacao")
    private Organizacao organizacao;


    private boolean isAtivo;
    private String status;

    @OneToMany(mappedBy = "arquivo")
    private List<Metadata> metadados;

    @ManyToMany(targetEntity= Metadata.class, fetch = FetchType.LAZY)
    @JoinTable(name = "hash", // nome da tabela no sql
            joinColumns = @JoinColumn(name = "id_arquivo"), // fk do arquivo no hash
            inverseJoinColumns = @JoinColumn(name = "id_metadata") // fk do metadado no hash
    )
    private List<Metadata> hash;
}
