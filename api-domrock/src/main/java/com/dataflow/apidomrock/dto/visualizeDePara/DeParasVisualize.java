package com.dataflow.apidomrock.dto.visualizeDePara;

import com.dataflow.apidomrock.entities.database.DePara;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DeParasVisualize(@JsonAlias("de") String de,
                               @JsonAlias("para") String para) {

    public DeParasVisualize(DePara dePara){
        this(dePara.getDe(), dePara.getPara());
    }
}
