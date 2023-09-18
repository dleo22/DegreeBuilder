package persistence;

import model.Course;
import model.Degree;
import model.exceptions.EmptyStringException;
import model.exceptions.InvalidFileException;
import model.exceptions.OutsideLimitException;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.stream.Stream;

// Represents a reader that reads worklist from JSON data stored in file
public class JsonReader {
    protected String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads source file as string and returns it
    protected String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }
        return contentBuilder.toString();
    }

    // MODIFIES: d
    // EFFECTS: parses courses from JSON object and adds them to degree
    protected void addCourses(Degree d, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("courses");
        for (Object json : jsonArray) {
            JSONObject nextCourse = (JSONObject) json;
            addCourse(d, nextCourse);
        }
    }

    // MODIFIES: d
    // EFFECTS: parses course from JSON object and adds it to degree
    protected void addCourse(Degree d, JSONObject jsonObject) {
        String subject = jsonObject.getString("subject");
        int code = jsonObject.getInt("code");
        int credits = jsonObject.getInt("credits");
        Course c = new Course(subject, code, credits);
        d.addCourse(c);
    }
}
