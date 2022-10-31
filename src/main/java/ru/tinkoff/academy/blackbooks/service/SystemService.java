package ru.tinkoff.academy.blackbooks.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class SystemService {
    private static final String BASE_URL = "actuator/";
    private final WebClient webClient;

    public Mono<String> getProbResponse(String suffixUri) {
        return webClient
                .get()
                .uri(BASE_URL + suffixUri)
                .retrieve()
                .bodyToMono(String.class);
    }
}
