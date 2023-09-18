package persistence;

import model.Degree;
import model.exceptions.EmptyStringException;
import model.exceptions.InvalidFileException;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

public class DegreeReader extends JsonReader {
    public DegreeReader(String source) {
        super(source);
    }

    // EFFECTS reads degrees from file and returns it
    public ArrayList<Degree> read() throws IOException, InvalidFileException {
        String jsonData = readFile(source);
        JSONArray jsonArray = new JSONArray(jsonData);
        return parseDegrees(jsonArray);
    }

    // EFFECTS: parses degrees from JSON object and returns it
    private ArrayList<Degree> parseDegrees(JSONArray jsonArray) throws InvalidFileException {
        ArrayList<Degree> listOfDegrees = new ArrayList<>();

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            Degree d = parseDegree(jsonObject);
            listOfDegrees.add(d);
        }
        return listOfDegrees;
    }

    // EFFECTS: parses degree from JSON object and returns it
    private Degree parseDegree(JSONObject jsonObject) throws InvalidFileException {
        String name = jsonObject.getString("name");
        Degree d = new Degree(name);
        addFaculty(d, jsonObject);
        addDegreeType(d,jsonObject);
        addCourses(d, jsonObject);
        return d;
    }

    // MODIFIES: d
    // EFFECTS: parses faculty from JSON object and adds it to degree
    private void addFaculty(Degree d, JSONObject jsonObject) throws InvalidFileException {
        String faculty = jsonObject.getString("faculty");
        try {
            d.setFaculty(faculty);
        } catch (EmptyStringException e) {
            throw new InvalidFileException();
        }
    }

    // MODIFIES: d
    // EFFECTS: parses degree type from JSON object and adds it to degree
    private void addDegreeType(Degree d, JSONObject jsonObject) throws InvalidFileException {
        String degreeType = jsonObject.getString("type");
        int type = d.getTypeFromString(degreeType);
        d.setDegreeType(type);

    }
}
