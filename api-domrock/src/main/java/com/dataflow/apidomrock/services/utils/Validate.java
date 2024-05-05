package com.dataflow.apidomrock.services.utils;

import com.dataflow.apidomrock.controllers.exceptions.CustomException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.Objects;

@Component
public class Validate {
    public static Boolean validateprocessUploadCSV(MultipartFile file, String delimiter) throws CustomException {

        if (Objects.equals(file.getOriginalFilename(), "")) {
            throw new CustomException("Nenhum arquivo foi enviado na requisição", HttpStatus.NOT_FOUND);
        }
        if (file.isEmpty()) {
            throw new CustomException("O arquivo enviado está vazio", HttpStatus.BAD_REQUEST);
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

        if (str == null || str.isEmpty()) {
            return true;
        }
        int length = str.length();
        for (int i = 0; i < length; i++) {
            if (!Character.isDigit(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public static Boolean isDouble(String str) {
        if (str == null || str.isEmpty()) {
            return false;
        }
        boolean decimalPointEncountered = false;
        int length = str.length();
        for (int i = 0; i < length; i++) {
            char c = str.charAt(i);
            if (Character.isDigit(c)) {
                continue;
            }
            if (c == '.' && !decimalPointEncountered) {
                decimalPointEncountered = true;
                continue;
            }
            if (c == '-' && i == 0) {
                continue;
            }
            return false;
        }
        return true;
    }



    public static boolean validarCNPJ(String cnpj) {

        cnpj = cnpj.replaceAll("[^0-9]", "");

        if (cnpj.length() != 14) {
            return false;
        }

        int soma = 0;
        int peso = 2;
        for (int i = 11; i >= 0; i--) {
            int digito = Character.getNumericValue(cnpj.charAt(i));
            soma += digito * peso;
            peso++;
            if (peso == 10) {
                peso = 2;
            }
        }
        int resto = soma % 11;
        int digitoVerificador1 = (resto < 2) ? 0 : (11 - resto);

        if (Character.getNumericValue(cnpj.charAt(12)) != digitoVerificador1) {
            return false;
        }

        soma = 0;
        peso = 2;
        for (int i = 12; i >= 0; i--) {
            int digito = Character.getNumericValue(cnpj.charAt(i));
            soma += digito * peso;
            peso++;
            if (peso == 10) {
                peso = 2;
            }
        }
        resto = soma % 11;
        int digitoVerificador2 = (resto < 2) ? 0 : (11 - resto);

        return Character.getNumericValue(cnpj.charAt(13)) == digitoVerificador2;
    }
}
