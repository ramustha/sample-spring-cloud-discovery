package com.example.sampleeurekaclient2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@RestController
@EnableDiscoveryClient
@SpringBootApplication
public class SampleEurekaClient2Application {

	public static void main(String[] args) {
		SpringApplication.run(SampleEurekaClient2Application.class, args);
	}

	@Autowired
	private WebClient.Builder webClientBuilder;

	@GetMapping("/")
	public Mono<String> home() {
		return webClientBuilder.build()
				.get().uri("http://eureka-client-1/")
				.retrieve()
				.bodyToMono(String.class);
	}

}

