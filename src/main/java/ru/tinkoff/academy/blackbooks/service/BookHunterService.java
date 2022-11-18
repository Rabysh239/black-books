package ru.tinkoff.academy.blackbooks.service;

import org.springframework.stereotype.Service;

import java.util.*;

import static java.util.UUID.randomUUID;

@Service
public class BookHunterService {
    private final Map<String, UUID> repository = new HashMap<>();

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

}
