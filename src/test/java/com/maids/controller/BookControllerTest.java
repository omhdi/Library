package com.maids.controller;

import com.maids.controllers.BookController;
import com.maids.entities.Book;
import com.maids.services.BookService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class BookControllerTest {

    private MockMvc mockMvc;

    @Mock
    private BookService bookService;

    @InjectMocks
    private BookController bookController;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(bookController).build();
    }

    @Test
    public void testUpdateBook() throws Exception{
        Book updatedBook = Book.builder()
                .title("test title")
                .author("test Author")
                .publicationYear(2025)
                .isbn("45698745632")
                .editionNumber(3)
                .category("Test Category")
                .city("Test Damas")
                .build();
        when(bookService.updateBook(any(Book.class), eq(Long.valueOf(updatedBook.getId())))).thenReturn(updatedBook);
        mockMvc.perform(put("/books/{id}", Long.valueOf(updatedBook.getId()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(updatedBook)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("200"))
                .andExpect(jsonPath("$.message").value("Success"));
        verify(bookService, times(1)).updateBook(any(Book.class), eq(Long.valueOf(updatedBook.getId())));
    }

}
