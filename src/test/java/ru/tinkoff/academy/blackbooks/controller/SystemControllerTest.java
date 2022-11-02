package ru.tinkoff.academy.blackbooks.controller;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;
import ru.tinkoff.academy.blackbooks.service.SystemService;

import static org.mockito.Mockito.*;

@SpringBootTest
class SystemControllerTest {
    private final SystemService service = mock(SystemService.class);
    private final SystemController controller = new SystemController(service);
    private final WebTestClient webTestClient = WebTestClient.bindToController(controller).build();

    @Test
    public void getLiveness() {
        String response = "{\"status\":\"UP\"}";
        Mono<String> mono = Mono.just(response);

        when(service.getProbResponse(any())).thenReturn(mono);

        webTestClient
                .get()
                .uri("/system/liveness")
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody()
                .json(response);

        verify(service).getProbResponse("health/liveness");
    }

    @Test
    public void getReadiness() {
        String response = "{\"status\":\"UP\"}";
        Mono<String> mono = Mono.just(response);

        when(service.getProbResponse(any())).thenReturn(mono);

        webTestClient
                .get()
                .uri("/system/readiness")
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody()
                .json(response);

        verify(service).getProbResponse("health/readiness");
    }

    @Test
    public void getHealth() {
        String response =
                "{\"build\":" +
                        "{\"artifact\":\"black-books\"," +
                        "\"name\":\"BlackBooks\"," +
                        "\"time\":\"2022-11-01T21:33:55.617Z\"," +
                        "\"version\":\"0.0.1-SNAPSHOT\"," +
                        "\"group\":\"ru.tinkoff.academy\"}}";

        Mono<String> mono = Mono.just(response);

        when(service.getProbResponse(any())).thenReturn(mono);

        webTestClient
                .get()
                .uri("/system/version")
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody()
                .json(response);

        verify(service).getProbResponse("info");
    }
}