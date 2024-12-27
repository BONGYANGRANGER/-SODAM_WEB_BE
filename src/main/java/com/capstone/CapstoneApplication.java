package com.capstone;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

<<<<<<< HEAD
@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
=======
@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
>>>>>>> 761439f21bb69bd3039ca3b02184dc51a9d6fdd2
public class CapstoneApplication {

	public static void main(String[] args) {
		SpringApplication.run(CapstoneApplication.class, args);
	}
}
