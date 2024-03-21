package com.dataflow.apidomrock.entities.database;

import com.dataflow.apidomrock.entities.enums.Type;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Metadado {
    private String fieldName;
    private Type fieldType;
    private int fieldMaxLength;
    private Object fieldDefaultValue;
    private String fieldDescription;
    private boolean fieldIsRequired;
}
