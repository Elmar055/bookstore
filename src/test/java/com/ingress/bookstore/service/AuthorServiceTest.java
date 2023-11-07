package com.ingress.bookstore.service;

import com.ingress.bookstore.model.Author;
import com.ingress.bookstore.model.Book;
import com.ingress.bookstore.repository.AuthorRepository;
import com.ingress.bookstore.repository.BookRepository;
import com.ingress.bookstore.service.AuthorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

class AuthorServiceTest {

    @Mock
    private AuthorRepository authorRepository;

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private AuthorService authorService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testDeleteAuthorBook_Success() {
        Long authorId = 1L;
        Long bookId = 2L;

        Author author = new Author();
        author.setId(authorId);

        Book book = new Book();
        book.setId(bookId);
        book.setAuthor(author);

        when(authorRepository.findById(authorId)).thenReturn(Optional.of(author));
        when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));

        assertTrue(authorService.deleteAuthorBook(authorId, bookId));

        verify(bookRepository, times(1)).delete(book);
    }

    @Test
    void testDeleteAuthorBook_Failure_AuthorNotFound() {
        Long authorId = 1L;
        Long bookId = 2L;

        when(authorRepository.findById(authorId)).thenReturn(Optional.empty());

        assertFalse(authorService.deleteAuthorBook(authorId, bookId));

        verify(bookRepository, never()).delete(any());
    }

    @Test
    void testDeleteAuthorBook_Failure_BookNotFound() {
        Long authorId = 1L;
        Long bookId = 2L;

        Author author = new Author();
        author.setId(authorId);

        when(authorRepository.findById(authorId)).thenReturn(Optional.of(author));
        when(bookRepository.findById(bookId)).thenReturn(Optional.empty());

        assertFalse(authorService.deleteAuthorBook(authorId, bookId));

        verify(bookRepository, never()).delete(any());
    }

    @Test
    void testDeleteAuthorBook_Failure_WrongAuthor() {
        Long authorId = 1L;
        Long bookId = 2L;

        Author author = new Author();
        author.setId(authorId);

        Author anotherAuthor = new Author();
        anotherAuthor.setId(3L);

        Book book = new Book();
        book.setId(bookId);
        book.setAuthor(anotherAuthor);

        when(authorRepository.findById(authorId)).thenReturn(Optional.of(author));
        when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));

        assertFalse(authorService.deleteAuthorBook(authorId, bookId));

        verify(bookRepository, never()).delete(any());
    }
}
