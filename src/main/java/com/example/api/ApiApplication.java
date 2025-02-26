package com.example.api;

import com.example.api.infrastructure.env.EnvConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ApiApplication {

	public static void main(String[] args) {

		EnvConfig.env_config();

		SpringApplication.run(ApiApplication.class, args);
	}

}
