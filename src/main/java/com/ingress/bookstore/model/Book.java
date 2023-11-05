package com.ingress.bookstore.model;


import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "book")
public class Book
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private Author author;

    @ManyToMany(mappedBy = "booksReading",cascade = CascadeType.ALL)
    private List<Student> studentsReading;

    public List<Student> getStudentsReading() {
        return studentsReading;
    }

    public void setStudentsReading(List<Student> studentsReading) {
        this.studentsReading = studentsReading;
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

    public Author getAuthor()
    {
        return author;
    }

    public void setAuthor(Author author)
    {
        this.author = author;
    }
}
