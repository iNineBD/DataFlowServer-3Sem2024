package com.dataflow.apidomrock.dto.SaveMetadado;

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

    private List<RequestBodySaveMetadadoDTO> metadados;

}
