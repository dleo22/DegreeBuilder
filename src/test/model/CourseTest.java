package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CourseTest {
    Course testCourse;
    Course testCourse2;

    @BeforeEach
    void runBeforeEach() {
        testCourse = new Course("CPSC",210,4);
        testCourse2 = new Course("CPSC",210,3);
    }

    @Test
    void testCourse() {
        assertEquals("CPSC", testCourse.getSubject());
        assertEquals(210, testCourse.getCode());
        assertEquals(4, testCourse.getCredits());
        assertTrue(testCourse.getId() > 0);
        assertEquals(testCourse.hashCode(), testCourse2.hashCode());
        assertEquals("CPSC 210 (4)", testCourse.toString());
    }

    @Test
    void testSetterMethods() {
        testCourse.setSubject("MATHS");
        testCourse.setCode(100);
        testCourse.setCredits(3);
        assertEquals("MATHS", testCourse.getSubject());
        assertEquals(100, testCourse.getCode());
        assertEquals(3, testCourse.getCredits());
    }

    @Test
    void testEquals() {
        Course c1 = testCourse;
        Course c2 = testCourse2;
        Course c3 = new Course("CPSC",110,4);
        Course c4 = new Course("BIOL",210,4);
        Course c5 = new Course("BIOL",112,3);

        assertEquals(c1,c2);
        assertNotEquals(c1,c3);
        assertNotEquals(c1,c4);
        assertNotEquals(c1,c5);
    }

}
