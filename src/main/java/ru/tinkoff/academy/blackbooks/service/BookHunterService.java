package ru.tinkoff.academy.blackbooks.service;

import org.json.JSONString;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

import static java.util.UUID.randomUUID;

@Service
public class BookHunterService implements ServiceDiscovery {
    private final String name;
    private final RestTemplate restTemplate;
    private final String url;
    private final Map<String, UUID> repository = new HashMap<>();


    public BookHunterService(
            @Value("${bookHunterService.name}") String name,
            RestTemplate restTemplate,
            @Value("${bookHunterService.url}") String url
    ) {
        this.name = name;
        this.restTemplate = restTemplate;
        this.url = url;
    }

    public UUID getBookHunterId(String address) {
        repository.putIfAbsent(address, randomUUID());
        return repository.get(address);
    }

    public String getBookHunterNick(UUID id) {
        for (Map.Entry<String, UUID> entry : repository.entrySet()) {
            if (entry.getValue() == id) {
                return entry.getKey();
            }
        }
        return null;
    }

    public String getName() {
        return name;
    }

    @Override
    public JSONString discoverService(String url) {
        return () -> restTemplate.getForObject(this.url + url, String.class);
    }
}
