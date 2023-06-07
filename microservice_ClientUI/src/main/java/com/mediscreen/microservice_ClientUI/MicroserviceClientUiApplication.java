package com.mediscreen.microservice_ClientUI;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients("com.mediscreen.microservice_ClientUI")
@SpringBootApplication
public class MicroserviceClientUiApplication {

	public static void main(String[] args) {
		SpringApplication.run(MicroserviceClientUiApplication.class, args);
	}

}
