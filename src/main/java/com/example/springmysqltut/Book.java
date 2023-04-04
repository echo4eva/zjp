package com.example.springmysqltut;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import org.json.simple.JSONObject;
import org.json.simple.JSONArray;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Book {
    @Id
    int book_id;
    String title;
    String release_date;
    String language;
    String copyright_status;
    String publisher;
    @ElementCollection
    List<String> subjects;

    public Book() {}
    public Book (int book_id, String title, String release_date, String language, String copyright_status, String publisher, List<String> subjects) {
        this.book_id = book_id;
        this.title = title;
        this.release_date = release_date;
        this.language = language;
        this.copyright_status = copyright_status;
        this.publisher = publisher;
        this.subjects = subjects;
    }

    public JSONObject getJSONObj() {
        JSONObject obj = new JSONObject();
        obj.put("book_id", this.book_id);
        obj.put("title", this.title);
        obj.put("release_date", this.release_date);
        obj.put("language", this.language);
        obj.put("copyright_status", this.copyright_status);
        obj.put("publisher", this.publisher);
        obj.put("subjects", this.subjects);
        return obj;
    }
}
