package com.dataflow.apidomrock.entities.database;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Entity
@Table(name = "organizacao")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Organizacao implements Serializable {
    @Id
    private String nome;
}
