package com.example.gestion_formation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan(basePackages = "com.example.gestion_formation.entities")
public class GestionFormationApplication {

	public static void main(String[] args) {
		SpringApplication.run(GestionFormationApplication.class, args);
	}

}
