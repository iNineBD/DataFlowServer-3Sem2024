package com.dataflow.apidomrock.entities.database;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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
    private String nome;

    private Integer valor;
}
