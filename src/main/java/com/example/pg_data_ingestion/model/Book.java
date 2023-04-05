package com.example.pg_data_ingestion.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;

import java.util.Date;
import java.util.Set;
import java.util.HashSet;

@Entity
public class Book {
    @Id
    @Column(name="book_id")
    Long bookId;

    @Column(name="title")
    String title;
    @Column(name="release_date")
    @JsonFormat(pattern="yyyy-MM-dd")
    Date release_date;

    @Column(name="language")
    String lang;

    @Column(name="copyright_status")
    String copyright_status;

    @ManyToMany(fetch=FetchType.LAZY)
    @JoinTable(
            name="book_author",
            joinColumns = @JoinColumn(name="book_id"),
            inverseJoinColumns = @JoinColumn(name = "author_id")
    )
    Set<Author> authors = new HashSet<Author>();


    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name="subject_book",
            joinColumns = @JoinColumn(name="book_id"),
            inverseJoinColumns = @JoinColumn(name = "subject_id")
    )
    Set<Subject> subjects = new HashSet<Subject>();
    public Book() {

    }
    public Book(Long id, String title, String lang, String copyright_status) {
        this.bookId = id;
        this.title = title;
        this.lang = lang;
        this.copyright_status = copyright_status;
    }
    public void addAuthor(Author author) {
        this.authors.add(author);
    }
    public void addSubject(Subject subject) {
        this.subjects.add(subject);
    }
}
