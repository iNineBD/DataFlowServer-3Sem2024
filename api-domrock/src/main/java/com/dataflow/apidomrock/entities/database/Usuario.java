package com.dataflow.apidomrock.entities.database;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "usuario")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Usuario implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String email;
    private String senha;

    @ManyToOne
    @JoinColumn (name="cnpj_organizacao", nullable=false)
    private Organizacao organizacao;

    @ManyToMany(targetEntity= NivelAcesso.class, fetch = FetchType.LAZY)
    @JoinTable(name = "nivel_acesso_usuario", // nome da tabela no sql
            joinColumns = @JoinColumn(name = "id_usuario"), // fk do usuario na nivel_acesso_usuario
            inverseJoinColumns = @JoinColumn(name = "id_nivel") // fk do nivel na nivel_acesso_usuario
    )
    private List<NivelAcesso> niveisAcesso;
}
