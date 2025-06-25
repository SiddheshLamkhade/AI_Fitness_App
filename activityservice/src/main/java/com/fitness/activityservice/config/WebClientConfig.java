package com.fitness.activityservice.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {
	
	
	@Bean
	@LoadBalanced //helps WebClient to resolve the service...
	//...name via Eureka Server
	public WebClient.Builder webClientBuilder(){
		return WebClient.builder();
	}
	
	
	
	//It's creating a WebClient bean in Spring Boot. 
	//It is used to make HTTP calls to other services (like REST APIs).
	@Bean
	// tells Spring to create and manage this WebClient object.
	public WebClient userServiceWebClient (WebClient.Builder webClientBuilder)
	{
		return webClientBuilder.baseUrl("http://USER-SERVICE")
				// sets base URL
				.build(); 
				// builds the WebClient
	}
	
	
	//baseUrl("http://USER-SERVICE") — this is the default address where 
	//it will send requests (to user service in this case).
	
}
