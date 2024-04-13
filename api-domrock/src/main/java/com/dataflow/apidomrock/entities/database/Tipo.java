package com.dataflow.apidomrock.entities.database;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Entity
@Table(name = "tipo")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Tipo implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "nome_tipo")
    private String nomeTipo;
}
