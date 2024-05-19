package com.dataflow.apidomrock.dto.savedepara;

import com.dataflow.apidomrock.dto.editdepara.RequestEditDePara;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record RequestSaveDePara(@JsonAlias("email") String email,
                                @JsonAlias("arquivo") String nomeArquivo,
                                @JsonAlias("cnpj") String cnpj,
                                @JsonAlias("metadados") List<MetadadosToDePara> metadados) {

    public RequestSaveDePara(RequestEditDePara request){
        this(request.email(),request.nomeArquivo(), request.cnpj(), request.metadados());
    }
}
