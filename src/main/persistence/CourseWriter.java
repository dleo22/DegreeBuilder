package persistence;

import model.Course;
import org.json.JSONArray;

import java.util.ArrayList;

public class CourseWriter extends JsonWriter {
    public CourseWriter(String destination) {
        super(destination);
    }

    // MODIFIES: this
    // EFFECTS: writes JSON representation of list of courses to file
    public void write(ArrayList<Course> courses) {
        JSONArray jsonArray = new JSONArray();
        for (Course c : courses) {
            jsonArray.put(c.toJson());
        }
        saveToFile(jsonArray.toString(TAB));
    }
}
