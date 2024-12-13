package com.corcino.medical;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class MedicalApplication {

	public static void main(String[] args) {
		SpringApplication.run(MedicalApplication.class, args);

		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

		// A senha que você quer encriptar
		String rawPassword = "12345";

		// Gerar o hash da senha
		String encodedPassword = passwordEncoder.encode(rawPassword);

		// Exibir o hash gerado
		System.out.println("Hash da senha: " + encodedPassword);
	}

}
