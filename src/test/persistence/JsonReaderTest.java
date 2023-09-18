package persistence;

import model.Course;
import model.Degree;
import model.exceptions.InvalidFileException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class JsonReaderTest extends JsonTest {
    private final ArrayList<Course> cpscArrayList = new ArrayList<>();
    private final ArrayList<Course> biolArrayList = new ArrayList<>();


    @BeforeEach
    void runBeforeEach() {
        Course cpsc110 = new Course("CPSC", 110, 4);
        Course cpsc210 = new Course("CPSC", 210, 4);
        Course biol112 = new Course("BIOL", 112, 3);

        cpscArrayList.add(cpsc110);
        cpscArrayList.add(cpsc210);
        biolArrayList.add(biol112);
    }

    @Test
    void testReaderNonExistentFile() {
        DegreeReader degreeReader = new DegreeReader("./data/noSuchFile.json");
        CourseReader courseReader = new CourseReader("./data/noSuchFile.json");
        try {
            degreeReader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        } catch (InvalidFileException e) {
            System.out.println("Invalid File Exception");
        }
        try {
            courseReader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // expected
        }
    }

    @Test
    void testReaderEmptyDegrees() {
        DegreeReader reader = new DegreeReader("./data/testReaderEmptyDegrees.json");
        try {
            ArrayList<Degree> listOfDegrees = reader.read();
            assertEquals(0,listOfDegrees.size());
        } catch (IOException e) {
            fail("Couldn't read from file");
        } catch (InvalidFileException e) {
            fail("Invalid file read");
        }
    }

    @Test
    void testReaderGeneralDegrees() {
        DegreeReader reader = new DegreeReader("./data/testReaderGeneralDegrees.json");
        try {
            ArrayList<Degree> listOfDegrees = reader.read();
            Degree cs = listOfDegrees.get(0);
            Degree bio = listOfDegrees.get(1);
            Degree bioGrad = listOfDegrees.get(2);
            assertEquals(3, listOfDegrees.size());
            checkDegree("Computer Science", "Science", 1, cpscArrayList , cs);
            checkDegree("Biology", "Science", 1, biolArrayList, bio);
            checkDegree("Biology", "Science", 2, biolArrayList, bioGrad);
        } catch (IOException e) {
            fail("Couldn't read from file");
        } catch (InvalidFileException e) {
            fail("Invalid file read");
        }
    }

    @Test
    void testReaderEmptyCourses() {
        CourseReader reader = new CourseReader("./data/testReaderEmptyCourses.json");
        try {
            ArrayList<Course> courses = reader.read();
            assertEquals(0,courses.size());
        } catch (Exception e) {
            fail("Exception not expected");
        }
    }

    @Test
    void testReaderGeneralCourses() {
        CourseReader reader = new CourseReader("./data/testReaderGeneralCourses.json");
        try {
            ArrayList<Course> courses = reader.read();
            assertEquals(3,courses.size());

            Course cpsc110 = courses.get(0);
            Course cpsc210 = courses.get(1);
            Course biol112 = courses.get(2);

            checkCourse("CPSC",110,4,cpsc110);
            checkCourse("CPSC",210,4,cpsc210);
            checkCourse("BIOL",112,3,biol112);
        } catch (Exception e) {
            fail("Exception not expected");
        }
    }

    @Test
    void testDegreeReaderThrowEmptyStringException() {
        DegreeReader reader = new DegreeReader("./data/exceptions/testReaderThrowEmptyStringException.json");
        try {
            reader.read();
            fail("InvalidFileException expected");
        } catch (InvalidFileException e) {
            // pass
        } catch (IOException e) {
            fail("Exception not expected");
        }
    }

    @Test
    void testDegreeReaderThrowOutsideLimitException() {
        DegreeReader reader = new DegreeReader("./data/exceptions/testReaderThrowOutsideLimitException.json");
        try {
            reader.read();
            fail("InvalidFileException expected");
        } catch (InvalidFileException e ) {
            // pass
        } catch (IOException e) {
            fail("Exception not expected");
        }
    }
}
