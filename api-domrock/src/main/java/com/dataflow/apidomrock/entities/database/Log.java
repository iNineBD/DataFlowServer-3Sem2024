package com.dataflow.apidomrock.entities.database;

import com.dataflow.apidomrock.entities.enums.Acao;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "log")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Log {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private LocalDateTime dataHora;
    private String observacao;
    private String estagio; // LZ, BZ or SZ
    private String acao; //'INSERIR', 'ALTERAR', 'EXCLUIR', 'APROVAR', 'REPROVAR'

    @ManyToOne
    @JoinColumn(name = "id_arquivo")
    private Arquivo arquivo;

    @ManyToOne
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;


}
