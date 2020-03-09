package br.com.luisjustin.api.mobilidade;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class MobilidadeApplication {

	public static void main(String[] args) {
		SpringApplication.run(MobilidadeApplication.class, args);
	}

}
