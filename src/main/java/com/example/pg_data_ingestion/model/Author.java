package com.example.pg_data_ingestion.model;

import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;

import java.util.Set;
import java.util.HashSet;

@Entity
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "author_id")
    Long authorId;
    @Column(name = "name")
    String name;
    @Column(name="entry")
    String entry;

    @ManyToMany(fetch=FetchType.LAZY,
            mappedBy = "authors")
    Set<Book> books = new HashSet<Book>();

    public Author() {}
    public Author(String name, String entry)
    {
        this.name = name;
        this.entry = entry;
    }
    public Long getId() {
        return this.authorId;
    }
    public String getName() {
        return this.name;
    }
}
