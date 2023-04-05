package com.example.pg_data_ingestion.repo;


import com.example.pg_data_ingestion.model.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SubjectRepo extends JpaRepository<Subject, Integer> {
    @Query("FROM Subject WHERE name = ?1")
    List<Subject> findByName(String name);


}


