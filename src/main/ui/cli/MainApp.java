package ui.cli;

import model.Course;
import model.Degree;
import model.exceptions.EmptyStringException;
import model.exceptions.InvalidFileException;
import model.exceptions.OutsideLimitException;
import persistence.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

// DegreeBuilder application
public class MainApp {
    protected Scanner input;
    private static final String DEGREE_JSON = "./data/degree.json";
    private static final String COURSE_JSON = "./data/course.json";
    private final DegreeWriter degreeWriter;
    private final DegreeReader degreeReader;
    private final CourseWriter courseWriter;
    private final CourseReader courseReader;

    private ArrayList<Degree> listOfDegrees = new ArrayList<Degree>();
    private ArrayList<Course> listOfCourses = new ArrayList<Course>();

    // EFFECTS: runs DegreeBuilder application
    public MainApp() throws FileNotFoundException {
        input = new Scanner(System.in);
        input.useDelimiter("\n");
        degreeWriter = new DegreeWriter(DEGREE_JSON);
        degreeReader = new DegreeReader(DEGREE_JSON);
        courseWriter = new CourseWriter(COURSE_JSON);
        courseReader = new CourseReader(COURSE_JSON);
        runMainApp();
    }

    // MODIFIES: this
    // EFFECTS: Main DegreeBuilder application
    private void runMainApp() {
        boolean isFinished = false;
        String command = null;


        while (!isFinished) {
            displayMenu();
            command = input.next();
            command = command.toLowerCase();

            if (command.equals("q")) {
                System.out.println("Quitting");
                isFinished = true;
            } else {
                processCommand(command);
            }
        }
        System.out.println("\nBye Bye!");
    }

    // MODIFIES: this
    // EFFECTS: Processes user command
    private void processCommand(String command) {
        if (command.equals("d")) {
            System.out.println("Degree Selected");
            new DegreeBuilder();
        } else if (command.equals("c")) {
            System.out.println("Course Selected");
            new CourseBuilder();
        } else if (command.equals("s")) {
            System.out.println("Saving to File");
            saveMainApp();
        } else if (command.equals("l")) {
            System.out.println("Loading from File");
            loadMainApp();
        } else {
            System.out.println("Invalid input");
        }
    }

    // EFFECTS: displays menu options to user
    private void displayMenu() {
        System.out.println("\nSelect from:");
        System.out.println("\td -> degree");
        System.out.println("\tc -> course");
        System.out.println("\ts -> save to file");
        System.out.println("\tl -> load from file");
        System.out.println("\tq -> quit");
    }

    public ArrayList<Degree> getListOfDegrees() {
        return listOfDegrees;
    }

    public ArrayList<Course> getListOfCourses() {
        return listOfCourses;
    }


    // MODIFIES: this
    // EFFECTS: overwrites list of degrees with new list of degrees
    public void setListOfDegrees(ArrayList<Degree> listOfDegrees) {
        this.listOfDegrees = listOfDegrees;
    }

    // MODIFIES: this
    // EFFECTS: overwrites list of courses with new list of courses
    public void setListOfCourses(ArrayList<Course> listOfCourses) {
        this.listOfCourses = listOfCourses;
    }

    public void saveMainApp() {
        try {
            degreeWriter.open();
            degreeWriter.write(listOfDegrees);
            degreeWriter.close();
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + DEGREE_JSON);
        }

