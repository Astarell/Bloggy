package com.astarell.Bloggy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

@SpringBootApplication
public class BloggyApplication {

	public static void main(String[] args) {
		SpringApplication.run(BloggyApplication.class, args);
	}

}
