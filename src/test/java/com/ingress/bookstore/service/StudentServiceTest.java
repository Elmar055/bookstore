package com.ingress.bookstore.service;


import com.ingress.bookstore.model.Book;
import com.ingress.bookstore.model.Student;
import com.ingress.bookstore.repository.StudentRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
public class StudentServiceTest {

    @Mock
    private StudentRepository studentRepository;

    @InjectMocks
    private StudentService studentService;

    @Test
    void testSaveStudent() {
        Student studentToSave = new Student();

        studentService.saveStudent(studentToSave);

        verify(studentRepository, times(1)).save(studentToSave);
    }

    @Test
    void testGetAll() {
        List<Student> students = Arrays.asList(new Student(), new Student(), new Student());

        when(studentRepository.findAll()).thenReturn(students);

        List<Student> result = studentService.getAll();

        assertEquals(students, result);
    }

    @Test
    void testGetBooksReadingByStudent() {
        Long studentId = 1L;
        List<Book> booksReading = Arrays.asList(new Book(), new Book());

        Student student = new Student();
        student.setId(studentId);
        student.setBooksReading(booksReading);

        when(studentRepository.findById(studentId)).thenReturn(Optional.of(student));

        List<Book> result = studentService.getBooksReadingByStudent(studentId);

        assertEquals(booksReading, result);
    }

    @Test
    void testGetStudentById() {
        Long studentId = 1L;
        Student student = new Student();
        student.setId(studentId);

        when(studentRepository.findById(studentId)).thenReturn(Optional.of(student));

        Optional<Student> result = studentService.getStudentById(studentId);

        assertEquals(Optional.of(student), result);
    }

    @Test
    void testUpdateStudent() {
        Student studentToUpdate = new Student();

        studentService.updateStudent(studentToUpdate);

        verify(studentRepository, times(1)).save(studentToUpdate);
    }
}
