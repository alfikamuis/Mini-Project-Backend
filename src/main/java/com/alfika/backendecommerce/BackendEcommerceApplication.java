package com.alfika.backendecommerce;

import com.github.javafaker.Faker;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class BackendEcommerceApplication {

	public static void main(String[] args) {
		SpringApplication.run(BackendEcommerceApplication.class, args);
	}

	@Bean
	Faker faker(){ return new Faker();}

	@Bean
	CommandLineRunner commandLineRunner(){
		return args ->  {
			System.out.println("No problem in here.........");
		};
	}

}
