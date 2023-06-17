package com.mediscreen.microservice_DiabetesReport;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients("com.mediscreen.microservice_DiabetesReport")
@SpringBootApplication
public class MicroserviceDiabetesReportApplication {

	public static void main(String[] args) {
		SpringApplication.run(MicroserviceDiabetesReportApplication.class, args);
	}

}
