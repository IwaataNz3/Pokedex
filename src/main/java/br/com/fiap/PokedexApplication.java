package br.com.fiap;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// Classe principal: o main sobe o Spring e o Tomcat embutido na porta 8080.
// @SpringBootApplication faz o Spring procurar as classes (Resource, CorsFilter...) dentro de br.com.fiap.
@SpringBootApplication
public class PokedexApplication {

	public static void main(String[] args) {
		SpringApplication.run(PokedexApplication.class, args);
	}

}
