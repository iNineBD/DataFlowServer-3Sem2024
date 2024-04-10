package com.dataflow.apidomrock.controllers.exceptions;

import com.dataflow.apidomrock.dto.customresponse.ResponseCustomDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.sql.SQLException;

/*
* Essa classe está mapeada pelo ControllerAdvice - isso siginifica que o spring vai buscar uma trataiva para qualquer exceção lançada nos controllers
* Como isso funciona?
* Quando estouramos uma exceção, ela tem um tipo (RuntimeException... etc)
*
* O Advice vem nessa classe GlobalExceptionHandler e busca o metodo que trata exceções do tipo em questão.
*
* Se fosse uma exeção do RuntimeException, por exemplo, a função invocada seria a handleException
* */
@ControllerAdvice
public class GlobalExceptionHandler {
    //Exceção para recurso não encontrado
    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<ResponseCustomDTO<Object>> handleResourceNotFoundException(ResponseStatusException ex) {
        ResponseCustomDTO<Object> customResponse = new ResponseCustomDTO<>(ex.getMessage(), null);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(customResponse);
    }
    @ExceptionHandler(IOException.class)
    public ResponseEntity<ResponseCustomDTO<Object>> handleIOReaderFileException(ResponseStatusException ex) {
        ResponseCustomDTO<Object> customResponse = new ResponseCustomDTO<>("Erro ao serializar arquivo inserido", null);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(customResponse);
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    //Exceção para entrada inválida
    public ResponseEntity<ResponseCustomDTO<Object>> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        ResponseCustomDTO<Object> customResponse = new ResponseCustomDTO<>(ex.getMessage(), null);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(customResponse);
    }

    @ExceptionHandler(AccessDeniedException.class)
    //Exceção para acesso negado
    public ResponseEntity<ResponseCustomDTO<Object>> handleAccessDeniedException(AccessDeniedException ex) {
        ResponseCustomDTO<Object> customResponse = new ResponseCustomDTO<>(ex.getMessage(), null);
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(customResponse);
    }

    @ExceptionHandler(RuntimeException.class)
    //Exceção para erro interno generico
    public ResponseEntity<ResponseCustomDTO<Object>> handleException(RuntimeException ex) {
        ResponseCustomDTO<Object> customResponse = new ResponseCustomDTO<>("Ocorreu um erro inesperado: "+ex.getMessage(), null);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(customResponse);
    }

    @ExceptionHandler(SQLException.class)
    //Exceção para erro interno generico
    public ResponseEntity<ResponseCustomDTO<Object>> handleSQLException(SQLException ex) {
        ResponseCustomDTO<Object> customResponse = new ResponseCustomDTO<>("Ocorreu algum erro interno na base: " +ex.getMessage(), null);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(customResponse);
    }
}
