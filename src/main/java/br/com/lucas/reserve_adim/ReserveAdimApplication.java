package br.com.lucas.reserve_adim;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ReserveAdimApplication {

	public static void main(String[] args) {

		Dotenv dotenv = Dotenv.configure().directory("./").filename("Variables.env").load();

		System.setProperty("API_TOKEN_SECRET", dotenv.get("API_TOKEN_SECRET"));
		System.setProperty("DB_URL", dotenv.get("DB_URL"));
		System.setProperty("DB_USERNAME", dotenv.get("DB_USERNAME"));
		System.setProperty("DB_PASSWORD", dotenv.get("DB_PASSWORD"));

		SpringApplication.run(ReserveAdimApplication.class, args);
	}

}
