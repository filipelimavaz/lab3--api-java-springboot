package br.com.ufpb.meajude;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan("br.com.ufpb.meajude.entities")
public class MeAjudeApplication {

	public static void main(String[] args) {
		SpringApplication.run(MeAjudeApplication.class, args);
	}

}
