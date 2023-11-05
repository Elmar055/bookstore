package com.ingress.bookstore.controller;

import com.ingress.bookstore.dto.BookDTO;
import com.ingress.bookstore.dto.StudentDTO;
import com.ingress.bookstore.model.Author;
import com.ingress.bookstore.model.Book;
import com.ingress.bookstore.model.Student;
import com.ingress.bookstore.service.AuthorService;
import com.ingress.bookstore.service.StudentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/student")
public class StudentController
{
    private final StudentService studentService;
    private final AuthorService authorService;

    public StudentController(StudentService studentService, AuthorService authorService) {
        this.studentService = studentService;
        this.authorService = authorService;
    }

    @GetMapping("/{studentId}/books")
    public ResponseEntity<List<BookDTO>> getBooksReadingByStudent(@PathVariable Long studentId)
    {
        List<BookDTO> booksReading = studentService.getBooksReadingByStudent(studentId)
                .stream()
                .map(book -> mapToDTO(book))
                .collect(Collectors.toList());

        if (booksReading.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(booksReading);
        }
    }

    // student/subscribe/toAuthor?studentId=1&authorId=2
    @PostMapping("/subscribe/toAuthor")
    public ResponseEntity<String> subscribeToAuthor(@RequestParam Long studentId, @RequestParam Long authorId) {
        Optional<Author> authorOptional = authorService.getAuthorById(authorId);
        Optional<Student> studentOptional = studentService.getStudentById(studentId);

        if (authorOptional.isPresent() && studentOptional.isPresent()) {
            Author author = authorOptional.get();
            Student student = studentOptional.get();

            if (!student.getSubscribedAuthors().contains(author)) {
                student.getSubscribedAuthors().add(author);
                author.getFollowers().add(student);
                studentService.updateStudent(student);
                authorService.updateAuthor(author);

                return ResponseEntity.ok("Subscription success! Student " + studentId + " subscribed to Author " + authorId + ".");
            } else {
                return ResponseEntity.badRequest().body("Student " + studentId + " is already subscribed to Author " + authorId + ".");
            }
        } else {
            return ResponseEntity.badRequest().body("Subscription unsuccessful. Student " + studentId + " or Author " + authorId + " not found.");
        }
    }
    private BookDTO mapToDTO(Book book) {
        BookDTO bookDTO = new BookDTO();
        bookDTO.setId(book.getId());
        bookDTO.setName(book.getName());
        bookDTO.setAuthorId(book.getAuthor().getId());
        return bookDTO;
    }
}
