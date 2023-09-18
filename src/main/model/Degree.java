package model;

import model.exceptions.EmptyStringException;
import model.exceptions.InvalidFileException;
import model.exceptions.OutsideLimitException;
import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;
import java.util.Objects;

// Represents a degree with a name, a faculty, a type (Undergrad, Postgrad, Doc), and a list of required courses
public class Degree implements Writable {

    private static int nextDegreeId = 1;
    private int id;
    private String name;
    private String faculty;
    private int type;

    private ArrayList<Course> listOfCourses;

    // REQUIRES: degreeName is not empty
    // EFFECTS: Constructor of Degree type
    public Degree(String degreeName) {
        id = nextDegreeId++;
        name = degreeName;
        faculty = "UNSET";
        type = 0;
        listOfCourses = new ArrayList<Course>();
        EventLog.getInstance().logEvent(new Event(this + " added"));
    }

    // MODIFIES: this
    // EFFECTS: edits degree name
    public void setName(String name) throws EmptyStringException {
        String initName = this.name;
        if (name.isEmpty()) {
            throw new EmptyStringException();
        } else {
            this.name = name;
        }
        EventLog.getInstance().logEvent(new Event("Degree name changed from " + initName + " to " + name));
    }

    // MODIFIES: this
    // EFFECTS: edits faculty name
    public void setFaculty(String faculty) throws EmptyStringException {
        String initFac = this.faculty;
        if (faculty.isEmpty()) {
            throw new EmptyStringException();
        } else {
            this.faculty = faculty;
        }
        EventLog.getInstance().logEvent(new Event("Degree faculty changed from "
                + initFac + " to " + faculty));
    }

    // MODIFIES: this
    // EFFECTS edits degree type
    public void setDegreeType(int type) throws OutsideLimitException {
        String initType = getTypeAsString();
        if ((type < 1) || (type > 3)) {
            throw new OutsideLimitException();
        }
        this.type = type;
        EventLog.getInstance().logEvent(new Event("Degree type changed from "
                + initType + " to " + getTypeAsString()));
    }

    // MODIFIES: this
    // EFFECTS: adds a course to list of courses,
    //              returns true if no duplicate found,
    //              else false.
    public Boolean addCourse(Course course) {
        for (Course c : listOfCourses) {
            if ((course.getCode() == c.getCode()) && (course.getSubject() == c.getSubject())) {
                return false;
            }
        }
        listOfCourses.add(course);
        EventLog.getInstance().logEvent(new Event(course + " added to " + this));
        return true;
    }

    // MODIFIES: this
    // EFFECTS: removes course from list of courses
    public void removeCourse(Course course) {
        listOfCourses.remove(course);
        EventLog.getInstance().logEvent(new Event(course + " removed from " + this));
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getFaculty() {
        return faculty;
    }

    public int getTypeFromString(String degreeType) throws InvalidFileException {
        if (degreeType.equals("Undergraduate")) {
            return 1;
        } else if (degreeType.equals("Postgraduate")) {
            return 2;
        } else if (degreeType.equals("Doctorate")) {
            return 3;
        } else {
            throw new InvalidFileException();
        }
    }

    // EFFECTS: returns string corresponding to degree's type
    public String getTypeAsString() {
        if (type == 1) {
            return "Undergraduate";
        } else if (type == 2) {
            return "Postgraduate";
        } else if (type == 3) {
            return "Doctorate";
        } else {
            return "UNSET";
        }
    }

    // EFFECTS: returns degree type as int
    public int getTypeAsInt() {
        return type;
    }

    public ArrayList<Course> getDegreeCourses() {
        return listOfCourses;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Degree degree = (Degree) o;
        return type == degree.type && Objects.equals(name, degree.name) && Objects.equals(faculty, degree.faculty);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, faculty, type);
    }

    @Override
    public String toString() {
        return name + " (" + getTypeAsString() + ")" + " - " + faculty;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("faculty", faculty);
        json.put("type", getTypeAsString());
        json.put("courses", coursesToJson());
        return json;
    }

    private JSONArray coursesToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Course c : listOfCourses) {
            jsonArray.put(c.toJson());
        }
        return jsonArray;
    }

}

