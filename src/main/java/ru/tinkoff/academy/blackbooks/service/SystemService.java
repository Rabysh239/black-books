package ru.tinkoff.academy.blackbooks.service;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class SystemService {
    private static final String BASE_URL = "actuator/";

    private final WebClient webClient;

    public SystemService() {
        webClient = WebClient.builder().baseUrl("http://localhost:8080/").build();
    }

    public Mono<String> getProbResponse(String suffixUri) {
        return webClient
                .get()
                .uri(BASE_URL + suffixUri)
                .retrieve()
                .bodyToMono(String.class);
    }
}
