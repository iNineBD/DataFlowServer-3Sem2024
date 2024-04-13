package com.dataflow.apidomrock.services.utils;

import com.dataflow.apidomrock.controllers.exceptions.CustomException;
import org.springframework.http.HttpStatus;
import org.springframework.web.multipart.MultipartFile;

import java.util.Objects;

public class Validate {
    public static Boolean validateprocessUploadCSV(MultipartFile file, String delimiter) throws CustomException {

        if (Objects.equals(file.getOriginalFilename(), "")) {
            throw new CustomException("Nenhum arquivo foi enviado na requisição", HttpStatus.NOT_FOUND);
        }
        if (file.isEmpty()) {
            throw new CustomException("O arquivo " + file.getOriginalFilename() + " enviado está vazio", HttpStatus.NO_CONTENT);
        }
        String fileType = Objects.requireNonNull(file.getOriginalFilename()).substring(file.getOriginalFilename().lastIndexOf('.') + 1);
        if (fileType.contains("csv")){
            return true;
        } else if (fileType.contains("xls")) {
            return false;
        } else {
            throw new CustomException("Apenas arquivos .XLS e .CSV são permitidos", HttpStatus.UNSUPPORTED_MEDIA_TYPE);
        }
    }


    public static Boolean isInteger(String str) {
        int length = str.length();
        for (int i = 0; i < length; i++) {
            if (!Character.isDigit(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }
}
