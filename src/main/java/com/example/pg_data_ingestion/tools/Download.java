package com.example.pg_data_ingestion.tools;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class Download {
    public static void main(String downloadURL, String bz2Dir) throws IOException {

        int CONNECT_TIMEOUT = 0;
        int READ_TIMEOUT = 0;

        FileUtils.copyURLToFile(
                new URL(downloadURL),
                new File(bz2Dir),
                CONNECT_TIMEOUT,
                READ_TIMEOUT);
    }
}
