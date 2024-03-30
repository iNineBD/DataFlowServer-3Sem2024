package com.dataflow.apidomrock.entities.database;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.catalina.LifecycleState;

import java.util.List;

@Entity
@Table(name = "tipo")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Tipo {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonProperty("tipoMetadado")
    private String nomeTipo;
}
