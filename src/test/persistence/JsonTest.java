package persistence;

import model.Course;
import model.Degree;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class JsonTest {
    protected void checkDegree(String name, String faculty, int type, ArrayList<Course> listOfCourses, Degree d) {
        assertEquals(name, d.getName());
        assertEquals(faculty, d.getFaculty());
        assertEquals(type, d.getTypeAsInt());
    }

    protected void checkCourse(String subject, int code, int credits, Course c) {
        assertEquals(subject,c.getSubject());
        assertEquals(code,c.getCode());
        assertEquals(credits,c.getCredits());
    }
}