package com.ingress.bookstore.controller;

import com.ingress.bookstore.dto.StudentDTO;
import com.ingress.bookstore.model.Student;
import com.ingress.bookstore.service.BookService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

public class BookControllerTest {

    @Mock
    private BookService bookService;

    @InjectMocks
    private BookController bookController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetReadersForBook() {
        Long bookId = 1L;

        Student student1 = new Student();
        student1.setId(1L);
        student1.setName("Student1");

        Student student2 = new Student();
        student2.setId(2L);
        student2.setName("Student2");

        List<Student> readers = Arrays.asList(student1, student2);

        when(bookService.getReadersForBook(bookId)).thenReturn(readers);

        ResponseEntity<List<StudentDTO>> response = bookController.getReadersForBook(bookId);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(2, response.getBody().size());
        assertEquals("Student1", response.getBody().get(0).getName());
        assertEquals("Student2", response.getBody().get(1).getName());
    }

    @Test
    public void testGetReadersForBookNoContent() {
        Long bookId = 1L;

        when(bookService.getReadersForBook(bookId)).thenReturn(Arrays.asList());

        ResponseEntity<List<StudentDTO>> response = bookController.getReadersForBook(bookId);

        assertEquals(204, response.getStatusCodeValue());

        if (response.getBody() != null) {
            assertEquals(0, response.getBody().size());
        }
    }


}