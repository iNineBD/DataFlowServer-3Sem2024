package com.dataflow.apidomrock.controllers;

import com.dataflow.apidomrock.dto.registerdto.ValidacaoDTO;
import com.dataflow.apidomrock.dto.customresponse.ResponseCustomDTO;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import java.security.NoSuchAlgorithmException;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class RegisterControllerValidacaoTest extends com.dataflow.apidomrock.controllers.RegisterControllerTestBase {

    @Test
    void testFinishingRegisterUserInDataBase_Success() throws Exception {
        // Arrange
        ValidacaoDTO validacaoDTO = new ValidacaoDTO();
        validacaoDTO.setEmailUsuario("test@example.com");
        validacaoDTO.setSenha("123456");

        doNothing().when(registerServices).FirstLogin(any(ValidacaoDTO.class));

        // Act & Assert
        mockMvc.perform(post("/register/validacao")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validacaoDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Processamento efetuado com sucesso"))
                .andExpect(jsonPath("$.data").doesNotExist());
    }
}