package ru.tinkoff.academy.blackbooks.service;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import ru.tinkoff.academy.blackbooks.dto.BookTransactionDTO;
import ru.tinkoff.academy.blackbooks.enums.SortType;
import ru.tinkoff.academy.blackbooks.mapper.BookTransactionMapper;
import ru.tinkoff.academy.blackbooks.model.BookTransaction;
import ru.tinkoff.academy.blackbooks.repository.BookTransactionRepository;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

import static java.util.List.of;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class BookTransactionServiceTest {
    private final BookTransactionRepository repository = mock(BookTransactionRepository.class);
    private final BookTransactionMapper mapper = mock(BookTransactionMapper.class);
    private final BookTransactionService service = new BookTransactionService(repository, mapper);

    @Test
    void create() {
        BookTransactionDTO bookTransactionDTO = mock(BookTransactionDTO.class);
        BookTransaction bookTransaction = mock(BookTransaction.class);

        when(mapper.mapToEntity(any())).thenReturn(bookTransaction);

        service.create(bookTransactionDTO);

        verify(mapper).mapToEntity(bookTransactionDTO);
        verify(repository).save(bookTransaction);
    }

    @Test
    void get() {
        BookTransaction bookTransaction = mock(BookTransaction.class);
        BookTransactionDTO bookTransactionDTO = mock(BookTransactionDTO.class);

        when(repository.get(any())).thenReturn(bookTransaction);
        when(mapper.mapToDto(any())).thenReturn(bookTransactionDTO);

        assertThat(service.get(new UUID(0, 0)).block()).isEqualTo(bookTransactionDTO);

        verify(repository).get(new UUID(0, 0));
        verify(mapper).mapToDto(bookTransaction);
    }

    @Test
    void getAll() {
        BookTransaction bookTransaction = mock(BookTransaction.class);
        List<BookTransaction> bookTransactionList = of(bookTransaction);
        BookTransactionDTO bookTransactionDTO = mock(BookTransactionDTO.class);

        when(repository.getAll()).thenReturn(bookTransactionList);
        when(mapper.mapToDto(any())).thenReturn(bookTransactionDTO);

        assertThat(service.getAll().blockFirst()).isEqualTo(bookTransactionDTO);

        verify(repository).getAll();
        verify(mapper).mapToDto(bookTransaction);
    }

    @Test
    void getNearestDepositID() {
        UUID bookDepositId = UUID.fromString("00000000-0000-0000-0000-000000000011");
        UUID bookHunterId = UUID.fromString("00000000-0000-0000-0000-000000000021");
        BookTransaction bookTransactionDepositId = new BookTransaction(
                UUID.fromString("00000000-0000-0000-0000-000000000000"),
                bookDepositId,
                UUID.fromString("00000000-0000-0000-0000-000000000020"),
                Timestamp.valueOf("2022-09-08 11:00:22.000000"),
                "returnal"
        );
        BookTransaction bookTransactionAll = new BookTransaction(
                UUID.fromString("00000000-0000-0000-0000-000000000001"),
                bookDepositId,
                bookHunterId,
                Timestamp.valueOf("2022-09-07 11:00:22.000000"),
                "returnal"
        );
        BookTransaction bookTransactionHunterId = new BookTransaction(
                UUID.fromString("00000000-0000-0000-0000-000000000002"),
                UUID.fromString("00000000-0000-0000-0000-000000000010"),
                bookHunterId,
                Timestamp.valueOf("2022-09-06 11:00:22.000000"),
                "returnal"
        );

        when(repository.getAll()).thenReturn(
                of(
                        bookTransactionDepositId,
                        bookTransactionAll,
                        bookTransactionHunterId
                )
        );
        BookTransactionDTO bookTransactionDTODepositId = mock(BookTransactionDTO.class);
        BookTransactionDTO bookTransactionDTOAll = mock(BookTransactionDTO.class);

        when(mapper.mapToDto(bookTransactionDepositId)).thenReturn(bookTransactionDTODepositId);
        when(mapper.mapToDto(bookTransactionAll)).thenReturn(bookTransactionDTOAll);

        Flux<BookTransactionDTO> nearest = service.getNearest(bookDepositId, null, 10L, SortType.DESK);
        assertThat(nearest.collectList().block()).isEqualTo(of(bookTransactionDTODepositId, bookTransactionDTOAll));

        verify(mapper).mapToDto(bookTransactionDepositId);
        verify(mapper).mapToDto(bookTransactionAll);
    }

    @Test
    void getNearestHunterId() {
        UUID bookDepositId = UUID.fromString("00000000-0000-0000-0000-000000000011");
        UUID bookHunterId = UUID.fromString("00000000-0000-0000-0000-000000000021");
        BookTransaction bookTransactionDepositId = new BookTransaction(
                UUID.fromString("00000000-0000-0000-0000-000000000000"),
                bookDepositId,
                UUID.fromString("00000000-0000-0000-0000-000000000020"),
                Timestamp.valueOf("2022-09-08 11:00:22.000000"),
                "returnal"
        );
        BookTransaction bookTransactionAll = new BookTransaction(
                UUID.fromString("00000000-0000-0000-0000-000000000001"),
                bookDepositId,
                bookHunterId,
                Timestamp.valueOf("2022-09-07 11:00:22.000000"),
                "returnal"
        );
        BookTransaction bookTransactionHunterId = new BookTransaction(
                UUID.fromString("00000000-0000-0000-0000-000000000002"),
                UUID.fromString("00000000-0000-0000-0000-000000000010"),
                bookHunterId,
                Timestamp.valueOf("2022-09-06 11:00:22.000000"),
                "returnal"
        );

        when(repository.getAll()).thenReturn(
                of(
                        bookTransactionDepositId,
                        bookTransactionAll,
                        bookTransactionHunterId
                )
        );
        BookTransactionDTO bookTransactionDTOAll = mock(BookTransactionDTO.class);
        BookTransactionDTO bookTransactionDTOHunterId = mock(BookTransactionDTO.class);

        when(mapper.mapToDto(bookTransactionAll)).thenReturn(bookTransactionDTOAll);
        when(mapper.mapToDto(bookTransactionHunterId)).thenReturn(bookTransactionDTOHunterId);

        Flux<BookTransactionDTO> nearest = service.getNearest(null, bookHunterId, 10L, SortType.DESK);
        assertThat(nearest.collectList().block()).isEqualTo(of(bookTransactionDTOAll, bookTransactionDTOHunterId));

        verify(mapper).mapToDto(bookTransactionAll);
        verify(mapper).mapToDto(bookTransactionHunterId);
    }

    @Test
    void getNearestCompose() {
        UUID bookDepositId = UUID.fromString("00000000-0000-0000-0000-000000000011");
        UUID bookHunterId = UUID.fromString("00000000-0000-0000-0000-000000000021");
        BookTransaction bookTransactionDepositId = new BookTransaction(
                UUID.fromString("00000000-0000-0000-0000-000000000000"),
                bookDepositId,
                UUID.fromString("00000000-0000-0000-0000-000000000020"),
                Timestamp.valueOf("2022-09-08 11:00:22.000000"),
                "returnal"
        );
        BookTransaction bookTransactionAll = new BookTransaction(
                UUID.fromString("00000000-0000-0000-0000-000000000001"),
                bookDepositId,
                bookHunterId,
                Timestamp.valueOf("2022-09-07 11:00:22.000000"),
                "returnal"
        );
        BookTransaction bookTransactionHunterId = new BookTransaction(
                UUID.fromString("00000000-0000-0000-0000-000000000002"),
                UUID.fromString("00000000-0000-0000-0000-000000000010"),
                bookHunterId,
                Timestamp.valueOf("2022-09-06 11:00:22.000000"),
                "returnal"
        );

        when(repository.getAll()).thenReturn(
                of(
                        bookTransactionDepositId,
                        bookTransactionAll,
                        bookTransactionHunterId
                )
        );
        BookTransactionDTO bookTransactionDTOAll = mock(BookTransactionDTO.class);

        when(mapper.mapToDto(bookTransactionAll)).thenReturn(bookTransactionDTOAll);

        Flux<BookTransactionDTO> nearest = service.getNearest(bookDepositId, bookHunterId, 10L, SortType.DESK);
        assertThat(nearest.collectList().block()).isEqualTo(of(bookTransactionDTOAll));

        verify(mapper).mapToDto(bookTransactionAll);
    }

    @Test
    void getNearestLimit() {
        UUID bookDepositId = UUID.fromString("00000000-0000-0000-0000-000000000011");
        UUID bookHunterId = UUID.fromString("00000000-0000-0000-0000-000000000021");
        BookTransaction bookTransactionDepositId = new BookTransaction(
                UUID.fromString("00000000-0000-0000-0000-000000000000"),
                bookDepositId,
                UUID.fromString("00000000-0000-0000-0000-000000000020"),
                Timestamp.valueOf("2022-09-08 11:00:22.000000"),
                "returnal"
        );
        BookTransaction bookTransactionAll = new BookTransaction(
                UUID.fromString("00000000-0000-0000-0000-000000000001"),
                bookDepositId,
                bookHunterId,
                Timestamp.valueOf("2022-09-07 11:00:22.000000"),
                "returnal"
        );
        BookTransaction bookTransactionHunterId = new BookTransaction(
                UUID.fromString("00000000-0000-0000-0000-000000000002"),
                UUID.fromString("00000000-0000-0000-0000-000000000010"),
                bookHunterId,
                Timestamp.valueOf("2022-09-06 11:00:22.000000"),
                "returnal"
        );

        when(repository.getAll()).thenReturn(
                of(
                        bookTransactionDepositId,
                        bookTransactionAll,
                        bookTransactionHunterId
                )
        );
        BookTransactionDTO bookTransactionDTODepositId = mock(BookTransactionDTO.class);

        when(mapper.mapToDto(bookTransactionDepositId)).thenReturn(bookTransactionDTODepositId);

        Flux<BookTransactionDTO> nearest = service.getNearest(null, null, 1L, SortType.DESK);
        assertThat(nearest.collectList().block()).isEqualTo(of(bookTransactionDTODepositId));

        verify(mapper).mapToDto(bookTransactionDepositId);
    }

    @Test
    void getNearestSortType() {
        UUID bookDepositId = UUID.fromString("00000000-0000-0000-0000-000000000011");
        UUID bookHunterId = UUID.fromString("00000000-0000-0000-0000-000000000021");
        BookTransaction bookTransactionDepositId = new BookTransaction(
                UUID.fromString("00000000-0000-0000-0000-000000000000"),
                bookDepositId,
                UUID.fromString("00000000-0000-0000-0000-000000000020"),
                Timestamp.valueOf("2022-09-08 11:00:22.000000"),
                "returnal"
        );
        BookTransaction bookTransactionAll = new BookTransaction(
                UUID.fromString("00000000-0000-0000-0000-000000000001"),
                bookDepositId,
                bookHunterId,
                Timestamp.valueOf("2022-09-07 11:00:22.000000"),
                "returnal"
        );
        BookTransaction bookTransactionHunterId = new BookTransaction(
                UUID.fromString("00000000-0000-0000-0000-000000000002"),
                UUID.fromString("00000000-0000-0000-0000-000000000010"),
                bookHunterId,
                Timestamp.valueOf("2022-09-06 11:00:22.000000"),
                "returnal"
        );

        when(repository.getAll()).thenReturn(
                of(
                        bookTransactionDepositId,
                        bookTransactionAll,
                        bookTransactionHunterId
                )
        );
        BookTransactionDTO bookTransactionDTODepositId = mock(BookTransactionDTO.class);
        BookTransactionDTO bookTransactionDTOAll = mock(BookTransactionDTO.class);
        BookTransactionDTO bookTransactionDTOHunterId = mock(BookTransactionDTO.class);

        when(mapper.mapToDto(bookTransactionDepositId)).thenReturn(bookTransactionDTODepositId);
        when(mapper.mapToDto(bookTransactionAll)).thenReturn(bookTransactionDTOAll);
        when(mapper.mapToDto(bookTransactionHunterId)).thenReturn(bookTransactionDTOHunterId);

        Flux<BookTransactionDTO> nearest = service.getNearest(null, null, 10L, SortType.ASK);
        assertThat(nearest.collectList().block()).isEqualTo(of(bookTransactionDTOHunterId, bookTransactionDTOAll, bookTransactionDTODepositId));

        verify(mapper).mapToDto(bookTransactionHunterId);
        verify(mapper).mapToDto(bookTransactionAll);
        verify(mapper).mapToDto(bookTransactionDepositId);
    }

    @Test
    void update() {
        BookTransactionDTO bookTransactionDTO = mock(BookTransactionDTO.class);
        BookTransaction bookTransaction = mock(BookTransaction.class);

        when(mapper.mapToEntity(any())).thenReturn(bookTransaction);

        service.update(new UUID(0, 0), bookTransactionDTO);

        verify(mapper).mapToEntity(bookTransactionDTO);
        verify(repository).update(new UUID(0, 0), bookTransaction);
    }

    @Test
    void delete() {
        service.delete(new UUID(0, 0));

        verify(repository).delete(new UUID(0, 0));
    }
}