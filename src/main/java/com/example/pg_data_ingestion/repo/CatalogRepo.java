package com.example.pg_data_ingestion.repo;
import com.example.pg_data_ingestion.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
public interface CatalogRepo  extends JpaRepository<Book, Integer>{
}
