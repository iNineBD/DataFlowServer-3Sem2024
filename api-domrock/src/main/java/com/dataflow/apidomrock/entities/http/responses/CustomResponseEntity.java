package com.dataflow.apidomrock.entities.http.responses;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

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