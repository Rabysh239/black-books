package ru.tinkoff.academy.blackbooks.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BookTransactionDTO {
    private String user;
    private String shelf;
    private String timestamp;
    private String action;
}
