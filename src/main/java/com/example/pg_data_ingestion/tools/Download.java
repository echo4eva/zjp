package com.example.pg_data_ingestion.tools;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class Download {
    public static void main(String[] args) throws IOException {

        String FILE_URL = "https://www.gutenberg.org/cache/epub/feeds/rdf-files.tar.bz2";
        String FILE_NAME = "C:\\Users\\jerii\\IdeaProjects\\zjp\\src\\main\\resources\\rdf-files.tar.bz2";
        int CONNECT_TIMEOUT = 0;
        int READ_TIMEOUT = 0;

        FileUtils.copyURLToFile(
                new URL(FILE_URL),
                new File(FILE_NAME),
                CONNECT_TIMEOUT,
                READ_TIMEOUT);
    }
}
