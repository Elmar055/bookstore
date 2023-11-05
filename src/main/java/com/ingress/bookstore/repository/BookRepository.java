package com.ingress.bookstore.repository;

import com.ingress.bookstore.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book,Long>
{
}
