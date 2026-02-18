package com.api.twitter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class TwitterApiApplication {
	public static void main(String[] args) {
		SpringApplication.run(TwitterApiApplication.class, args);
	}
}
