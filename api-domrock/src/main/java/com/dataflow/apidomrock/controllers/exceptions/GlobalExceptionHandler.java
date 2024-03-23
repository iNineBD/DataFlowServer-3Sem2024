package com.dataflow.apidomrock.controllers.exceptions;

import com.dataflow.apidomrock.entities.http.responses.CustomResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;

import java.nio.file.AccessDeniedException;

@ControllerAdvice
public class GlobalExceptionHandler {
    //Exceção para recurso não encontrado
    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<CustomResponseEntity<Object>> handleResourceNotFoundException(ResponseStatusException ex) {
        CustomResponseEntity<Object> customResponse = new CustomResponseEntity<>(ex.getMessage(), null);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(customResponse);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    //Exceção para entrada inválida
    public ResponseEntity<CustomResponseEntity<Object>> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        CustomResponseEntity<Object> customResponse = new CustomResponseEntity<>(ex.getMessage(), null);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(customResponse);
    }

    @ExceptionHandler(AccessDeniedException.class)
    //Exceção para acesso negado
    public ResponseEntity<CustomResponseEntity<Object>> handleAccessDeniedException(AccessDeniedException ex) {
        CustomResponseEntity<Object> customResponse = new CustomResponseEntity<>(ex.getMessage(), null);
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(customResponse);
    }

    @ExceptionHandler(RuntimeException.class)
    //Exceção para erro interno generico
    public ResponseEntity<CustomResponseEntity<Object>> handleException(RuntimeException ex) {
        CustomResponseEntity<Object> customResponse = new CustomResponseEntity<>(ex.getMessage(), null);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(customResponse);
    }
}
