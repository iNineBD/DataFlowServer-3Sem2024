package com.dataflow.apidomrock.entities.database;

import com.dataflow.apidomrock.entities.enums.TypeEnum;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "metadado")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Metadata {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer ID;
    private String fieldName;
    private TypeEnum fieldTypeEnum;
    private Integer fieldMaxLength;
    private Object fieldDefaultValue;
    private String fieldDescription;
    private Boolean fieldIsRequired;
}
