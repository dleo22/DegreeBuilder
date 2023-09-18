package ui;


import model.Course;
import model.Degree;
import model.exceptions.DuplicateElementException;
import model.exceptions.InvalidFileException;
import persistence.*;
import ui.tabs.CourseTab;
import ui.tabs.DegreeTab;
import ui.tabs.HomeTab;

import javax.swing.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

// Main
public class MainAppGUI extends JFrame {
    public static final int HOME_TAB_INDEX = 0;
    public static final int DEGREE_TAB_INDEX = 1;
    public static final int COURSE_TAB_INDEX = 2;

    public static final int WIDTH = 600;
    public static final int HEIGHT = 400;
    private final JTabbedPane sidebar;

    private ArrayList<Degree> degrees;
    private ArrayList<Course> courses;

    private static final String DEGREE_JSON = "./data/degree.json";
    private static final String COURSE_JSON = "./data/course.json";
    private DegreeWriter degreeWriter;
    private DegreeReader degreeReader;
    private CourseWriter courseWriter;
    private CourseReader courseReader;


    public static void main(String[] args) {
        new MainAppGUI();
    }

    // MODIFIES: this
    // EFFECTS: creates DegreeBuilder GUI
    private MainAppGUI() {
        super("Degree Builder Console");
        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        degrees = new ArrayList<>();
        courses = new ArrayList<>();
        loadPersistence();

        sidebar = new JTabbedPane();
        sidebar.setTabPlacement(JTabbedPane.LEFT);

        loadTabs();
        add(sidebar);
        setVisible(true);
    }

    // EFFECTS: instantiates writer and reader objects for persistence
    private void loadPersistence() {
        degreeWriter = new DegreeWriter(DEGREE_JSON);
        degreeReader = new DegreeReader(DEGREE_JSON);
        courseWriter = new CourseWriter(COURSE_JSON);
        courseReader = new CourseReader(COURSE_JSON);
    }

    // EFFECTS: writes degrees to file
    public void saveMainApp() throws FileNotFoundException {
        degreeWriter.open();
        degreeWriter.write(degrees);
        degreeWriter.close();

        courseWriter.open();
        courseWriter.write(courses);
        courseWriter.close();
    }

    // MODIFIES: this
    // EFFECTS: loads degrees from file
    public void loadMainApp() throws IOException, InvalidFileException {
        degrees = degreeReader.read();
        courses = courseReader.read();
    }

    // MODIFIES: this
    // EFFECTS: adds home tab, settings tab and report tab to this GUI
    private void loadTabs() {
        JPanel homeTab = new HomeTab(this);
        JPanel degreeTab = new DegreeTab(this);
        JPanel courseTab = new CourseTab(this);

        sidebar.add(homeTab, HOME_TAB_INDEX);
        sidebar.setTitleAt(HOME_TAB_INDEX,"Home");
        sidebar.add(degreeTab, DEGREE_TAB_INDEX);
        sidebar.setTitleAt(DEGREE_TAB_INDEX,"Degree");
        sidebar.add(courseTab, COURSE_TAB_INDEX);
        sidebar.setTitleAt(COURSE_TAB_INDEX,"Course");
    }

    // EFFECTS: returns sidebar of this GUI
    public JTabbedPane getTabbedPane() {
        return sidebar;
    }

    public ArrayList<Degree> getDegrees() {
        return degrees;
    }

    public ArrayList<Course> getCourses() {
        return courses;
    }

    //MODIFIES: this
    // EFFECTS: adds degree to degreesJ
    public void addDegree(Degree d) throws DuplicateElementException {
        if (!degrees.contains(d)) {
            degrees.add(d);
        } else {
            throw new DuplicateElementException();
        }
    }

    //MODIFIES: this
    // EFFECTS: adds course to courses
    public void addCourse(Course c) throws DuplicateElementException {
        if (!courses.contains(c)) {
            courses.add(c);
        } else {
            throw new DuplicateElementException();
        }
    }

    // MODIFIES: this
    // EFFECTS: removes degree from degrees
    public void removeDegree(Degree d) {
        degrees.remove(d);
    }

    //MODIFIES: this
    // EFFECTS: removes course from courses
    public void removeCourse(Course c) {
        courses.remove(c);
    }
}
