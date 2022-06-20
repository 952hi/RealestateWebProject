package com.ssafy.vue;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.MultipartAutoConfiguration;

@SpringBootApplication(exclude = { MultipartAutoConfiguration.class })
public class VueApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(VueApiApplication.class, args);
	}

}
