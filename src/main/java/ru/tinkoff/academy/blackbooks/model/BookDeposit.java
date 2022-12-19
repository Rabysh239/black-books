package ru.tinkoff.academy.blackbooks.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "book_deposit")
public class BookDeposit {
    @Id
    @Column(name = "id")
    private UUID id;
    @Column(name = "nick")
    private String nick;
    @Column(name = "address")
    private String address;
    @Column(name = "description")
    private String description;
    @Column(name = "type")
    private String type;
    @Column(name = "latitude")
    private Double latitude;
    @Column(name = "longitude")
    private Double longitude;
}
