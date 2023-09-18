package model;

import model.exceptions.DuplicateElementException;
import model.exceptions.EmptyStringException;
import model.exceptions.OutsideLimitException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class DegreeTest {
    Degree testDegree;
    Degree testDegree2;
    Degree testDegree3;

    @BeforeEach
    void runBefore() {
        testDegree = new Degree("a");
        testDegree2 = new Degree("b");
        testDegree3 = new Degree("a");
        setTestDegrees();
    }

    @Test
    void testDegree() {
        assertTrue(testDegree.getId() > 0);
        assertEquals("a", testDegree.getName());
        assertEquals("sci", testDegree.getFaculty());
        assertTrue(testDegree2.getId() > 0);
        assertEquals(0, testDegree.getDegreeCourses().size());
        assertEquals("a (Undergraduate) - sci", testDegree.toString());
        assertEquals(testDegree.hashCode(),testDegree3.hashCode());
    }

    @Test // TO ADD: tests for Exception setter methods
    void testSetterMethods() {
        try {
            testDegree.setName("Computer Science");
            testDegree.setFaculty("Science");
            testDegree.setDegreeType(3);
        } catch (EmptyStringException e) {
            fail("EmptyStringException not expected");
        } catch (OutsideLimitException e) {
            fail("OutsideLimitException not expected");
        }
        assertEquals("Computer Science", testDegree.getName());
        assertEquals("Science", testDegree.getFaculty());
        assertEquals("Doctorate", testDegree.getTypeAsString());
    }

    @Test
    void testSetterMethodThrowOutsideLimitException() {
        try {
            testDegree.setDegreeType(0);
            fail("test expected to fail");
        } catch (OutsideLimitException e) {
            // pass
        }
        try {
            testDegree.setDegreeType(4);
            fail("test expected to fail");
        } catch (OutsideLimitException e) {
            // pass
        }
    }

    @Test
    void testSetterMethodThrowEmptyStringException() {
        try {
            testDegree.setName("");
            fail("name is empty string");
        } catch (EmptyStringException e) {
            // pass
        }
        try {
            testDegree.setFaculty("");
            fail("faculty is empty string");
        } catch (EmptyStringException e) {
            // pass
        }
    }

    @Test
    void testPrintDegreeType() {
        try {
            assertEquals("Undergraduate", testDegree.getTypeAsString());
            testDegree.setDegreeType(2);
            assertEquals("Postgraduate", testDegree.getTypeAsString());
            testDegree.setDegreeType(3);
            assertEquals("Doctorate", testDegree.getTypeAsString());
        } catch (OutsideLimitException e) {
            fail();
        }
    }

    @Test
    void testRemoveCourse() {
        Course c1 = new Course("CPSC",100,4);
        Course c2 = new Course("CPSC",121,4);
        Course c3 = new Course("BIOL",112,3);
        testDegree.addCourse(c1);
        testDegree.addCourse(c2);
        assertEquals(2,testDegree.getDegreeCourses().size());
        testDegree.removeCourse(c1);
        assertEquals(1,testDegree.getDegreeCourses().size());
        assertEquals(c2,testDegree.getDegreeCourses().get(0));
        testDegree.removeCourse(c3);
        assertEquals(1,testDegree.getDegreeCourses().size());
        assertEquals(c2,testDegree.getDegreeCourses().get(0));
    }

    @Test // Tests adding course to empty listOfCourses
    void testAddCourse1() {
        Course testCourse = new Course("CPSC", 110, 4);
        assertTrue(testDegree.addCourse(testCourse));
        ArrayList<Course> courseList = testDegree.getDegreeCourses();
        assertEquals(1, courseList.size());
        assertEquals(testCourse, courseList.get(0));
    }

    @Test // Tests adding multiple non-duplicate courses to listOfCourses
    void testAddCourse2() {
        Course c1 = new Course("CPSC", 110, 4);
        Course c2 = new Course("CPSC", 210, 4);
        Course c3 = new Course("MATH", 101, 3);
        Course c4 = new Course("CHEM", 101, 4);

        assertTrue(testDegree.addCourse(c1));
        assertTrue(testDegree.addCourse(c2));
        assertTrue(testDegree.addCourse(c3));
        assertTrue(testDegree.addCourse(c4));

        ArrayList<Course> courseList = testDegree.getDegreeCourses();
        assertEquals(4, courseList.size());
        assertEquals(c1, courseList.get(0));
        assertEquals(c2, courseList.get(1));
        assertEquals(c3, courseList.get(2));
        assertEquals(c4, courseList.get(3));
    }

    @Test // Tests adding duplicate course to listOfCourses
    void testAddCourse3() {
        Course c1 = new Course("MATH",100,3);
        Course c2 = new Course("CPSC",110,4);

        assertTrue(testDegree.addCourse(c1));
        assertTrue(testDegree.addCourse(c2));
        assertFalse(testDegree.addCourse(c1));
    }

    @Test
    void testCourseEquals() {
        Course c1 = new Course("MATH",100,3);
        Course c2 = new Course("CPSC",110,4);
        Course c3 = new Course("MATH",100,3);
        Course c4 = c1;
        Course c5 = null;
        Degree dummyDegree = new Degree("Computer Science");

        assertNotEquals(c1,c2);
        assertEquals(c1,c3);
        assertEquals(c1,c4);
        assertNotEquals(c1,c5);
        assertNotEquals(c1,dummyDegree);
    }

    @Test
    void testDegreeEquals() {
        int i = 0;
        assertEquals(testDegree, testDegree);
        assertEquals(testDegree,testDegree3);
        assertNotEquals(testDegree,testDegree2);
        assertNotEquals(testDegree, i);
        Degree d4 = new Degree("a");
        Degree d5 = new Degree("a");
        Degree d6 = new Degree("a");
        Degree d7 = new Degree("b");
        Degree d8 = null;
        try {
            d4.setFaculty("sci");
            d4.setDegreeType(3);
            d5.setFaculty("arts");
            d5.setDegreeType(1);
            d6.setFaculty("arts");
            d6.setDegreeType(2);
            d7.setFaculty("sci");
            d7.setDegreeType(1);

        } catch (Exception e) {
            fail("Exception not expected");
        }
        assertNotEquals(testDegree,d4);
        assertNotEquals(testDegree,d5);
        assertNotEquals(testDegree,d6);
        assertNotEquals(testDegree,d7);
        assertNotEquals(testDegree,d8);
    }

    @Test
    void testDuplicateElementException() {
        ArrayList<Degree> degrees = new ArrayList<>();
        try {
            degrees = addDegree(degrees, testDegree);
            degrees = addDegree(degrees, testDegree2);
        } catch (DuplicateElementException e) {
            fail("Exception not expected");
        }
        try {
            degrees = addDegree(degrees,testDegree3);
            fail("expected to fail");
        } catch (DuplicateElementException e) {
            // expected
        }
        assertEquals(2,degrees.size());
        assertEquals(testDegree,degrees.get(0));
        assertEquals(testDegree2,degrees.get(1));
    }

    private ArrayList<Degree> addDegree(ArrayList<Degree> degrees, Degree d) throws DuplicateElementException {
        if (degrees.contains(d)) {
            throw new DuplicateElementException();
        } else {
            degrees.add(d);
        }
        return degrees;
    }

    private void setTestDegrees() {
        try {
            testDegree.setFaculty("sci");
            testDegree.setDegreeType(1);
            testDegree2.setFaculty("art");
            testDegree2.setDegreeType(2);
            testDegree3.setFaculty("sci");
            testDegree3.setDegreeType(1);
        } catch (EmptyStringException | OutsideLimitException e) {
            fail("Test not expected to fail");
        }
    }

}
