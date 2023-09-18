package persistence;

import model.Course;
import model.Degree;
import model.exceptions.EmptyStringException;
import model.exceptions.InvalidFileException;
import model.exceptions.OutsideLimitException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class JsonWriterTest extends JsonTest{
    private final ArrayList<Degree> testListOfDegrees = new ArrayList<>();
    private final ArrayList<Course> testListOfCourses = new ArrayList<>();

    @BeforeEach
    void runBeforeEach() {
        try {
            Degree maths = new Degree("Maths");
            maths.setDegreeType(1);
            maths.setFaculty("Science");

            Degree mathsPhd = new Degree("Maths");
            mathsPhd.setDegreeType(3);
            mathsPhd.setFaculty("Science");

            Course math100 = new Course("MATH", 100, 3);
            Course math101 = new Course("MATH", 101, 3);

            maths.addCourse(math100);
            maths.addCourse(math101);

            testListOfCourses.add(math100);
            testListOfCourses.add(math101);

            testListOfDegrees.add(maths);
            testListOfDegrees.add(mathsPhd);
        } catch (EmptyStringException | OutsideLimitException e) {
            fail("Exception should not be thrown");
        }

    }

    @Test
    void testWriterInvalidFile() {
        try {
            JsonWriter degreewriter = new DegreeWriter(("./data/my\0illegal:fileName.json"));
            degreewriter.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // expected
        }
        try {
            JsonWriter courseWriter = new CourseWriter(("./data/my\0illegal:fileName.json"));
            courseWriter.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // expected
        }
    }

    @Test
    void testWriterEmptyDegrees() {
        try {
            ArrayList<Degree> emptyDegrees = new ArrayList<>();
            DegreeWriter writer = new DegreeWriter("./data/testWriterEmptyDegrees.json");
            writer.open();
            writer.write(emptyDegrees);
            writer.close();

            DegreeReader reader = new DegreeReader("./data/testWriterEmptyDegrees.json");
            ArrayList<Degree> testDegrees = reader.read();
            assertEquals(0, testDegrees.size());
        } catch (Exception e) {
            fail("Exception not expected");
        }
    }

    @Test
    void testWriterEmptyCourses() {
        try {
            ArrayList<Course> emptyCourses = new ArrayList<>();
            CourseWriter writer = new CourseWriter("./data/testWriterEmptyCourses.json");
            writer.open();
            writer.write(emptyCourses);
            writer.close();

            CourseReader reader = new CourseReader("./data/testWriterEmptyCourses.json");
            ArrayList<Course> testCourses = reader.read();
            assertEquals(0,testCourses.size());
        } catch (Exception e) {
            fail("exception not expected");
        }
    }

    @Test
    void testWriterGeneralDegrees() {
        try {
            DegreeWriter writer = new DegreeWriter("./data/testWriterGeneralDegrees.json");
            writer.open();
            writer.write(testListOfDegrees);
            writer.close();

            DegreeReader reader = new DegreeReader(("./data/testWriterGeneralDegrees.json"));
            ArrayList<Degree> listOfDegree = reader.read();
            assertEquals(2, listOfDegree.size());
            Degree maths = listOfDegree.get(0);
            Degree mathsPhd = listOfDegree.get(1);

            checkDegree("Maths", "Science", 1, testListOfCourses, maths);
            checkDegree("Maths", "Science", 3, new ArrayList<>(), mathsPhd );

        } catch (IOException e) {
            fail("Could not read from file");
        } catch(InvalidFileException e) {
            fail("Invalid file read");
        }
    }

    @Test
    void testWriterGeneralCourses() {
        try {
            CourseWriter writer = new CourseWriter("./data/testWriterGeneralCourses.json");
            writer.open();
            writer.write(testListOfCourses);
            writer.close();

            CourseReader reader = new CourseReader("./data/testWriterGeneralCourses.json");
            ArrayList<Course> listOfCourse = reader.read();
            assertEquals(2,listOfCourse.size());

            Course math100 = listOfCourse.get(0);
            Course math101 = listOfCourse.get(1);

            checkCourse("MATH", 100, 3, math100);
            checkCourse("MATH", 101, 3, math101);

        } catch (Exception e) {
            fail("exception not expected");
        }
    }
 }
