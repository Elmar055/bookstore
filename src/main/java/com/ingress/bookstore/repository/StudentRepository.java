package com.ingress.bookstore.repository;

import com.ingress.bookstore.model.Book;
import com.ingress.bookstore.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StudentRepository extends JpaRepository<Student,Long>
{
    Student findByName(String name);
    List<Student> findByBooksReadingContains(Book book);
}
