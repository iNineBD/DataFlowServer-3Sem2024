package com.dataflow.apidomrock.dto.registerdto;

import lombok.*;

import java.util.List;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UsuarioDTO {
    private String emailUsuario;
    private List<String> nivelAcesso;
    private String cnpj;
    private String organizacao;
}
