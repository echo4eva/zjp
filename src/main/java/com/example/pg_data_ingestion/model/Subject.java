package com.example.pg_data_ingestion.model;

import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;

import java.util.Set;
import java.util.HashSet;
@Entity
public class Subject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="subject_id")
    Long subject_id;
    @Column(name="name")
    String name;

    @ManyToMany(fetch=FetchType.LAZY,
            mappedBy = "subjects")
    Set<Book> subjects = new HashSet<>();

    public Subject() {}
    public Subject(String name) {
        this.name = name;
    }
    public Long getId() {
        return this.subject_id;
    }
    public String getName() {
        return this.name;
    }
}
