package ru.tinkoff.academy.blackbooks.service;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class BookDepositServiceTest {
    private final String name = "BookDeposit";
    @Mock
    private final RestTemplate restTemplate = mock(RestTemplate.class);
    private final String url = "http://localhost:8070";
    private final BookDepositService bookDepositService = new BookDepositService(name, restTemplate, url);

    @Test
    void getName() {
        assertEquals(name, bookDepositService.getName());
    }

    @Test
    void discoverService() {
        String uri = "/system/version";
        String response = "response";

        when(restTemplate.getForObject(anyString(), ArgumentMatchers.any(Class.class))).thenReturn(response);

        assertEquals(response, bookDepositService.discoverService(uri).toJSONString());

        verify(restTemplate).getForObject(url + uri, String.class);
    }
}