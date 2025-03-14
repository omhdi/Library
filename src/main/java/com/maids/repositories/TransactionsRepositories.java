package com.maids.repositories;

import com.maids.entities.Transactions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TransactionsRepositories extends JpaRepository<Transactions, Long> {

//    @Query(name = "select * from transactions where book_id =? and patron_id =? and is_borrowed= true")
//    Optional<Transactions> GetTransaction(@Param("book_id")Long book_id, @Param("patron_id") Long patron_id);
//    @Query(value = "select count(*) from transactions where book_id = :BookId and is_borrowed = true")
//    int findByBookId(@Param("bookId") Long BookId);
    @Query(value = "SELECT COUNT(*) FROM Transactions  WHERE book_id = :bookId AND is_borrowed = true", nativeQuery = true)
    int findTransactionByBookId(@Param("bookId") Long bookId);

    @Query(value = "update trasactions set is_borrowed= false where id = :id", nativeQuery = true)
    int updateTransactionForReturning(@Param("id") Long id);

    @Query(value = "SELECT * FROM transactions where book_id = :book_id and patron_id = :patron_id and is_borrowed = true", nativeQuery = true)
    Optional<Transactions> findTransactionByBookAndPAtron(@Param("book_id") Long book_id, @Param("patron_id") Long patron_id);
}
