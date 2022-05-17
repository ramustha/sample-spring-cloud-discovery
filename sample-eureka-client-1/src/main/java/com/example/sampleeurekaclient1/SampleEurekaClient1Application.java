package com.example.sampleeurekaclient1;

import com.netflix.discovery.EurekaClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@EnableDiscoveryClient
@SpringBootApplication
public class SampleEurekaClient1Application {

	public static void main(String[] args) {
		SpringApplication.run(SampleEurekaClient1Application.class, args);
	}

	@Autowired
	private EurekaClient eurekaClient;

	@GetMapping("/")
	public Mono<String> home() {
		String instanceId = eurekaClient.getApplicationInfoManager().getInfo().getInstanceId();
		return Mono.just(String.format("Hello from instance %s", instanceId));
	}
}
