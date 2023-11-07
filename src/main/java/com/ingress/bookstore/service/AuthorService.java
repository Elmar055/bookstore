package com.ingress.bookstore.service;

import com.ingress.bookstore.model.Author;
import com.ingress.bookstore.model.Book;
import com.ingress.bookstore.repository.AuthorRepository;
import com.ingress.bookstore.repository.BookRepository;
import com.ingress.bookstore.repository.StudentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class AuthorService
{
    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;

    private final StudentRepository studentRepository;


    public AuthorService(AuthorRepository authorRepository, BookRepository bookRepository, StudentRepository studentRepository)
    {
        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;
        this.studentRepository = studentRepository;
    }

    public Author saveAuthor(Author newAuthor)
    {
        return authorRepository.save(newAuthor);
    }

    public List<Author> getAll()
    {
        return authorRepository.findAll();
    }

    public List<Book> getAuthorBooks(Long authorId) {
        Optional<Author> author = authorRepository.findById(authorId);
        return author.map(Author::getBooksAuthored).orElse(null);
    }

    @Transactional
    public boolean deleteAuthorBook(Long authorId, Long bookId) {
        Optional<Author> optionalAuthor = authorRepository.findById(authorId);
        Optional<Book> optionalBook = bookRepository.findById(bookId);

        if (optionalAuthor.isPresent() && optionalBook.isPresent()) {
            Author author = optionalAuthor.get();
            Book book = optionalBook.get();

            if (book.getAuthor() == author) {
                bookRepository.delete(book);
                System.out.println("Book deleted successfully");
                return true;
            }
        }
        return false;
    }

    public Optional<Author> getAuthorById(Long authorId)
    {
        return authorRepository.findById(authorId);
    }

    public Author updateAuthor(Author author)
    {
        return authorRepository.save(author);
    }


}
