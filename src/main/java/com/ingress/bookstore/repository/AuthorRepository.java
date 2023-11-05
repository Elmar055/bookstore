package com.ingress.bookstore.repository;

import com.ingress.bookstore.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<Author,Long>
{
}
