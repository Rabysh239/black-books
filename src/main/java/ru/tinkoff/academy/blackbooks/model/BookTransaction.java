package ru.tinkoff.academy.blackbooks.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Timestamp;
import java.util.UUID;

import static java.util.UUID.randomUUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "book_transaction")
public class BookTransaction {
    @Id
    @Column(name = "id")
    private UUID id;
    @Column(name = "book_deposit_id")
    private UUID bookDepositId;
    @Column(name = "book_hunter_id")
    private UUID bookHunterId;
    @Column(name = "timestamp")
    private Timestamp timestamp;
    @Column(name = "action")
    private String action;
    private Book book;
}
