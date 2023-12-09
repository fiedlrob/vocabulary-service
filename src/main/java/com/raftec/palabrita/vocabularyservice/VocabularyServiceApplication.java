package com.raftec.palabrita.vocabularyservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
public class VocabularyServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(VocabularyServiceApplication.class, args);
	}

}
