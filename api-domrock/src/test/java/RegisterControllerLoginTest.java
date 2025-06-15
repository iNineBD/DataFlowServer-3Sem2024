package com.dataflow.apidomrock.controllers;

import com.dataflow.apidomrock.dto.registerdto.AutenticacaoDTO;
import com.dataflow.apidomrock.dto.registerdto.ResponseLoginDTO;
import com.dataflow.apidomrock.entities.database.Usuario;
import com.dataflow.apidomrock.dto.customresponse.ResponseCustomDTO;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import java.util.Collections;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class RegisterControllerLoginTest extends com.dataflow.apidomrock.controllers.RegisterControllerTestBase {

    @Test
    void testUserLogin_Success() throws Exception {
        // Arrange
        AutenticacaoDTO autenticacaoDTO = new AutenticacaoDTO();
        autenticacaoDTO.setLogin("test@example.com");
        autenticacaoDTO.setSenha("password123");

        Usuario usuario = new Usuario();
        usuario.setNome("Test User");
        usuario.setEmail("test@example.com");

        when(registerServices.login(any(AutenticacaoDTO.class))).thenReturn("mockToken");
        when(registerServices.getUsuario(any(AutenticacaoDTO.class))).thenReturn(usuario);

        // Act & Assert
        mockMvc.perform(post("/register/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(autenticacaoDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Processamento efetuado com sucesso"))
                .andExpect(jsonPath("$.data.token").value("mockToken"))
                .andExpect(jsonPath("$.data.nome").value("Test User"))
                .andExpect(jsonPath("$.data.email").value("test@example.com"));
    }
}