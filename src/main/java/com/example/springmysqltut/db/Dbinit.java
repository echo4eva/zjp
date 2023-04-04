package com.example.springmysqltut.db;

import com.example.springmysqltut.pgcatalog.TestingParsingRDFs;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

@Service
public class Dbinit implements CommandLineRunner {
    private BookRepository bookRepository;
    private String directoryPath = "C:\\Users\\peril\\Downloads\\test";

    public Dbinit(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public void run(String[] args) throws Exception {
        TestingParsingRDFs a = new TestingParsingRDFs(directoryPath);
        this.bookRepository.saveAll(a.getBooks());
    }
}
