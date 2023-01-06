package ru.tinkoff.academy.blackbooks.dto;

import lombok.*;

@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class BookDTO {
    private String transaction;
    private String title;
    private String author;
}
