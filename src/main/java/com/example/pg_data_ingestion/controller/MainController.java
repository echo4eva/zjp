package com.example.pg_data_ingestion.controller;


import com.example.pg_data_ingestion.model.Book;
import com.example.pg_data_ingestion.repo.CatalogRepo;
import com.example.pg_data_ingestion.service.CatalogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value="/demo")
public class MainController {
    @Autowired
    private CatalogService catalogService;

    // GET
    @GetMapping(value = "/helloworld")
    public String helloWorld() {
        return "Hello World!";
    }

    @GetMapping("/books")
    public List<Book> getAllBooks() {
        return catalogService.getAllBooks();
    }

    @GetMapping("/book/{id}")
    public Book getBook(@PathVariable("id") int id) {
        return catalogService.getBook(id);
    }

    @DeleteMapping("/book/{id}")
    public void deleteBook(@PathVariable("id") int id) {
        catalogService.deleteBook(id);
    }

    @PutMapping("/book")
    public void updateBook(@RequestBody Book book) {
        catalogService.updateBook(book);
    }

}
