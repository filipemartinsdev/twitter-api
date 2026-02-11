package com.api.twitter;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.modulith.core.ApplicationModules;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest @ActiveProfiles(value = "test")
class TwitterApiApplicationTests {

	@Test
	void contextLoads() {
	}

	@Test
	void applicationModules(){
		var applicationModules = ApplicationModules.of(TwitterApiApplication.class);
		System.out.println(applicationModules);
	}
}
