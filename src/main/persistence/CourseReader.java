package persistence;

import model.Course;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

public class CourseReader extends JsonReader {
    public CourseReader(String source) {
        super(source);
    }

    // EFFECTS reads courses from file and returns it
    public ArrayList<Course> read() throws IOException {
        String jsonData = readFile(source);
        JSONArray jsonArray = new JSONArray(jsonData);
        return parseCourses(jsonArray);
    }

    // EFFECTS: parses courses from JSON object and returns it
    private ArrayList<Course> parseCourses(JSONArray jsonArray) {
        ArrayList<Course> listOfCourses = new ArrayList<>();

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            Course c = parseCourse(jsonObject);
            listOfCourses.add(c);
        }
        return listOfCourses;
    }

    // EFFECTS: parses degree from JSON object and returns it
    private Course parseCourse(JSONObject jsonObject) {
        String subject = jsonObject.getString("subject");
        int code = jsonObject.getInt("code");
        int credits = jsonObject.getInt("credits");
        return new Course(subject, code, credits);
    }
}
