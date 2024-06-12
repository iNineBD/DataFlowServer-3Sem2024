package com.dataflow.apidomrock.dto.log;

import com.fasterxml.jackson.annotation.JsonAlias;

public record UserDTO(@JsonAlias("organizacao") String organizacao,
                      @JsonAlias ("nome") String nome,
                      @JsonAlias("nivel")String nivel,
                      @JsonAlias("email") String email) {
}
