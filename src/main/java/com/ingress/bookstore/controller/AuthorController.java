package com.ingress.bookstore.controller;

import com.ingress.bookstore.dto.BookDTO;
import com.ingress.bookstore.model.Author;
import com.ingress.bookstore.model.Student;
import com.ingress.bookstore.service.AuthorService;
import com.ingress.bookstore.service.BookService;
import com.ingress.bookstore.service.MailService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/author")
public class AuthorController
{
    private final BookService bookService;
    private final MailService mailService;
    private final AuthorService authorService;

    public AuthorController(BookService bookService, MailService mailService, AuthorService authorService) {
        this.bookService = bookService;
        this.mailService = mailService;
        this.authorService = authorService;
    }

    @PostMapping("/{authorId}/addBook")
    public ResponseEntity<String> addBook(@PathVariable Long authorId,@RequestBody BookDTO bookRequest)
    {
        Optional<Author> authorOptional = authorService.getAuthorById(authorId);
        if (authorOptional.isPresent()) {
            Author author = authorOptional.get();
            List<Student> followers = author.getFollowers();

            if (followers == null) {
                followers = new ArrayList<>();
                author.setFollowers(followers);
            }

            mailService.sendMail(followers);
            bookService.addBook(bookRequest);

            return new ResponseEntity<>("Book added successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Author not found with id: " + authorId, HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{authorId}/book/{bookId}")
    public ResponseEntity<String> deleteAuthorBook(@PathVariable Long authorId, @PathVariable Long bookId) {
        boolean deleted = authorService.deleteAuthorBook(authorId, bookId);

        if (deleted) {
            return new ResponseEntity<>("Book deleted successfully.", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Failed to delete the book.", HttpStatus.BAD_REQUEST);
        }
    }

}
