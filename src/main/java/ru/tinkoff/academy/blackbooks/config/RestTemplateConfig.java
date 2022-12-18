package ru.tinkoff.academy.blackbooks.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import static java.time.Duration.ofSeconds;

@Configuration
public class RestTemplateConfig {
    private final Long connectTimeOut;
    private final Long readTimeOut;

    public RestTemplateConfig(@Value("${timeOut.connect}") Long connectTimeOut, @Value("${timeOut.read}") Long readTimeOut) {
        this.connectTimeOut = connectTimeOut;
        this.readTimeOut = readTimeOut;
    }

    @Bean
    public RestTemplateBuilder restTemplateBuilder() {
        return new RestTemplateBuilder();
    }

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder restTemplateBuilder) {
        return restTemplateBuilder
                .setConnectTimeout(ofSeconds(connectTimeOut))
                .setReadTimeout(ofSeconds(readTimeOut))
                .build();
    }
}
