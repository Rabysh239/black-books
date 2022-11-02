package ru.tinkoff.academy.blackbooks.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.tinkoff.academy.blackbooks.dto.BookTransactionDTO;
import ru.tinkoff.academy.blackbooks.service.BookTransactionService;
import ru.tinkoff.academy.blackbooks.enums.SortType;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("transaction")
@Tag(name="Transaction operations")
public class BookTransactionController {
    private final BookTransactionService service;

    @GetMapping
    public Flux<BookTransactionDTO> readBookTransactions() {
        return service.getAll();
    }

    @GetMapping("{id}")
    public Mono<BookTransactionDTO> readBookTransaction(@PathVariable UUID id) {
        return service.get(id);
    }

    @GetMapping("transactions")
    public Flux<BookTransactionDTO> getNearest(
            @RequestParam(required = false) UUID bookDepositId,
            @RequestParam(required = false) UUID bookHunterId,
            @RequestParam(required = false, defaultValue = "10") Long amount,
            @RequestParam(required = false, name = "sortBy", defaultValue = "DESK") SortType type
    ) {
        return service.getNearest(bookDepositId, bookHunterId, amount, type);
    }

    @PostMapping
    public void createBookTransaction(@RequestBody BookTransactionDTO bookTransactionDTO) {
        service.create(bookTransactionDTO);
    }

    @PutMapping("{id}")
    public void updateBookTransaction(@PathVariable UUID id, @RequestBody BookTransactionDTO bookTransactionDTO) {
        service.update(id, bookTransactionDTO);
    }

    @DeleteMapping("{id}")
    public void deleteBookTransaction(@PathVariable UUID id) {
        service.delete(id);
    }
}
