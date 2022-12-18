package ru.tinkoff.academy.blackbooks.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClientException;
import reactor.core.publisher.Mono;
import ru.tinkoff.academy.blackbooks.service.BookDepositService;
import ru.tinkoff.academy.blackbooks.service.BookHunterService;
import ru.tinkoff.academy.blackbooks.service.ServiceDiscovery;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("api")
@Tag(name = "Api methods")
public class ApiController {
    private final BookDepositService bookDepositService;
    private final BookHunterService bookHunterService;

    @GetMapping("discovery")
    public Mono<Map<String, String>> discovery() {
        return Mono.just(
                Map.of(
                        bookHunterService.getName(),
                        handleDiscoverService(bookHunterService, bookHunterService.getName()),
                        bookDepositService.getName(),
                        handleDiscoverService(bookDepositService, bookDepositService.getName())
                )
        );
    }

    private String handleDiscoverService(ServiceDiscovery service, String serviceName) {
        String result;
        try {
            result = service.discoverService("/system/version").toJSONString();
        } catch (RestClientException e) {
            result = serviceName + " is not available";
        }
        return result;
    }
}
