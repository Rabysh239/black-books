package ru.tinkoff.academy.blackbooks.service;

import org.junit.jupiter.api.Test;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class SystemServiceTest {
    private final WebClient webClient = mock(WebClient.class);
    private final SystemService service = new SystemService(webClient);

    @Test
    void getProbResponse() {
        String suffixUri = "info";
        var requestHeadersUriSpecMock = mock(WebClient.RequestHeadersUriSpec.class);
        WebClient.RequestBodySpec requestBodySpec = mock(WebClient.RequestBodySpec.class);
        WebClient.ResponseSpec responseSpec = mock(WebClient.ResponseSpec.class);
        var mono = mock(Mono.class);

        when(webClient.get()).thenReturn(requestHeadersUriSpecMock);
        when(requestHeadersUriSpecMock.uri(any(String.class))).thenReturn(requestBodySpec);
        when(requestBodySpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.bodyToMono(any(Class.class))).thenReturn(mono);

        assertThat(service.getProbResponse(suffixUri)).isEqualTo(mono);

        verify(webClient).get();
        verify(requestHeadersUriSpecMock).uri("actuator/" + suffixUri);
        verify(requestBodySpec).retrieve();
        verify(responseSpec).bodyToMono(String.class);

    }
}