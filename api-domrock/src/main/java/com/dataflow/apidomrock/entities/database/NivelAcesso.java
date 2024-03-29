package com.dataflow.apidomrock.entities.database;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

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
