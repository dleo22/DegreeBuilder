package persistence;

import model.Degree;
import org.json.JSONArray;

import java.util.ArrayList;

public class DegreeWriter extends JsonWriter {

    public DegreeWriter(String destination) {
        super(destination);
    }

    // MODIFIES: this
    // EFFECTS: writes JSON representation of list of degrees to file
    public void write(ArrayList<Degree> degrees) {
        JSONArray jsonArray = new JSONArray();
        for (Degree d : degrees) {
            jsonArray.put(d.toJson());
        }
        saveToFile(jsonArray.toString(TAB));
    }
}
