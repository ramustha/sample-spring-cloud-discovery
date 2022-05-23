package com.example.sampleeurekaclient1;

import com.netflix.discovery.EurekaClient;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandProperties;
import com.netflix.hystrix.HystrixObservableCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.Customizer;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.hystrix.ReactiveHystrixCircuitBreakerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

@RestController
@EnableDiscoveryClient
@SpringBootApplication
public class SampleEurekaClient1Application {
	private static final Logger LOG = LoggerFactory.getLogger(SampleEurekaClient1Application.class);

	public static void main(String[] args) {
		SpringApplication.run(SampleEurekaClient1Application.class, args);
	}

	@Autowired
	private EurekaClient eurekaClient;
	@Autowired
	private ReactiveHystrixCircuitBreakerFactory circuitBreakerFactory;

	@GetMapping("/")
	public Mono<String> home(@RequestHeader("Authorization") Optional<String> authorization) {
		LOG.info("authorization => {}", authorization);
		String instanceId = eurekaClient.getApplicationInfoManager().getInfo().getInstanceId();
		int randomSeconds = ThreadLocalRandom.current().nextInt(3);
		return circuitBreakerFactory.create("home")
				.run(Mono.delay(Duration.ofSeconds(randomSeconds)) // simulate delay
								.then(Mono.just(String.format("Hello from instance %s", instanceId))),
						throwable -> Mono.just("Fallback from circuit breaker with delay " + randomSeconds + " seconds"));
	}
}

@Configuration
class CircuitBreakerCustomizer {
	@Bean
	public Customizer<ReactiveHystrixCircuitBreakerFactory> defaultConfig() {
		return factory -> factory.configureDefault(id -> HystrixObservableCommand.Setter
				.withGroupKey(HystrixCommandGroupKey.Factory.asKey(id))
				.andCommandPropertiesDefaults(HystrixCommandProperties.Setter()
						.withExecutionTimeoutInMilliseconds(2000)) // timeout 2 seconds
		);
	}
}
