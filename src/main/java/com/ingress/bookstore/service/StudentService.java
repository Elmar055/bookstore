package com.ingress.bookstore.service;

import com.ingress.bookstore.model.Book;
import com.ingress.bookstore.model.Student;
import com.ingress.bookstore.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentService
{
    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository)
    {
        this.studentRepository = studentRepository;
    }

    public Student saveStudent(Student newStudent)
    {
        return studentRepository.save(newStudent);
    }

    public List<Student> getAll()
    {
        return studentRepository.findAll();
    }

    public List<Book> getBooksReadingByStudent(Long studentId)
    {
        Student student = studentRepository.findById(studentId).orElse(null);

        if (student != null) {
            return student.getBooksReading();
        } else {
            return null;
        }
    }

    public Optional<Student> getStudentById(Long studentId)
    {
        return studentRepository.findById(studentId);
    }

    public Student updateStudent(Student student)
    {
        return studentRepository.save(student);
    }
}
