package ru.tinkoff.academy.blackbooks.dto;

import lombok.*;

@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class BookTransactionDTO {
    private String user;
    private String shelf;
    private String bookName;
    private String bookAuthor;
    private String timestamp;
    private String action;
}
