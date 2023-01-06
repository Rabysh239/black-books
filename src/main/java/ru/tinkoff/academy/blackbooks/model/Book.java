package ru.tinkoff.academy.blackbooks.model;

import lombok.*;

import java.util.UUID;

import static java.util.UUID.randomUUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Book {
    private UUID id = randomUUID();
    private String title;
    private String author;
    private BookTransaction transaction;
}