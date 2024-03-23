package com.dataflow.apidomrock.entities.http.responses;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

/*
* Essa classe contem a estrutura padrão dos nossos responses.
* Se algum desses campos for NULL, ele ignora no JSON do response
* Sempre teremos um ID de execução (vamos rpecisar mais pra frente por conta dos logs), a data da execuçao e uma critica ("sucesso", "erro"...)
* O response é um generico pois a ideia é usar pra todos os responses (response de login, response de put...)
* */
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CustomResponseEntity<T> {
    private UUID transID;
    private String critica;
    private LocalDateTime serverTime;
    private T response;

    public CustomResponseEntity(String feedback, T response) {
        this.critica = feedback;
        this.response = response;
        this.transID = UUID.randomUUID();
        this.serverTime = LocalDateTime.now();
    }
}