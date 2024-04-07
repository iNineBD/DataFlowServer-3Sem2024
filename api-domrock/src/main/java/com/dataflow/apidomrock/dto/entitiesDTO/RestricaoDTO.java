package com.dataflow.apidomrock.dto.entitiesDTO;

import com.dataflow.apidomrock.entities.database.Metadata;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class RestricaoDTO {
    private Integer id;
    private String nome;
    private String valor;
}
