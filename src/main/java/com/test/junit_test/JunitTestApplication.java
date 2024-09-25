package com.test.junit_test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class JunitTestApplication {

	public static void main(String[] args) {
		SpringApplication.run(JunitTestApplication.class, args);
		System.out.println("APP listen on port: 8080");
	}

}
