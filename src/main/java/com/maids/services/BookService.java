package com.maids.services;

import com.maids.entities.Book;
import com.maids.exception.NoBookFoundException;
import com.maids.repositories.BookRepositories;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    @Autowired
    BookRepositories bookRepositories;
    public List<Book> getAllBooks(){
        return bookRepositories.findAll();
    }

    public Optional<Book> findBookById(Long id){
        return bookRepositories.findById(id);
    }
    public Book getBookById(Long id){
        return findBookById(id).orElseThrow(() -> new NoBookFoundException("No book found with id: " + id));
    }

    public Book createBook(Book book) {
        return bookRepositories.save(book);

    }

    public Book updateBook(Book book, Long id) {
        Book oldBook = bookRepositories.findById(id)
                .orElseThrow(() -> new NoBookFoundException("Book not found with id: " + id));
        oldBook.setTitle(book.getTitle());
        oldBook.setAuthor(book.getAuthor());
        oldBook.setPublicationYear(book.getPublicationYear());
        oldBook.setIsbn(book.getIsbn());
        oldBook.setEditionNumber(book.getEditionNumber());
        oldBook.setCategory(book.getCategory());
        oldBook.setCity(book.getCity());
        return bookRepositories.save(oldBook);
    }

    public void deleteById(Long id) {
        if (!bookRepositories.existsById(id)) {
            throw new NoBookFoundException("Book not found with id: " + id);
        }
        bookRepositories.deleteById(id);
    }
}
