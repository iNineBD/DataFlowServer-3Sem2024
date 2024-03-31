package com.dataflow.apidomrock.dto.SaveMetadado;

import com.dataflow.apidomrock.entities.database.Arquivo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RequestBodySaveDTO {
    private String nomeArquivo;
    private String usuario;
    private List<RequestBodySaveMetadadoDTO> metadados;

}
