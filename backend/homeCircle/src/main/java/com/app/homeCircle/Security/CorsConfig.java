package com.app.homeCircle.Security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig {

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("http://localhost:4200") // URL de tu app angular
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // Metodos HTTP permitidos
                        .allowedHeaders("*") // Permitir todos los comentarios
                        .allowCredentials(true); // Permitir credenciales como cookies
            }
        };
    }

}
