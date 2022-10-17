package ru.tinkoff.academy.blackbooks.adapter;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.tinkoff.academy.blackbooks.model.BookTransaction;
import ru.tinkoff.academy.blackbooks.model.BookTransactionDTO;
import ru.tinkoff.academy.blackbooks.service.BookDepositService;
import ru.tinkoff.academy.blackbooks.service.BookHunterService;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.UUID;

import static java.sql.Timestamp.valueOf;
import static org.modelmapper.config.Configuration.AccessLevel.PRIVATE;

@Component
public class BookTransactionMapper {
    private final ModelMapper modelMapper;
    private final SimpleDateFormat formatter;
    @Autowired
    private BookDepositService bookDepositService;
    @Autowired
    private BookHunterService bookHunterService;

    public BookTransactionMapper() {
        modelMapper = new ModelMapper();
        modelMapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.STRICT)
                .setFieldMatchingEnabled(true)
                .setSkipNullEnabled(true)
                .setFieldAccessLevel(PRIVATE);
        formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSSSS");
    }

    public BookTransaction mapToEntity(BookTransactionDTO bookTransactionDTO) {
        if (bookTransactionDTO == null) {
            return null;
        }
        BookTransaction bookTransaction = modelMapper.map(bookTransactionDTO, BookTransaction.class);
        UUID bookDepositId = bookDepositService.getBookDepositId(bookTransactionDTO.getShelf());
        bookTransaction.setBookDepositId(bookDepositId);
        UUID bookHunterId = bookHunterService.getBookHunterId(bookTransactionDTO.getUser());
        bookTransaction.setBookHunterId(bookHunterId);
        Timestamp timestamp = valueOf(bookTransactionDTO.getTimestamp());
        bookTransaction.setTimestamp(timestamp);
        return bookTransaction;
    }

    public BookTransactionDTO mapToDto(BookTransaction bookTransaction) {
        if (bookTransaction == null) {
            return null;
        }
        BookTransactionDTO bookTransactionDTO = modelMapper.map(bookTransaction, BookTransactionDTO.class);
        String bookDepositAddress = bookDepositService.getBookDepositAddress(bookTransaction.getBookDepositId());
        bookTransactionDTO.setShelf(bookDepositAddress);
        String bookHunterNick = bookHunterService.getBookHunterNick(bookTransaction.getBookHunterId());
        bookTransactionDTO.setUser(bookHunterNick);
        String timestamp = formatter.format(bookTransaction.getTimestamp());
        bookTransactionDTO.setTimestamp(timestamp);
        return bookTransactionDTO;
    }
}
