package com.example.pg_data_ingestion.service;

import com.example.pg_data_ingestion.exception.ResourceNotFoundException;
import com.example.pg_data_ingestion.model.Book;
import com.example.pg_data_ingestion.repo.CatalogRepo;
import com.example.pg_data_ingestion.repo.SubjectRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.config.ConfigDataResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CatalogService {
    @Autowired
    private CatalogRepo catalogRep;

    @Autowired
    private SubjectRepo subjectRep;

    // GET
    public List<Book> getAllBooks() {
        List<Book> allBooks = catalogRep.findAll();
        return allBooks;
    }
    public Book getBook(int id) {
        Book book = catalogRep.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Book " + id + " not found"));
        return book;
    }

    public void deleteBook(int id) {
        if (catalogRep.existsById(id)) {
            catalogRep.deleteById(id);
        } else {
            throw new ResourceNotFoundException("Book " + id + " not found");
        }
    }

    public void updateBook(Book book) {
        catalogRep.save(book);
    }

    public List<Book> getBooksBySubjectId(Long id) {
        if (subjectRep.existsById(id.intValue())) {
            return catalogRep.findBookBySubjectID(id);
        } else {
            throw new ResourceNotFoundException("Subject " + id + " not found");
        }
    }
}
