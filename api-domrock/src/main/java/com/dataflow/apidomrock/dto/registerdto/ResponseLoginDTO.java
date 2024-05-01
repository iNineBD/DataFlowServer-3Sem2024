package com.dataflow.apidomrock.dto.registerdto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


public record ResponseLoginDTO(String token, String nomeUsuario, String email, boolean isMaster) {
}
