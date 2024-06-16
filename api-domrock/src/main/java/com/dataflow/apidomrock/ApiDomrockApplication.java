package com.dataflow.apidomrock;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

/*
	Olá, caros colegas desenvolvedores!
	Com este comentário, encerramos a sprint-4 e, consequentemente, o API 3° semestre!!
	Obrigado a todos os envolvidos!!!
*/
@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "API DomRock", version = "1.0", description = "API realizada em conjunto com a empresa Dom Rock para gerenciamento de pipeline de dados"))
public class ApiDomrockApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiDomrockApplication.class, args);
	}

}