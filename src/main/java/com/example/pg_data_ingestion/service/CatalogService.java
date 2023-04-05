package com.example.pg_data_ingestion.service;

import com.example.pg_data_ingestion.repo.CatalogRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CatalogService {
    @Autowired
    private CatalogRepo catalogRep;

}
