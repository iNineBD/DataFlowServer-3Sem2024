package com.dataflow.apidomrock.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.List;

@Configuration
public class WebConfig {

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();

        // Permitir solicitações de qualquer origem
        config.setAllowCredentials(true);

        // Substituir "*" por uma lista de origens específicas
        config.setAllowedOrigins(List.of("http://127.0.0.1:5500", "http://localhost:8080"));

        // Permitir solicitações de qualquer método (GET, POST, etc.)
        config.addAllowedMethod("*");

        // Permitir cabeçalhos específicos
        config.addAllowedHeader("*");

        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }
}