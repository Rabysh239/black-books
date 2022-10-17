package ru.tinkoff.academy.blackbooks.repository;

import org.springframework.stereotype.Repository;
import ru.tinkoff.academy.blackbooks.model.BookTransaction;

import java.util.*;

@Repository
public class BookTransactionRepository {
    private final Map<UUID, BookTransaction> table;

    public BookTransactionRepository() {
        table = new HashMap<>();
    }

    public void save(BookTransaction bookTransaction) {
        table.put(bookTransaction.getId(), bookTransaction);
    }

    public BookTransaction get(UUID id) {
        return table.get(id);
    }

    public List<BookTransaction> getAll() {
        return new ArrayList<>(table.values());
    }

    public void update(UUID id, BookTransaction bookTransaction) {
        table.put(id, bookTransaction);
    }

    public void delete(UUID id) {
        table.remove(id);
    }
}
