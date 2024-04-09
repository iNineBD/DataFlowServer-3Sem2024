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
@Table(name = "nivel_acesso")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class NivelAcesso implements Serializable {
    @Id
    private String nivel;
}
