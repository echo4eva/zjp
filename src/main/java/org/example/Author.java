package org.example;
import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
public class Author {
    int author_id;
    String author_name;
    String birth_date;

    public Author (int id, String name, String birth_date) {
        this.author_id = id;
        this.author_name = name;
        this.birth_date = birth_date;
    }
    public JSONObject getJSONObj() {
        JSONObject obj = new JSONObject();
        obj.put("author_id", this.author_id);
        obj.put("author_name", this.author_name);
        obj.put("birth_date", this.birth_date);
        return obj;
    }
}
