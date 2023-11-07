package com.ingress.bookstore.controller;

import com.ingress.bookstore.dto.BookDTO;
import com.ingress.bookstore.model.Author;
import com.ingress.bookstore.model.Book;
import com.ingress.bookstore.model.Student;
import com.ingress.bookstore.service.AuthorService;
import com.ingress.bookstore.service.StudentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

public class StudentControllerTest {

    @Mock
    private StudentService studentService;

    @Mock
    private AuthorService authorService;

    @InjectMocks
    private StudentController studentController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetBooksReadingByStudent() {
        Long studentId = 1L;

        Book book1 = new Book();
        book1.setId(1L);
        book1.setName("Book1");
        book1.setAuthor(new Author());

        Book book2 = new Book();
        book2.setId(2L);
        book2.setName("Book2");
        book2.setAuthor(new Author());

        when(studentService.getBooksReadingByStudent(studentId)).thenReturn(Arrays.asList(book1, book2));

        ResponseEntity<List<BookDTO>> response = studentController.getBooksReadingByStudent(studentId);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(2, response.getBody().size());
        assertEquals("Book1", response.getBody().get(0).getName());
        assertEquals("Book2", response.getBody().get(1).getName());
    }

    @Test
    public void testGetBooksReadingByStudentNoContent() {
        Long studentId = 1L;

        when(studentService.getBooksReadingByStudent(studentId)).thenReturn(Arrays.asList());

        ResponseEntity<List<BookDTO>> response = studentController.getBooksReadingByStudent(studentId);

        assertEquals(204, response.getStatusCodeValue());
        assertNull(response.getBody());
    }

//    @Test
//    public void testSubscribeToAuthorSuccess() {
//        Long studentId = 1L;
//        Long authorId = 2L;
//
//        Author author = new Author();
//        author.setId(authorId);
//
//        Student student = new Student();
//        student.setId(studentId);
//        student.setSubscribedAuthors(new ArrayList<>()); // Boş bir liste oluştur
//
//        when(authorService.getAuthorById(authorId)).thenReturn(Optional.of(author));
//        when(studentService.getStudentById(studentId)).thenReturn(Optional.of(student));
//        when(studentService.updateStudent(any(Student.class))).thenAnswer(invocation -> invocation.getArgument(0)); // Doğrudan gelen öğrenci nesnesini döndür
//        when(authorService.updateAuthor(any(Author.class))).thenAnswer(invocation -> invocation.getArgument(0)); // Doğrudan gelen yazar nesnesini döndür
//
//        ResponseEntity<String> response = studentController.subscribeToAuthor(studentId, authorId);
//
//        assertEquals(200, response.getStatusCodeValue());
//        assertEquals("Subscription success! Student 1 subscribed to Author 2.", response.getBody());
//
//        assertNotNull(student.getSubscribedAuthors());
//        assertEquals(1, student.getSubscribedAuthors().size());
//        assertEquals(author, student.getSubscribedAuthors().get(0));
//
//        assertNotNull(author.getFollowers());
//        assertEquals(1, author.getFollowers().size());
//        assertEquals(student, author.getFollowers().get(0));
//
//        verify(studentService, times(1)).updateStudent(student);
//        verify(authorService, times(1)).updateAuthor(author);
//    }

    @Test
    public void testSubscribeToAuthorAlreadySubscribed() {
        Long studentId = 1L;
        Long authorId = 2L;

        Author author = new Author();
        author.setId(authorId);

        Student student = new Student();
        student.setId(studentId);
        student.setSubscribedAuthors(Arrays.asList(author));

        when(authorService.getAuthorById(authorId)).thenReturn(Optional.of(author));
        when(studentService.getStudentById(studentId)).thenReturn(Optional.of(student));

        ResponseEntity<String> response = studentController.subscribeToAuthor(studentId, authorId);

        assertEquals(400, response.getStatusCodeValue());
        assertEquals("Student 1 is already subscribed to Author 2.", response.getBody());

        verify(studentService, never()).updateStudent(any(Student.class));
        verify(authorService, never()).updateAuthor(any(Author.class));
    }

    @Test
    public void testSubscribeToAuthorNotFound() {
        Long studentId = 1L;
        Long authorId = 2L;

        when(authorService.getAuthorById(authorId)).thenReturn(Optional.empty());
        when(studentService.getStudentById(studentId)).thenReturn(Optional.empty());

        ResponseEntity<String> response = studentController.subscribeToAuthor(studentId, authorId);

        assertEquals(400, response.getStatusCodeValue());
        assertEquals("Subscription unsuccessful. Student 1 or Author 2 not found.", response.getBody());

        verify(studentService, never()).updateStudent(any(Student.class));
        verify(authorService, never()).updateAuthor(any(Author.class));
    }
}