package com.maids.services;

import com.maids.entities.Book;
import com.maids.entities.Patron;
import com.maids.entities.Transactions;
import com.maids.exception.NoBookFoundException;
import com.maids.exception.NoPatrensFoundException;
import com.maids.exception.BorrowTransactionNotCompleatException;
import com.maids.exception.ReturnTransactionNotCompleatException;
import com.maids.repositories.BookRepositories;
import com.maids.repositories.PatronsRepositories;
import com.maids.repositories.TransactionsRepositories;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.Optional;

@Service
public class TransactionService {

    @Autowired
    BookRepositories bookRepositories;
    @Autowired
    PatronsRepositories patronsRepositories;
    @Autowired
    TransactionsRepositories transactionsRepositories;
    public Transactions borrowBook(Long patronId, Long bookId) {
        Book book = bookRepositories.findById(bookId).orElseThrow(() -> new NoBookFoundException("Book not found with id: " + bookId));
        Patron patron = patronsRepositories.findById(patronId).orElseThrow(() -> new NoPatrensFoundException("Patron not found with id: " + patronId));
        if (book.getTransactions() != null && book.getTransactions().stream().anyMatch(t -> t.getIs_borrowed())) {
            throw new BorrowTransactionNotCompleatException("Book is already borrowed");
        }
        Date borrowDate = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(borrowDate);
        calendar.add(Calendar.DAY_OF_MONTH, 7);
        Transactions transaction = Transactions.builder()
                .book(book)
                .patron(patron)
                .transaction_date(new Date())
                .return_Date(calendar.getTime())
                .is_borrowed(true)
                .build();
        return transactionsRepositories.save(transaction);
    }

    public void returnBook(Long patronId, Long bookId) {
        Optional<Transactions> transactionOpt = transactionsRepositories.findTransactionByBookAndPAtron(bookId, patronId);
        if (transactionOpt.isEmpty()) {
            throw new ReturnTransactionNotCompleatException("No active transaction found");
        }
        Transactions transaction = transactionOpt.get();
//        if (!transaction.getIs_borrowed()) {
//            throw new BorrowTransactionNotCompleatException("This book has already been returned.");
//        }
        transaction.setIs_borrowed(false);
        transactionsRepositories.save(transaction);
    }
}
