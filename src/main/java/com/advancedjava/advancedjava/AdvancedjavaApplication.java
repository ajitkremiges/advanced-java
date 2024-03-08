package com.advancedjava.advancedjava;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.advancedjava.advancedjava")
public class AdvancedjavaApplication {

	public static void main(String[] args) {
		SpringApplication.run(AdvancedjavaApplication.class, args);
	
	}

}
