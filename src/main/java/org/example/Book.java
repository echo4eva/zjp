package org.example;

import org.json.simple.JSONObject;
import org.json.simple.JSONArray;

public class Book {
    int book_id;
    String title;
    String release_date;
    String langauge;
    String copyright_status;
    String publisher;

    public Book (int book_id, String title, String release_date, String language, String copyright_status, String publisher) {
        this.book_id = book_id;
        this.title = title;
        this.release_date = release_date;
        this.langauge = language;
        this.copyright_status = copyright_status;
        this.publisher = publisher;
    }

    public JSONObject getJSONObj() {
        JSONObject obj = new JSONObject();
        obj.put("book_id", this.book_id);
        obj.put("title", this.title);
        obj.put("release_date", this.release_date);
        obj.put("language", this.langauge);
        obj.put("copyright_status", this.copyright_status);
        obj.put("publisher", this.publisher);
        return obj;
    }
}
