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
    private String email;
    private String senha;

    @ManyToOne
    @JoinColumn (name="organizacao_nome", nullable=false)
    private Organizacao organizacao;

    //TODO: revisar esse mapeamento
    @ManyToMany(targetEntity= NivelAcesso.class, fetch = FetchType.LAZY)
    @JoinTable(name = "nivel_acesso_usuario", // nome da tabela no sql
            joinColumns = @JoinColumn(name = "email"), // fk do usuario na nivel_acesso_usuario
            inverseJoinColumns = @JoinColumn(name = "nivel") // fk do nivel na nivel_acesso_usuario
    )
    private List<NivelAcesso> niveisAcesso;
}
