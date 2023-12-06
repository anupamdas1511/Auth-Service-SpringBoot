package com.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@EnableAutoConfiguration
@ComponentScan
@SpringBootApplication
@EnableScheduling
public class AuthenticationApplication {

	public static void main(String[] args) {
		SpringApplication springApplication = new SpringApplication(AuthenticationApplication.class);
		springApplication.run(args);
//		SpringApplication.run(AuthenticationApplication.class, args);
	}

}
