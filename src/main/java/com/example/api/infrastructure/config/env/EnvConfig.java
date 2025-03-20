package com.example.api.infrastructure.config.env;

import io.github.cdimascio.dotenv.Dotenv;

public class EnvConfig {

    public static void env_config() {
        Dotenv dotenv = Dotenv.configure()
                .directory("dotenv_files")
                .filename(".env")
                .load();

        // Definir variáveis de ambiente para o Spring Boot
        System.setProperty("DB_HOST", dotenv.get("DB_HOST"));
        System.setProperty("DB_PORT", dotenv.get("DB_PORT"));
        System.setProperty("DB_NAME", dotenv.get("DB_NAME"));
        System.setProperty("DB_USERNAME", dotenv.get("DB_USERNAME"));
        System.setProperty("DB_PASSWORD", dotenv.get("DB_PASSWORD"));
        System.setProperty("SERVER_PORT", dotenv.get("SERVER_PORT", "8080"));

    }

}
