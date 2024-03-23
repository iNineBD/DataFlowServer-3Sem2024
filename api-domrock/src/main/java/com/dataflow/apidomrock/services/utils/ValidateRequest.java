package com.dataflow.apidomrock.services.utils;

import org.apache.tika.Tika;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.List;

public class ValidateRequest {
    public static void validateprocessUploadCSV(MultipartFile file, String delimiter){
        if (delimiter == null){
            throw new ResponseStatusException(HttpStatusCode.valueOf(404), "É necessário especificar o delimitador do arquivo");
        }
        if (delimiter.length() > 1){
            throw new IllegalArgumentException("o delimitador deve ser apenas um caracter");
        }
        if(!List.of(',', ';', '|', ':', '\t', ' ').contains((delimiter.charAt(0)))){
            throw new IllegalArgumentException("O delimitador é inválido");
        }
        if (file.getOriginalFilename().equals("")) {
            throw new IllegalArgumentException("Nenhum arquivo foi enviado na requisição");
        }
        if (file.isEmpty()) {
            throw new IllegalArgumentException("O arquivo " + file.getOriginalFilename() + " enviado está vazio");
        }
        Tika tika = new Tika();

        String fileType = null;
        try {
            fileType = tika.detect(file.getBytes());
        } catch (IOException e) {
            throw new RuntimeException("Houve algum erro ao identificar o formato do arquivo");
        }
        if ((!"text/csv".equals(fileType)) && (!"text/plain".equals(fileType)) ) {
            throw new RuntimeException("Conforme definido no escopo do projeto, a aplicação interpreta apenas CSVs");
        }
    }
}
