package com.ingress.bookstore.service;

import com.ingress.bookstore.model.Author;
import com.ingress.bookstore.model.Book;
import com.ingress.bookstore.model.Student;
import com.ingress.bookstore.repository.BookRepository;
import com.ingress.bookstore.repository.StudentRepository;
import com.ingress.bookstore.dto.BookDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @Mock
    private StudentRepository studentRepository;

    @InjectMocks
    private BookService bookService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        studentRepository = mock(StudentRepository.class);
        bookRepository = mock(BookRepository.class);
        bookService = new BookService(bookRepository, studentRepository);
    }

    @Test
    void testGetAllBooks() {
        List<Book> books = new ArrayList<>();
        books.add(new Book());
        books.add(new Book());

        when(bookRepository.findAll()).thenReturn(books);

        List<Book> result = bookService.getAll();

        assertEquals(2, result.size());
        verify(bookRepository, times(1)).findAll();
    }

    @Test
    void testAddBook() {
        BookDTO bookRequest = new BookDTO();
        bookRequest.setName("Test Book");
        bookRequest.setAuthorId(1L);

        when(bookRepository.save(any(Book.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Book result = bookService.addBook(bookRequest);

        assertEquals(bookRequest.getName(), result.getName());
        assertEquals(bookRequest.getAuthorId(), result.getAuthor().getId());
        verify(bookRepository, times(1)).save(any(Book.class));
    }

    @Test
    void testGetReadersForBook() {
        Long bookId = 1L;

        Book book = new Book();
        book.setId(bookId);

        List<Student> readers = new ArrayList<>();
        readers.add(new Student());
        readers.add(new Student());

        when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));
        when(studentRepository.findByBooksReadingContains(book)).thenReturn(readers);

        List<Student> result = bookService.getReadersForBook(bookId);

        assertEquals(2, result.size());
        verify(bookRepository, times(1)).findById(bookId);
        verify(studentRepository, times(1)).findByBooksReadingContains(book);
    }

//    @Test
//    void testAddBookReading()
//    {
//        Long studentId = 1L;
//        Long bookId = 2L;
//
//        Student student = new Student();
//        student.setId(studentId);
//
//        Book book = new Book();
//        book.setId(bookId);
//
//        when(studentRepository.findById(studentId)).thenReturn(Optional.of(student));
//        when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));
//        when(studentRepository.save(any(Student.class))).thenAnswer(invocation -> invocation.getArgument(0));
//
//        bookService.addBookReading(studentId, bookId);
//
//        verify(studentRepository, times(1)).save(any(Student.class));
//
//        assertEquals(1, student.getBooksReading().size());
//        assertEquals(book, student.getBooksReading().get(0));
//    }

}
