package ru.tinkoff.academy.blackbooks.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import ru.tinkoff.academy.blackbooks.service.SystemService;

@RestController
@RequiredArgsConstructor
@RequestMapping("system")
@Tag(name="System prob")
public class SystemController {
    private final SystemService service;

    @GetMapping("liveness")
    Mono<String> getLiveness() {
        return service.getProbResponse("health/liveness");
    }

    @GetMapping("readiness")
    Mono<String> getReadiness() {
        return service.getProbResponse("health/readiness");
    }

    @GetMapping("version")
    Mono<String> getHealth() {
        return service.getProbResponse("info");
    }
}
