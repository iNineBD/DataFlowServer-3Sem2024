package com.dataflow.apidomrock.entities.database;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "depara")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DePara {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String de;
    private String para;

    @ManyToOne
    @JoinColumn(name = "id_metadado_dePara")
    private Metadata metadado;


}
