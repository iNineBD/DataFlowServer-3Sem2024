package com.dataflow.apidomrock.dto.registerdto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.List;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UsuarioDTO {
    private String emailUsuario;
    private String nivelAcesso;
    private String cnpj;
    private String organização;
}
