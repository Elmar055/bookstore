package com.ingress.bookstore.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "student")
public class Student
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private LocalDate birthDate;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public User getUser()
    {
        return user;
    }

    public void setUser(User user)
    {
        this.user = user;
    }

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "student_subscribed_authors",
            joinColumns = @JoinColumn(name = "student_id"),
            inverseJoinColumns = @JoinColumn(name = "author_id")
    )
    private List<Author> subscribedAuthors;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "student_books",
            joinColumns = @JoinColumn(name = "student_id"),
            inverseJoinColumns = @JoinColumn(name = "book_id"))
    private List<Book> booksReading;

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public List<Book> getBooksReading() {
        return booksReading;
    }

    public void setBooksReading(List<Book> booksReading) {
        this.booksReading = booksReading;
    }

    public void addBookReading(Book book) {
        this.booksReading.add(book);
        book.getStudentsReading().add(this);
    }

    public List<Author> getSubscribedAuthors()
    {
        return subscribedAuthors;
    }

    public void setSubscribedAuthors(List<Author> subscribedAuthors)
    {
        this.subscribedAuthors = subscribedAuthors;
    }
}
