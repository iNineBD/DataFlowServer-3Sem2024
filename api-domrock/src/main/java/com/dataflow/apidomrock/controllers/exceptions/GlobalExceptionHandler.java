package com.dataflow.apidomrock.controllers.exceptions;

import com.dataflow.apidomrock.entities.http.responses.CustomResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;

import java.nio.file.AccessDeniedException;

/*
* Essa classe está mapeada pelo ControllerAdvice - isso siginifica que o spring vai buscar uma trataiva para qualquer exceção lançada nos controllers
* Como isso funciona?
* Quando estouramos uma exceção, ela tem um tipo (RuntimeException... etc)
*
* O Adivice vem nessa classe GlobalExceptionHandler e busca o metodo que trata exceções do tipo em questão.
*
* Se fosse uma exeção do RuntimeException, por exemplo, a função invocada seria a handleException
* */
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
