package com.ingress.bookstore.controller;

import com.ingress.bookstore.dto.BookDTO;
import com.ingress.bookstore.model.Author;
import com.ingress.bookstore.model.Student;
import com.ingress.bookstore.service.AuthorService;
import com.ingress.bookstore.service.BookService;
import com.ingress.bookstore.service.MailService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

public class AuthorControllerTest {

    @Mock
    private BookService bookService;

    @Mock
    private MailService mailService;

    @Mock
    private AuthorService authorService;

    @InjectMocks
    private AuthorController authorController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testAddBook() {
        Long authorId = 1L;
        BookDTO bookDTO = new BookDTO();
        bookDTO.setName("Test Book");
        bookDTO.setAuthorId(authorId);

        Author author = new Author();
        author.setId(authorId);
        List<Student> followers = new ArrayList<>();
        author.setFollowers(followers);

        when(authorService.getAuthorById(authorId)).thenReturn(Optional.of(author));

        ResponseEntity<String> response = authorController.addBook(authorId, bookDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Book added successfully", response.getBody());

        verify(mailService, times(1)).sendMail(followers);
        verify(bookService, times(1)).addBook(bookDTO);
    }

    @Test
    public void testAddBookAuthorNotFound() {
        Long authorId = 1L;
        BookDTO bookDTO = new BookDTO();
        bookDTO.setName("Test Book");
        bookDTO.setAuthorId(1L);

        when(authorService.getAuthorById(authorId)).thenReturn(Optional.empty());

        ResponseEntity<String> response = authorController.addBook(authorId, bookDTO);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Author not found with id: " + authorId, response.getBody());

        verify(mailService, never()).sendMail(anyList());
        verify(bookService, never()).addBook(any(BookDTO.class));
    }

    @Test
    public void testDeleteAuthorBook() {
        Long authorId = 1L;
        Long bookId = 2L;

        when(authorService.deleteAuthorBook(authorId, bookId)).thenReturn(true);

        ResponseEntity<String> response = authorController.deleteAuthorBook(authorId, bookId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Book deleted successfully.", response.getBody());
    }

    @Test
    public void testDeleteAuthorBookFailed() {
        Long authorId = 1L;
        Long bookId = 2L;

        when(authorService.deleteAuthorBook(authorId, bookId)).thenReturn(false);

        ResponseEntity<String> response = authorController.deleteAuthorBook(authorId, bookId);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Failed to delete the book.", response.getBody());
    }
}