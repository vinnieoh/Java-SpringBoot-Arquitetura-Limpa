package com.example.api.infrastructure.env;

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
        System.setProperty("DB_DRIVER", dotenv.get("DB_DRIVER"));
        System.setProperty("DB_DIALECT", dotenv.get("DB_DIALECT"));
        System.setProperty("SPRING_JPA_SHOW_SQL", dotenv.get("SPRING_JPA_SHOW_SQL", "false"));
        System.setProperty("SERVER_PORT", dotenv.get("SERVER_PORT", "8080"));

    }

}
