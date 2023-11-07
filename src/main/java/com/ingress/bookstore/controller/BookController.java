package com.ingress.bookstore.controller;

import com.ingress.bookstore.dto.StudentDTO;
import com.ingress.bookstore.model.Student;
import com.ingress.bookstore.service.BookService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/book")
public class BookController
{
    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    // get specific book readers
    @GetMapping("/{bookId}/readers")
    public ResponseEntity<List<StudentDTO>> getReadersForBook(@PathVariable Long bookId)
    {
        List<Student> readers = bookService.getReadersForBook(bookId);

        if (readers.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        List<StudentDTO> readerDTOs = readers.stream()
                .map(student -> {
                    StudentDTO dto = new StudentDTO();
                    dto.setId(student.getId());
                    dto.setName(student.getName());

                    return dto;
                })
                .collect(Collectors.toList());

        return ResponseEntity.ok(readerDTOs);
    }


}
