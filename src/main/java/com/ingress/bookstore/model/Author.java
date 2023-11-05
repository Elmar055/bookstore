package com.ingress.bookstore.model;

import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "author")
public class Author
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private LocalDate birthdate;

    @OneToMany(mappedBy = "author", cascade = CascadeType.REMOVE)
    private List<Book> booksAuthored;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToMany(mappedBy = "subscribedAuthors",cascade = CascadeType.ALL)
    private List<Student> followers;

    public List<Student> getFollowers()
    {
        return followers;
    }

    public void setFollowers(List<Student> followers)
    {
        this.followers = followers;
    }

    public User getUser()
    {
        return user;
    }

    public void setUser(User user)
    {
        this.user = user;
    }

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

    public LocalDate getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(LocalDate birthdate) {
        this.birthdate = birthdate;
    }

    public List<Book> getBooksAuthored()
    {
        return booksAuthored;
    }

    public void setBooksAuthored(List<Book> booksAuthored)
    {
        this.booksAuthored = booksAuthored;
    }

}
