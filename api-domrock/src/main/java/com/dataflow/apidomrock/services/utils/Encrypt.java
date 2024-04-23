package com.dataflow.apidomrock.services.utils;

import com.dataflow.apidomrock.controllers.exceptions.CustomException;
import org.springframework.http.HttpStatus;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.swing.undo.CannotUndoException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class Encrypt {
    public static String encrypt(String strToEncrypt) throws NoSuchAlgorithmException, CustomException {
        KeyGenerator keyGen = KeyGenerator.getInstance("AES");
        keyGen.init(128); // Tamanho da chave, 128 bits para AES
        SecretKey secretKey = keyGen.generateKey();
        try {
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            byte[] encryptedBytes = cipher.doFinal(strToEncrypt.getBytes());
            return Base64.getEncoder().encodeToString(encryptedBytes);
        } catch (Exception e) {
            throw new CustomException("Erro ao criptografar a senha", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
