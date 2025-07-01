package com.fitness.aiservice.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class GeminiService {

    private final WebClient webClient;

    @Value("${gemini.api.key}")
    private String geminiApiKey;

    @Value("${gemini.api.path}")
    private String geminiApiPath;

    public GeminiService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder
                .baseUrl("https://generativelanguage.googleapis.com")
                .build();
    }

    public String getAnswer(String question) {
        Map<String, Object> requestBody = Map.of(
            "contents", new Object[] {
                Map.of("parts", new Object[] {
                    Map.of("text", question)
                })
            }
        );

        return webClient.post()
            .uri(uriBuilder -> uriBuilder
                .path(geminiApiPath)
                .queryParam("key", geminiApiKey)
                .build())
            .header("Content-Type", "application/json")
            .bodyValue(requestBody)
            .retrieve()
            .bodyToMono(String.class)
            .block();
    }
}
