package com.devs4j.client;

import com.devs4j.client.clients.DragonBallCharacterClient;
import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import com.netflix.discovery.shared.Application;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.http.ResponseEntity;

import java.util.List;

@SpringBootApplication
@EnableFeignClients
public class Devs4jClientStandaloneApplication implements ApplicationRunner {

	private final static Logger log = LoggerFactory.getLogger(Devs4jClientStandaloneApplication.class);

	@Autowired
	private EurekaClient eurekaClient;

	@Autowired
	private DragonBallCharacterClient dragonBallClient;

	public static void main(String[] args) {
		SpringApplication.run(Devs4jClientStandaloneApplication.class, args);
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		for(int i = 0 ; i < 10 ; i++) {
			ResponseEntity<String> response = dragonBallClient.getApplicationName();
			log.info("Status {}", response.getStatusCode());
			String body = response.getBody();
			log.info("Body {}", body);
		}
	}

//	@Override
//	public void run(ApplicationArguments args) throws Exception {
//		Application applciation = eurekaClient.getApplication("devs4j-dragon-ball");
//		log.info("#############################");
//		log.info("Application name {}", applciation.getName());
//
//		List<InstanceInfo> instanceInfos = applciation.getInstances();
//		for(InstanceInfo instanceInfo : instanceInfos) {
//			log.info("Ip address {}:{}", instanceInfo.getIPAddr(), instanceInfo.getPort());
//		}
//		log.info("#############################");
//	}
}
