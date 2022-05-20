package com.example.samplenetflixzuulgateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.ribbon.RibbonClients;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

@EnableZuulProxy
@EnableDiscoveryClient
@SpringBootApplication
@RibbonClients(defaultConfiguration = RibbonEurekaClientConfig.class)
public class SampleNetflixZuulGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(SampleNetflixZuulGatewayApplication.class, args);
	}

}
