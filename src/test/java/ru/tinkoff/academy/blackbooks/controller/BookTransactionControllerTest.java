package ru.tinkoff.academy.blackbooks.controller;

import org.junit.jupiter.api.Test;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.tinkoff.academy.blackbooks.enums.SortType;
import ru.tinkoff.academy.blackbooks.dto.BookTransactionDTO;
import ru.tinkoff.academy.blackbooks.service.BookTransactionService;

import java.util.UUID;

import static java.util.List.of;
import static org.mockito.Mockito.*;

class BookTransactionControllerTest {
    private final BookTransactionService service = mock(BookTransactionService.class);
    private final BookTransactionController controller = new BookTransactionController(service);
    private final WebTestClient webTestClient = WebTestClient.bindToController(controller).build();

    @Test
    void readBookTransactions() {
        BookTransactionDTO bookTransactionDTO = new BookTransactionDTO(
                "Book Overloard",
                "3rd Floor, International House, 1 St Katharine's Way, London E1W 1UN, Великобритания",
                "2022-09-08 11:00:22.000000",
                "returnal"
        );

        when(service.getAll()).thenReturn(Flux.fromIterable(of(bookTransactionDTO)));

        webTestClient
                .get()
                .uri("/transaction")
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody()
                .json(String.format(
                                "[{" +
                                        "\"user\":\"%s\"," +
                                        "\"shelf\":\"%s\"," +
                                        "\"timestamp\":\"%s\"," +
                                        "\"action\":\"%s\"}]",
                                bookTransactionDTO.getUser(),
                                bookTransactionDTO.getShelf(),
                                bookTransactionDTO.getTimestamp(),
                                bookTransactionDTO.getAction()
                        )
                );

        verify(service).getAll();
    }

    @Test
    void readBookTransaction() {
        UUID id = new UUID(0, 0);
        BookTransactionDTO bookTransactionDTO = new BookTransactionDTO(
                "Book Overloard",
                "3rd Floor, International House, 1 St Katharine's Way, London E1W 1UN, Великобритания",
                "2022-09-08 11:00:22.000000",
                "returnal"
        );

        when(service.get(any())).thenReturn(Mono.just(bookTransactionDTO));

        webTestClient
                .get()
                .uri("/transaction/" + id)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody()
                .json(String.format(
                                "{" +
                                        "\"user\":\"%s\"," +
                                        "\"shelf\":\"%s\"," +
                                        "\"timestamp\":\"%s\"," +
                                        "\"action\":\"%s\"}",
                                bookTransactionDTO.getUser(),
                                bookTransactionDTO.getShelf(),
                                bookTransactionDTO.getTimestamp(),
                                bookTransactionDTO.getAction()
                        )
                );

        verify(service).get(id);
    }

    @Test
    void getNearest() {
        UUID id = new UUID(0, 0);
        Long amount = 1L;
        SortType type = SortType.ASK;

        BookTransactionDTO bookTransactionDTO = new BookTransactionDTO(
                "Book Overloard",
                "3rd Floor, International House, 1 St Katharine's Way, London E1W 1UN, Великобритания",
                "2022-09-08 11:00:22.000000",
                "returnal"
        );

        when(service.getNearest(any(), any(), any(), any())).thenReturn(Flux.fromIterable(of(bookTransactionDTO)));

        webTestClient
                .get()
                .uri(uriBuilder ->
                        uriBuilder
                                .path("/transaction/transactions")
                                .queryParam("bookDepositId", id)
                                .queryParam("bookHunterId", id)
                                .queryParam("amount", amount)
                                .queryParam("sortBy", type)
                                .build()
                )
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody()
                .json(String.format(
                                "[{" +
                                        "\"user\":\"%s\"," +
                                        "\"shelf\":\"%s\"," +
                                        "\"timestamp\":\"%s\"," +
                                        "\"action\":\"%s\"}]",
                                bookTransactionDTO.getUser(),
                                bookTransactionDTO.getShelf(),
                                bookTransactionDTO.getTimestamp(),
                                bookTransactionDTO.getAction()
                        )
                );

        verify(service).getNearest(id, id, amount, type);
    }

    @Test
    void createBookTransaction() {
        BookTransactionDTO bookTransactionDTO = new BookTransactionDTO(
                "Book Overloard",
                "3rd Floor, International House, 1 St Katharine's Way, London E1W 1UN, Великобритания",
                "2022-09-08 11:00:22.000000",
                "returnal"
        );

        webTestClient
                .post()
                .uri("/transaction")
                .bodyValue(bookTransactionDTO)
                .exchange()
                .expectStatus()
                .isOk();

        verify(service).create(bookTransactionDTO);
    }

    @Test
    void updateBookTransaction() {
        UUID id = new UUID(0, 0);
        BookTransactionDTO bookTransactionDTO = new BookTransactionDTO(
                "Book Overloard",
                "3rd Floor, International House, 1 St Katharine's Way, London E1W 1UN, Великобритания",
                "2022-09-08 11:00:22.000000",
                "returnal"
        );

        webTestClient
                .put()
                .uri("/transaction/" + id)
                .bodyValue(bookTransactionDTO)
                .exchange()
                .expectStatus()
                .isOk();

        verify(service).update(id, bookTransactionDTO);
    }

    @Test
    void deleteBookTransaction() {
        UUID id = new UUID(0, 0);

        webTestClient
                .delete()
                .uri("/transaction/" + id)
                .exchange()
                .expectStatus()
                .isOk();

        verify(service).delete(id);
    }
}