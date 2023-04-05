package com.example.pg_data_ingestion.controller;


import com.example.pg_data_ingestion.repo.CatalogRepo;
import com.example.pg_data_ingestion.service.CatalogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value="/book")
public class MainController {
    @Autowired
    private CatalogService catalogService;
}
