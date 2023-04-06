package com.example.pg_data_ingestion.service;

import com.example.pg_data_ingestion.model.Book;
import com.example.pg_data_ingestion.repo.CatalogRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CatalogService {
    @Autowired
    private CatalogRepo catalogRep;

    // GET
    public List<Book> getAllBooks() {
        List<Book> allBooks = catalogRep.findAll();
        return allBooks;
    }
    public Book getBook(int id) {
        Book book = catalogRep.findById(id).orElse(null);
        return book;
    }

    public void deleteBook(int id) {
        catalogRep.deleteById(id);
    }

    public void updateBook(Book book) {
        catalogRep.save(book);
    }

}
