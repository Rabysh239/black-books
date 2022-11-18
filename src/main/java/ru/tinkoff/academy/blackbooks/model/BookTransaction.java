package ru.tinkoff.academy.blackbooks.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.UUID;

import static java.util.UUID.randomUUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BookTransaction {
    private UUID id = randomUUID();
    private UUID bookDepositId;
    private UUID bookHunterId;
    private Timestamp timestamp;
    private String action;
}
