package com.dataflow.apidomrock.services.utils;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.util.List;
import java.util.Objects;

public class ValidateRequest {
    public static MultipartFile validateprocessUploadCSV(MultipartFile file, String delimiter){


        if (file.isEmpty()) {
            throw new IllegalArgumentException("Nenhum arquivo enviado. Por favor, envie um arquivo CSV.");
        }

        if (delimiter == null || delimiter.isEmpty()){
            throw new IllegalArgumentException("É necessário especificar o delimitador do arquivo");
        }
        if (delimiter.length() > 1){
            throw new IllegalArgumentException("o delimitador deve ser apenas um caracter");
        }
        if(!List.of(',', ';', '|', ':', '\t', ' ').contains((delimiter.charAt(0)))){
            throw new IllegalArgumentException("O delimitador é inválido");
        }
        if (Objects.equals(file.getOriginalFilename(), "")) {
            throw new IllegalArgumentException("Nenhum arquivo foi enviado na requisição");
        }
        if (file.isEmpty()) {
            throw new IllegalArgumentException("O arquivo " + file.getOriginalFilename() + " enviado está vazio");
        }

        String fileType = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf('.')+1);
        if ((!"csv".equalsIgnoreCase(fileType))) {
            throw new RuntimeException("Conforme definido no escopo do projeto, a aplicação interpreta apenas CSVs");
        }
        return file;
    }
}
