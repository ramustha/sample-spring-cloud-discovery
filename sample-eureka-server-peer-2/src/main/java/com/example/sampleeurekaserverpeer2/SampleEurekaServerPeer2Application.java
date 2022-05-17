package com.example.sampleeurekaserverpeer2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@EnableEurekaServer
@SpringBootApplication
public class SampleEurekaServerPeer2Application {

	public static void main(String[] args) {
		SpringApplication.run(SampleEurekaServerPeer2Application.class, args);
	}

}
