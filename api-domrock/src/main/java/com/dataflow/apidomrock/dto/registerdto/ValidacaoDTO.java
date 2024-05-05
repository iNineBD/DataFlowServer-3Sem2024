package com.dataflow.apidomrock.dto.registerdto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ValidacaoDTO {
    private String emailUsuario;
    private String nome;
    private String token;
    private String senha;

}
