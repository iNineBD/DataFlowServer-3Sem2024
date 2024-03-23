package com.dataflow.apidomrock.entities.database;

import com.dataflow.apidomrock.entities.enums.Type;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Metadata {
    private String fieldName;
    private Type fieldType;
    private Integer fieldMaxLength;
    private Object fieldDefaultValue;
    private String fieldDescription;
    private Boolean fieldIsRequired;
}
