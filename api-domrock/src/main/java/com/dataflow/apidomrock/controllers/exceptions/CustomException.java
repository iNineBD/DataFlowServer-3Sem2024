package com.dataflow.apidomrock.controllers.exceptions;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class CustomException extends Exception {
    private String msg;
    private HttpStatus httpStatus;

    public CustomException(String msg, HttpStatus httpStatus) {
       this.msg = msg;
        this.httpStatus = httpStatus;
    }

}