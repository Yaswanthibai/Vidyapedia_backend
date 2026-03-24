package com.example.college_predicter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
public class CollegePredicterApplication {

	public static void main(String[] args) {
		SpringApplication.run(CollegePredicterApplication.class, args);
	}

}