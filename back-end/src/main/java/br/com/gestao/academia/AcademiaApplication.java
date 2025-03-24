package br.com.gestao.academia;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SpringBootApplication
public class AcademiaApplication {

	private static final Logger logger = LoggerFactory.getLogger(AcademiaApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(AcademiaApplication.class, args);
		logger.info("Started AcademiaApplication");
	}
}
