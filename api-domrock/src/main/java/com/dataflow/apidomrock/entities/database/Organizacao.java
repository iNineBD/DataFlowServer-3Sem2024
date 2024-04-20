package com.dataflow.apidomrock.entities.database;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "organizacao")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Organizacao implements Serializable {
    @Id
    private String cnpj;
    private String nome;
}
