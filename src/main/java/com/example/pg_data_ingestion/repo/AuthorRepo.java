package com.example.pg_data_ingestion.repo;

import com.example.pg_data_ingestion.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AuthorRepo extends JpaRepository<Author, Integer> {
    @Query("FROM Author WHERE name = ?1")
    List<Author> findByName(String name);



}
