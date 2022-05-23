package com.example.sampleeurekaclient2;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;

@RestController
@EnableDiscoveryClient
@SpringBootApplication
public class SampleEurekaClient2Application {
	private static final Logger LOG = LoggerFactory.getLogger(SampleEurekaClient2Application.class);

	public static void main(String[] args) {
		SpringApplication.run(SampleEurekaClient2Application.class, args);
	}

	@Autowired
	private WebClient.Builder webClientBuilder;

	@GetMapping("/")
	public Mono<String> home(ServerHttpRequest request) {
		LOG.info("headers => {}", request.getHeaders());

		return webClientBuilder.build()
				.get().uri("http://eureka-client-1/")
				.headers(httpHeaders -> addRequestedHeaders(request, httpHeaders))
				.retrieve()
				.bodyToMono(String.class);
	}

	private static void addRequestedHeaders(ServerHttpRequest request, HttpHeaders httpHeaders) {
		for (Map.Entry<String, List<String>> stringListEntry : request.getHeaders().entrySet()) {
			httpHeaders.addAll(stringListEntry.getKey(), stringListEntry.getValue());
		}
	}

}

