package com.example.pg_data_ingestion.exception;

import org.apache.jena.rdf.model.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1L;
    public ResourceNotFoundException(String message) {
        super(message);
    }

}