        try {
            courseWriter.open();
            courseWriter.write(listOfCourses);
            courseWriter.close();
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + COURSE_JSON);
        }
    }

    public void loadMainApp() {
        try {
            listOfDegrees = degreeReader.read();
            System.out.println("Loaded degrees from " + DEGREE_JSON);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + DEGREE_JSON);
        } catch (InvalidFileException e) {
            System.out.println("Invalid file read from: " + DEGREE_JSON);
        }

        try {
            listOfCourses = courseReader.read();
            System.out.println("Loaded courses from " + COURSE_JSON);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + COURSE_JSON);
        }
    }

    // UI for Degree class
    class DegreeBuilder {
        public DegreeBuilder() {
            processDegree();
        }

        // EFFECTS: processes degree command
        private void processDegree() {
            Boolean isFinished = false;
            displayDegreeMenu();

            while (!isFinished) {
                String command = input.next();
                command = command.toLowerCase();
                switch (command) {
                    case "a":
                        addDegree();
                        isFinished = true;
                        break;
                    case "s":
                        selectDegree();
                        isFinished = true;
                        break;
                    default:
                        System.out.println("ERROR INVALID INPUT");
                        break;
                }
            }
        }

        //MODIFIES: this
        //EFFECTS: adds a degree to listOfDegrees
        private void addDegree() {
            System.out.println("Adding a degree");
            Boolean isFinished = false;
            Degree degree = new Degree("");
            editDegreeName(degree);
            editDegreeFaculty(degree);
            editDegreeType(degree);
            listOfDegrees.add(degree);
            System.out.println("Added " + degree.getTypeAsString()
                    + " degree called " + degree.getName()
                    + " in the faculty of " + degree.getFaculty());
        }

        // EFFECTS: displays list of courses and selects degree for user
        private void selectDegree() {
            System.out.println("Selecting an existing degree");
            displayDegrees();
            processSelectedDegree(listOfDegrees.get(input.nextInt() - 1));
        }

        // EFFECTS: removes, edits degree, or displays courses from list of degrees
        private void processSelectedDegree(Degree degree) {
            System.out.println("Selected " + degree.getName() + " in the Faculty of " + degree.getFaculty());
            displaySelectedDegreeChoices();
            Boolean isFinished = false;
            while (!isFinished) {
                switch (input.nextInt()) {
                    case 1:
                        removeDegree(degree);
                        isFinished = true;
                        break;
                    case 2:
                        editDegree(degree);
                        isFinished = true;
                        break;
                    case 3:
                        displayDegreeCourses(degree);
                        isFinished = true;
                        break;
                    default:
                        System.out.println("INVALID INPUT");
                }
            }
        }

        // REQUIRES: degree is in list of degrees
        // MODIFIES: this
        // EFFECTS: removes inputted degree in list of degrees
        private void removeDegree(Degree degree) {
            listOfDegrees.remove(listOfDegrees.indexOf(degree));
            System.out.println("Removed " + degree.getName() + " from database of degrees");
            System.out.println("New database of degrees:");
            displayDegrees();
        }

        // REQUIRES: degree in listOfDegrees
        // MODIFIES: this
        // EFFECTS: changes properties of degree parameter
        @SuppressWarnings("methodlength")
        private void editDegree(Degree degree) {
            System.out.println("Editing " + degree.getName() + " degree");
            displayEditDegree();
            Boolean isFinished = false;
            while (!isFinished) {
                switch (input.nextInt()) {
                    case 1:
                        editDegreeName(degree);
                        isFinished = true;
                        break;
                    case 2:
                        editDegreeFaculty(degree);
                        isFinished = true;
                        break;
                    case 3:
                        editDegreeType(degree);
                        isFinished = true;
                        break;
                    case 4:
                        changeDegreeCourses(degree);
                        isFinished = true;
                        break;
                    default:
                        System.out.println("INVALID INPUT");
                }
            }
        }

        // EFFECTS: sets degree name
        private void editDegreeName(Degree d) {
            Boolean isFinished = false;
            while (!isFinished) {
                try {
                    System.out.println("Input DEGREE name");
                    d.setName(input.next());
                    isFinished = true;
                } catch (EmptyStringException e) {
                    System.out.println("Cannot input empty string");
                }
            }
            System.out.println("Set DEGREE name to " + d.getName());
        }

        // EFFECTS sets faculty name
        private void editDegreeFaculty(Degree d) {
            Boolean isFinished = false;
            while (!isFinished) {
                try {
                    System.out.println("Input FACULTY name");
                    d.setFaculty(input.next());
                    isFinished = true;
                } catch (EmptyStringException e) {
                    System.out.println("Cannot input empty string");
                }
            }
            System.out.println("Set FACULTY name to " + d.getFaculty());
        }

        // EFFECTS edits degree type
        private void editDegreeType(Degree d) {
            Boolean isFinished = false;
            while (!isFinished) {
                try {
                    System.out.println("Input degree TYPE:");
                    displayDegreeTypes();
                    d.setDegreeType(input.nextInt());
                    isFinished = true;
                } catch (OutsideLimitException e) {
                    System.out.println("Degree TYPE invalid!");
                }
            }
            System.out.println("Set degree TYPE to " + d.getTypeAsString());
        }


        // MODIFIES: this, Degree d
        // EFFECTS: adds course to selected degree's list of courses
        private void changeDegreeCourses(Degree d) {
            int count = 1;
            for (Course c : listOfCourses) {
                System.out.println(count + ". " + c.getSubject() + " " + c.getCode());
                count++;
            }
            int index = input.nextInt();
            Course c = listOfCourses.get(index - 1);
            System.out.println("Adding " + c.getSubject() + " " + c.getCode() + " to " + d.getName());
            d.addCourse(c);
            //System.out.println(d.getDegreeCourses());
        }

        // EFFECTS: displays degree selection menu
        private void displayDegreeMenu() {
            System.out.println("\n Select from:");
            System.out.println("\ta -> add a degree");
            System.out.println("\ts -> select an existing degree");
        }

        // EFFECTS: displays types of degrees
        private void displayDegreeTypes() {
            System.out.println("\n Choose Degree Type:");
            System.out.println("\t1 -> Undergraduate");
            System.out.println("\t2 -> Postgraduate");
            System.out.println("\t3 -> Doctorate");
        }

        // EFFECTS: displays choices for a selected degree
        private void displaySelectedDegreeChoices() {
            System.out.println("\n Select from:");
            System.out.println("\t1 -> remove selected degree");
            System.out.println("\t2 -> edit selected degree");
            System.out.println("\t3 -> view required courses");
        }

        // EFFECTS: displays courses for selected degree
        private void displayDegreeCourses(Degree d) {
            Boolean isFinished = false;
            if (d.getDegreeCourses().size() == 0) {
                System.out.println("No courses listed for selected degree");
            }
            for (Course c : d.getDegreeCourses()) {
                System.out.println(c.getSubject() + " " + c.getCode() + " (Credits: " + c.getCredits() + ")");
            }
            while (!isFinished) {
                System.out.println("Press ENTER to continue");
                Scanner scanner = new Scanner(System.in);
                String readString = scanner.nextLine();
                if (readString.isEmpty()) {
                    isFinished = true;
                }
            }
        }

        // EFFECTS: displays the database of degrees
        private void displayDegrees() {
            if (listOfDegrees.size() == 0) {
                System.out.println("No degrees in database");
            }
            int count = 1;
            for (Degree d : listOfDegrees) {
                displayDegree(d, count);
                count++;
            }
        }

        // EFFECTS: displays the info of Degree type in console
        private void displayDegree(Degree d, int count) {
            String name = d.getName();
            String faculty = d.getFaculty();
            String degreeType = d.getTypeAsString();
            System.out.println(count + ". " + name + " in the Faculty of " + faculty + "(" + degreeType + ")");
        }

        // EFFECTS: displays degree edit menu
        private void displayEditDegree() {
            System.out.println("\n Select from:");
            System.out.println("\t1 -> Change degree name");
            System.out.println("\t2 -> Change degree faculty");
            System.out.println("\t3 -> Change degree type");
            System.out.println("\t4 -> Add course requirements");
        }
    }

    // UI for Course class
    class CourseBuilder {

        public CourseBuilder() {
            processCourse();
        }

        // EFFECTS: processes course command
        @SuppressWarnings("methodlength")
        public void processCourse() {
            Boolean isFinished = false;
            displayCourseMenu();

            while (!isFinished) {
                String command = input.next();
                command = command.toLowerCase();
                switch (command) {
                    case "a":
                        addCourse();
                        isFinished = true;
                        break;
                    case "s":
                        System.out.println("Implementation Incomplete [!!!]");
                        isFinished = true;
                        break;
                    case "v":
                        displayAllCourses();
                        System.out.println("Press ENTER to continue:");
                        Scanner scanner = new Scanner(System.in);
                        String readString = scanner.nextLine();
                        if (readString.isEmpty()) {
                            isFinished = true;
                        }
                        break;
                    default:
                        System.out.println("ERROR INVALID INPUT");
                        break;
                }
            }
        }

        // MODIFIES: this
        // EFFECTS: adds new Course to list of courses
        private void addCourse() {
            System.out.println("Adding a course");
            System.out.println("\n Input Subject Code:");
            String subjectCode = input.next();
            System.out.println("\n Input Course Code:");
            int courseCode = input.nextInt();
            System.out.println("\n Input number of Credits:");
            int credits = input.nextInt();
            Course course = new Course(subjectCode, courseCode, credits);
            ArrayList<Course> currentCourses = MainApp.this.getListOfCourses();
            currentCourses.add(course);
            MainApp.this.setListOfCourses(currentCourses);
            System.out.println("Added " + subjectCode + " " + courseCode
                    + " with credits of " + credits);
        }

        // EFFECTS: displays course selection menu
        private void displayCourseMenu() {
            System.out.println("\n Select from:");
            System.out.println("\ta -> add a course");
            System.out.println("\ts -> select an existing course");
            System.out.println("\tv -> view all courses");
        }

        // EFFECTS: displays all courses in database
        private void displayAllCourses() {
            if (listOfCourses.size() == 0) {
                System.out.println("No courses in database");
            }
            int count = 1;
            for (Course c : listOfCourses) {
                displayCourse(c, count);
                count++;
            }
        }

        // EFFECTS: displays the info of Course c
        private void displayCourse(Course c, int count) {
            String subject = c.getSubject();
            int code = c.getCode();
            int credits = c.getCredits();
            System.out.println(count + ". " + subject + " " + code + " (Credits: " + credits + ")");
        }
    }

}
