import com.dataflow.apidomrock.dto.registerdto.UsuarioDTO;
import com.dataflow.apidomrock.dto.customresponse.ResponseCustomDTO;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class RegisterControllerCadastroTest extends com.dataflow.apidomrock.controllers.RegisterControllerTestBase {

    @Test
    void testRegisterUserInDataBase_Success() throws Exception {
        // Arrange
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setEmailUsuario("test@example.com");
        usuarioDTO.setOrganizacao("Test User");
        usuarioDTO.setNivelAcesso(Arrays.asList("LZ"));
        usuarioDTO.setCnpj("12345678901234");

        doNothing().when(registerServices).registerInDatabase(any(UsuarioDTO.class));

        // Act & Assert
        mockMvc.perform(post("/register/cadastro")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(usuarioDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Processamento efetuado com sucesso"))
                .andExpect(jsonPath("$.data").doesNotExist());
    }
}