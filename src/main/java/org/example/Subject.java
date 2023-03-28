package org.example;
import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
public class Subject {
    int subject_id;
    String subject_name;

    public Subject(int subject_id, String subject_name) {
        this.subject_id = subject_id;
        this.subject_name = subject_name;
    }
    public JSONObject getJSONObj() {
        JSONObject obj = new JSONObject();
        obj.put("subject_id", this.subject_id);
        obj.put("subject_name", this.subject_name);
        return obj;
    }
}
