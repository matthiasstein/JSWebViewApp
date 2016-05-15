package stein.fbg.hsbo.de.jswebviewapp;

import org.json.JSONException;
import org.json.JSONObject;

public class Feature {
    private String title;
    private String id;
    private String name;
    private String type;

    public Feature(String data) {
        try {
            JSONObject json = new JSONObject(data);
            JSONObject attributes = json.getJSONObject("attributes");
            this.id = attributes.getString("OBJECTID");
            this.name = attributes.getString("name");
            this.type = attributes.getString("typ");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        this.title = this.name + " - " + this.type;
    }

    public String getTitle() {
        return title;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    @Override
    public String toString() {
        return "Feature{" +
                "title='" + title + '\'' +
                ", id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
