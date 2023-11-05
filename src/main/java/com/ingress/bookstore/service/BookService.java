package com.ingress.bookstore.service;

import com.ingress.bookstore.model.Author;
import com.ingress.bookstore.model.Book;
import com.ingress.bookstore.model.Student;
import com.ingress.bookstore.repository.BookRepository;
import com.ingress.bookstore.repository.StudentRepository;
import com.ingress.bookstore.dto.BookDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService
{
    private final BookRepository bookRepository;

    private final StudentRepository studentRepository;

    public BookService(BookRepository bookRepository, StudentRepository studentRepository)
    {
        this.bookRepository = bookRepository;
        this.studentRepository = studentRepository;
    }

    public List<Book> getAll(){
        return bookRepository.findAll();
    }

    public Book addBook(BookDTO bookRequest)
    {
        Book book = new Book();
        book.setName(bookRequest.getName());

        Author author = new Author();
        author.setId(bookRequest.getAuthorId());
        book.setAuthor(author);

        return bookRepository.save(book);
    }

    public List<Student> getReadersForBook(Long bookId)
    {
        Book book = bookRepository.findById(bookId).orElse(null);
        if (book == null) {
            return null;
        }

        return studentRepository.findByBooksReadingContains(book);
    }

    public void addBookReading(Long studentId, Long bookId) {
        Student student = studentRepository.findById(studentId).orElse(null);
        Book book = bookRepository.findById(bookId).orElse(null);

        if (student != null && book != null) {
            student.addBookReading(book);
            studentRepository.save(student);
        }
    }
}
