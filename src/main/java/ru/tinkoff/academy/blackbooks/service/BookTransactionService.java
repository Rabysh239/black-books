package ru.tinkoff.academy.blackbooks.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.tinkoff.academy.blackbooks.mapper.BookTransactionMapper;
import ru.tinkoff.academy.blackbooks.enums.SortType;
import ru.tinkoff.academy.blackbooks.model.BookTransaction;
import ru.tinkoff.academy.blackbooks.dto.BookTransactionDTO;
import ru.tinkoff.academy.blackbooks.repository.BookTransactionRepository;

import java.util.Comparator;
import java.util.UUID;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Comparator.comparing;
import static ru.tinkoff.academy.blackbooks.enums.SortType.ASK;

@Service
@RequiredArgsConstructor
public class BookTransactionService {
    private final BookTransactionRepository repository;
    private final BookTransactionMapper mapper;
    private static final Comparator<BookTransaction> TIMESTAMP_COMPARATOR = comparing(BookTransaction::getTimestamp);

    public void create(BookTransactionDTO bookTransactionDTO) {
        BookTransaction bookTransaction = mapper.mapToEntity(bookTransactionDTO);
        repository.save(bookTransaction);
    }

    public Mono<BookTransactionDTO> get(UUID id) {
        BookTransaction bookTransaction = repository.get(id);
        BookTransactionDTO bookTransactionDTO = mapper.mapToDto(bookTransaction);
        return Mono.just(bookTransactionDTO);
    }

    public Flux<BookTransactionDTO> getAll() {
        return mapToFluxDTO(repository.getAll().stream());
    }

    public Flux<BookTransactionDTO> getNearest(UUID bookDepositId, UUID bookHunterId, Long amount, SortType type) {
        Predicate<BookTransaction> depositPredicate = getPredicate(bookDepositId, BookTransaction::getBookDepositId);
        Predicate<BookTransaction> hunterPredicate = getPredicate(bookHunterId, BookTransaction::getBookHunterId);
        return mapToFluxDTO(repository
                .getAll()
                .stream()
                .filter(depositPredicate.and(hunterPredicate))
                .sorted(type == ASK ? TIMESTAMP_COMPARATOR : TIMESTAMP_COMPARATOR.reversed())
                .limit(amount)
        );
    }

    public void update(UUID id, BookTransactionDTO bookTransactionDTO) {
        BookTransaction bookTransaction = mapper.mapToEntity(bookTransactionDTO, id);
        repository.update(id, bookTransaction);
    }

    public void delete(UUID id) {
        repository.delete(id);
    }

    private Flux<BookTransactionDTO> mapToFluxDTO(Stream<BookTransaction> stream) {
        return Flux.fromIterable(stream.map(mapper::mapToDto).collect(Collectors.toList()));
    }

    private Predicate<BookTransaction> getPredicate(UUID id, Function<BookTransaction, UUID> getId) {
        if (id == null) {
            return (t) -> true;
        }
        return (t) -> getId.apply(t) == id;
    }
}
