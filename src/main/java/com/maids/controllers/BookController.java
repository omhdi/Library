package com.maids.controllers;

import com.maids.dto.BookDTO;
import com.maids.entities.Book;
import com.maids.entities.CustomResponseEntity;
import com.maids.exception.NoBookFoundException;
import com.maids.services.BookService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api")
public class BookController {

    @Autowired
    BookService bookService;
    @GetMapping("/books")
    @Cacheable(value = "AllBookCache")
    public ResponseEntity<Object> getAllBooks(){
        List<Book> books = bookService.getAllBooks();
        CustomResponseEntity<List<Book>> response = new CustomResponseEntity(HttpStatus.OK.toString(), "Success", books);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/books/{id}")
    public ResponseEntity<Object> getBook(@PathVariable Long id) {
        Book book = bookService.getBookById(id);
        CustomResponseEntity<Book> response = new CustomResponseEntity<>(HttpStatus.OK.toString(), "Success", Collections.singletonList(book));
        return new ResponseEntity<>(response, HttpStatus.OK);
}
    @PostMapping("/books")
    @Transactional
    public ResponseEntity<CustomResponseEntity<Book>> createBook(@Valid @RequestBody BookDTO bookdto) {
        Book book = Book.builder()
                .title(bookdto.getTitle())
                .author(bookdto.getAuthor())
                .publicationYear(bookdto.getPublicationYear())
                .isbn(bookdto.getIsbn())
                .editionNumber(bookdto.getEditionNumber())
                .category(bookdto.getCategory())
                .city(bookdto.getCity())
                .build();
            bookService.createBook(book);
            CustomResponseEntity<Book> response = new CustomResponseEntity<>(HttpStatus.OK.toString(), "Success", null);
            return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/books/{id}")
    @Transactional
    public ResponseEntity<Object> updateBook(@PathVariable Long id,@Valid @RequestBody BookDTO bookdto) {
        Book book = Book.builder()
                .title(bookdto.getTitle())
                .author(bookdto.getAuthor())
                .publicationYear(bookdto.getPublicationYear())
                .isbn(bookdto.getIsbn())
                .editionNumber(bookdto.getEditionNumber())
                .category(bookdto.getCategory())
                .city(bookdto.getCity())
                .build();
            Book bookUpdate = bookService.updateBook(book, id);
            CustomResponseEntity<Book> response = new CustomResponseEntity<>(HttpStatus.OK.toString(), "Success", null);
            return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/books/{id}")
    @Transactional
    public ResponseEntity<Object> deleteBook(@PathVariable Long id) {
        bookService.deleteById(id);
        CustomResponseEntity<Book> response = new CustomResponseEntity<>(HttpStatus.OK.toString(), "Success", null);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
