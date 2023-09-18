package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;
import java.util.Objects;

// Represents a course with a subject code (CPSC), a course code (110), amount of credits, and a list of professors
public class Course implements Writable {

    private static int nextCourseId = 1;

    private int id;
    private int code;
    private String subject;
    private int credits;

    // EFFECTS: constructs a course with a subject & course code, and num of credits
    public Course(String subjectCode, int courseCode, int credits) {
        id = nextCourseId++;
        this.subject = subjectCode;
        this.code = courseCode;
        this.credits = credits;
        EventLog.getInstance().logEvent(new Event(this + " added"));
    }

    public void setSubject(String subject) {
        String initsubj = this.subject;
        this.subject = subject;
        EventLog.getInstance().logEvent(new Event(this
                + " changed Subject Code from " + initsubj + " to " + subject));
    }

    public void setCode(int courseCode) {
        int initcode = this.code;
        code = courseCode;
        EventLog.getInstance().logEvent(new Event(this
                + " changed Course Code from " + initcode + " to " + courseCode));
    }

    public void setCredits(int numCredits) {
        int initcred = this.credits;
        credits = numCredits;
        EventLog.getInstance().logEvent(new Event(this
                + " changed Course Credits from " + initcred + " to " + numCredits));
    }

    public int getId() {
        return id;
    }

    public int getCode() {
        return code;
    }

    public String getSubject() {
        return subject;
    }

    public int getCredits() {
        return credits;
    }

    @Override
    public String toString() {
        return subject + " " + code + " (" + credits + ")";
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("subject", subject);
        json.put("code", code);
        json.put("credits", credits);
        return json;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Course course = (Course) o;
        return code == course.code && Objects.equals(subject, course.subject);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code, subject);
    }
}
