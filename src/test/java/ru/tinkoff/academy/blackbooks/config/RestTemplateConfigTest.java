package ru.tinkoff.academy.blackbooks.config;

import org.junit.jupiter.api.Test;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.web.client.RestTemplate;

import static java.time.Duration.ofSeconds;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class RestTemplateConfigTest {
    private final Long connectTimeOut = 2L;
    private final Long readTimeOut = 3L;
    private final RestTemplateConfig restTemplateConfig = new RestTemplateConfig(connectTimeOut, readTimeOut);

    @Test
    void restTemplateBuilder() {
        assertInstanceOf(RestTemplateBuilder.class, restTemplateConfig.restTemplateBuilder());
    }

    @Test
    void restTemplate() {
        RestTemplateBuilder restTemplateBuilder = mock(RestTemplateBuilder.class);
        RestTemplate restTemplate = mock(RestTemplate.class);

        when(restTemplateBuilder.setConnectTimeout(any())).thenReturn(restTemplateBuilder);
        when(restTemplateBuilder.setReadTimeout(any())).thenReturn(restTemplateBuilder);
        when(restTemplateBuilder.build()).thenReturn(restTemplate);

        assertEquals(restTemplate, restTemplateConfig.restTemplate(restTemplateBuilder));

        verify(restTemplateBuilder).setConnectTimeout(ofSeconds(connectTimeOut));
        verify(restTemplateBuilder).setReadTimeout(ofSeconds(readTimeOut));
        verify(restTemplateBuilder).build();
    }
}