package com.example.sampleeurekaserverpeer1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@EnableEurekaServer
@SpringBootApplication
public class SampleEurekaServerPeer1Application {

	public static void main(String[] args) {
		SpringApplication.run(SampleEurekaServerPeer1Application.class, args);
	}

}
