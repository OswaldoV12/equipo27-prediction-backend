package com.h12_25_l.equipo27.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class PredictionBackendApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(PredictionBackendApiApplication.class, args);
	}

}