package com.dataflow.apidomrock.dto.customresponse;

import java.time.LocalDateTime;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonInclude;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

/*
* Essa classe contem a estrutura padrão dos nossos responses.
* Se algum desses campos for NULL, ele ignora no JSON do response
* Sempre teremos um ID de execução (vamos rpecisar mais pra frente por conta dos logs), a data da execuçao e uma critica ("sucesso", "erro"...)
* O response é um generico pois a ideia é usar pra todos os responses (response de login, response de put...)
* */
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "DTO para criação de header de resposta customizada")
public class ResponseCustomDTO<T> {
    @Schema(description = "ID do arquivo", example = "123e4567-e89b-12d3-a456-426614174000")
    private UUID transID;
    @Schema(description = "Observação referente ao arquivo", example = "Processamento efetuado com sucesso")
    private String critica;
    @Schema(description = "Data e hora do processo", example = "2021-08-01T12:00:00")
    private LocalDateTime serverTime;
    @Schema(description = "Resposta da execução")
    private T response;

    public ResponseCustomDTO(String feedback, T response) {
        this.critica = feedback;
        this.response = response;
        this.transID = UUID.randomUUID();
        this.serverTime = LocalDateTime.now();
    }
}