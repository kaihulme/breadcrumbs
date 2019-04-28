package com.spe.breadcrumbs.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;

import java.sql.Connection;
import java.util.Optional;
import java.util.Properties;

@SpringBootApplication
public class WebApplication {

	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(WebApplication.class);
		app.run();

//		String home = System.getProperty("user.home");
//		System.out.println(home);

	}
}
