package com.secran.certificados.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.nio.file.Path;
import java.nio.file.Paths;

@Configuration
public class CertificadoConfig {

    @Value("${certificados.pasta.segura}")
    private String pastaSegura;

    @Bean
    public Path pastaSeguraPath() {
        return Paths.get(pastaSegura).toAbsolutePath().normalize();
    }
}