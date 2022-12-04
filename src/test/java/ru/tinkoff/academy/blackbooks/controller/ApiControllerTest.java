package ru.tinkoff.academy.blackbooks.controller;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.client.RestClientException;
import ru.tinkoff.academy.blackbooks.service.BookDepositService;
import ru.tinkoff.academy.blackbooks.service.BookHunterService;

import static java.lang.String.format;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
class ApiControllerTest {
    private final BookDepositService bookDepositService = mock(BookDepositService.class);
    private final BookHunterService bookHunterService = mock(BookHunterService.class);
    private final ApiController controller = new ApiController(bookDepositService, bookHunterService);
    private final WebTestClient webTestClient = WebTestClient.bindToController(controller).build();


    @Test
    void discovery() {
        String bookDepositServiceName = "BookDeposit";
        String bookHunterServiceName = "BookHunter";
        String bookDepositServiceResponse ="response";
        String response = format("{\"%s\":\"%s\",\"%s\":\"%s is not available\"}",
                bookDepositServiceName, bookDepositServiceResponse,
                bookHunterServiceName, bookHunterServiceName
        );

        when(bookDepositService.getName()).thenReturn(bookDepositServiceName);
        when(bookDepositService.discoverService(any())).thenReturn(() -> bookDepositServiceResponse);
        when(bookHunterService.getName()).thenReturn(bookHunterServiceName);
        when(bookHunterService.discoverService(any())).thenThrow(RestClientException.class);

        webTestClient
                .get()
                .uri("/api/discovery")
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody()
                .json(response);

        verify(bookDepositService, times(2)).getName();
        verify(bookDepositService).discoverService("/system/version");
        verify(bookHunterService, times(2)).getName();
        verify(bookHunterService).discoverService("/system/version");
    }
}