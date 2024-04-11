package com.dataflow.apidomrock.services.utils;

import com.dataflow.apidomrock.controllers.exceptions.CustomException;
import org.springframework.http.HttpStatus;
import org.springframework.web.multipart.MultipartFile;

import java.util.Objects;

public class ValidateRequest {
    public static MultipartFile validateprocessUploadCSV(MultipartFile file, String delimiter) throws CustomException {

        if (Objects.equals(file.getOriginalFilename(), "")) {
            throw new CustomException("Nenhum arquivo foi enviado na requisição", HttpStatus.NOT_FOUND);
        }
        if (file.isEmpty()) {
            throw new CustomException("O arquivo " + file.getOriginalFilename() + " enviado está vazio", HttpStatus.NO_CONTENT);
        }
        String fileType = Objects.requireNonNull(file.getOriginalFilename()).substring(file.getOriginalFilename().lastIndexOf('.') + 1);
        if ((!"csv".equalsIgnoreCase(fileType))) {
            throw new CustomException("Apenas arquivos .csv são permitidos", HttpStatus.UNSUPPORTED_MEDIA_TYPE);
        }

        return file;
    }
}
