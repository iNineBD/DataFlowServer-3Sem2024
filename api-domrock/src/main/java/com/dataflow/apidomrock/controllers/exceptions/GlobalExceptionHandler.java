package com.dataflow.apidomrock.controllers.exceptions;

import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.sql.SQLException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;

import com.dataflow.apidomrock.dto.customresponse.ResponseCustomDTO;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

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
@Tag(name = "GlobalExceptionHandler", description = "Classe para tratar exceções globais")
public class GlobalExceptionHandler {
    // Exceção para recurso não encontrado
    @Operation(summary = "Trata exceções de recurso não encontrado", method = "GET")
    @ApiResponse(responseCode = "404", description = "Recurso não encontrado")
    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<ResponseCustomDTO<Object>> handleResourceNotFoundException(ResponseStatusException ex) {
        ResponseCustomDTO<Object> customResponse = new ResponseCustomDTO<>(ex.getMessage(), null);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(customResponse);
    }

    @ExceptionHandler(IOException.class)
    @Operation(summary = "Trata exceções de leitura de arquivo", method = "GET")
    @ApiResponse(responseCode = "400", description = "Erro ao serializar arquivo inserido")
    public ResponseEntity<ResponseCustomDTO<Object>> handleIOReaderFileException(ResponseStatusException ex) {
        ResponseCustomDTO<Object> customResponse = new ResponseCustomDTO<>("Erro ao serializar arquivo inserido", null);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(customResponse);
    }

    // Exceção para entrada inválida
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @Operation(summary = "Trata exceções de entrada inválida", method = "GET")
    @ApiResponse(responseCode = "400", description = "Entrada inválida")
    public ResponseEntity<ResponseCustomDTO<Object>> handleMethodArgumentNotValidException(
            MethodArgumentNotValidException ex) {
        ResponseCustomDTO<Object> customResponse = new ResponseCustomDTO<>(ex.getMessage(), null);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(customResponse);
    }

    // Exceção para entrada inválida
    @ExceptionHandler(CustomException.class)
    @Operation(summary = "Trata exceções customizadas para entrada inválida", method = "GET")
    @ApiResponse(responseCode = "500", description = "Erro interno")
    public ResponseEntity<ResponseCustomDTO<Object>> handleCustomException(CustomException ex) {
        ResponseCustomDTO<Object> customResponse = new ResponseCustomDTO<>(ex.getMsg(), null);
        return ResponseEntity.status(ex.getHttpStatus()).body(customResponse);
    }

    // Exceção para acesso negado
    @ExceptionHandler(AccessDeniedException.class)
    @ApiResponse(responseCode = "403", description = "Acesso negado")
    @Operation(summary = "Trata exceções de acesso negado", method = "GET")
    public ResponseEntity<ResponseCustomDTO<Object>> handleAccessDeniedException(AccessDeniedException ex) {
        ResponseCustomDTO<Object> customResponse = new ResponseCustomDTO<>(ex.getMessage(), null);
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(customResponse);
    }

    // Exceção para erro interno generico
    @ExceptionHandler(RuntimeException.class)
    @ApiResponse(responseCode = "500", description = "Erro interno")
    @Operation(summary = "Trata exceções de erro interno", method = "GET")
    public ResponseEntity<ResponseCustomDTO<Object>> handleException(RuntimeException ex) {
        if (ex.getMessage().contains("Usuário inexistente ou senha inválida")) {
            ResponseCustomDTO<Object> customResponse = new ResponseCustomDTO<>(ex.getMessage(), null);
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(customResponse);
        }
        ResponseCustomDTO<Object> customResponse = new ResponseCustomDTO<>(
                "Ocorreu um erro inesperado: " + ex.getMessage(), null);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(customResponse);
    }

    // Exceção para erro interno generico
    @ExceptionHandler(SQLException.class)
    @ApiResponse(responseCode = "500", description = "Erro interno")
    @Operation(summary = "Trata exceções de erro interno na base", method = "GET")
    public ResponseEntity<ResponseCustomDTO<Object>> handleSQLException(SQLException ex) {
        ResponseCustomDTO<Object> customResponse = new ResponseCustomDTO<>(
                "Ocorreu algum erro interno na base: " + ex.getMessage(), null);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(customResponse);
    }

    // Exceção para erro interno generico
    @ExceptionHandler(UsernameNotFoundException.class)
    @ApiResponse(responseCode = "500", description = "Erro interno")
    @Operation(summary = "Trata exceções de erro interno na base", method = "GET")
    public ResponseEntity<ResponseCustomDTO<Object>> handleAuthException(UsernameNotFoundException ex) {
        ResponseCustomDTO<Object> customResponse = new ResponseCustomDTO<>(
                "Usuário ou senha inválidos: " + ex.getMessage(), null);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(customResponse);
    }
}
