package com.homemadewonder.www;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class HomeMadeWonderApplication {

	public static void main(String[] args) {
		SpringApplication.run(HomeMadeWonderApplication.class, args);
		System.err.println("HomeMadeWonder application running successfully");
	}

}
