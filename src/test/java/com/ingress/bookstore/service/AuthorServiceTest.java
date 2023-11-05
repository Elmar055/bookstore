package com.ingress.bookstore.service;

import com.ingress.bookstore.repository.AuthorRepository;
import com.ingress.bookstore.repository.BookRepository;
import com.ingress.bookstore.repository.StudentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

class AuthorServiceTest {

    private AuthorService authorService;
    private AuthorRepository authorRepository;
    private BookRepository bookRepository;
    private StudentRepository studentRepository;

    @BeforeEach
    void setUp() {
        authorRepository = Mockito.mock(AuthorRepository.class);
        bookRepository = Mockito.mock(BookRepository.class);
        studentRepository = Mockito.mock(StudentRepository.class);

        authorService = new AuthorService(authorRepository,bookRepository,studentRepository);
    }


    @Test
    public void whenDeleteAuthorBook()
    {

    }

}