package com.dataflow.apidomrock.dto.entitiesDTO;

import com.dataflow.apidomrock.entities.database.Arquivo;
import com.dataflow.apidomrock.entities.database.Restricao;
import com.dataflow.apidomrock.entities.database.Tipo;
import jakarta.persistence.*;

import java.util.List;

public class MetadataDTO {

    private Integer ID;
    private String nome;
    private String valorPadrao;
    private String descricao;
    private Boolean ativo;

    private String arquivo;

    private List<RestricaoDTO> restricoes;

    private String nomeTipo;
}
