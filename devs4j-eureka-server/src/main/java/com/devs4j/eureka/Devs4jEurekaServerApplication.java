package com.devs4j.eureka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class Devs4jEurekaServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(Devs4jEurekaServerApplication.class, args);
	}

}
