package ui.frames;

import model.Course;
import model.Degree;
import ui.MainAppGUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// Represents a JFrame that allows user to select required courses for given degree
public class SelectCoursesFrame extends JFrame {
    private final MainAppGUI controller;
    private final Degree degree;

    // REQUIRES: MainAppGui that controls the main app
    // EFFECTS: creates select courses frame which allows user to select which courses degree requires
    public SelectCoursesFrame(MainAppGUI controller, Degree degree) {
        super("Courses for " + degree.toString());
        this.controller = controller;
        this.degree = degree;
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(300,600);
        setLayout(new FlowLayout());

        add(new JLabel("Select courses required for " + degree.getName()));
        placeCourses();

        setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: allows user to select which courses are required using
    private void placeCourses() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        for (Course c : controller.getCourses()) {
            JCheckBox check = new JCheckBox(c.toString(), degree.getDegreeCourses().contains(c));
            check.addActionListener(new CheckboxListener(c));
            this.add(check);
        }
        this.add(panel);
    }

    //Represents an action listener that listens for actions to checkbox object
    private class CheckboxListener implements ActionListener {
        Course course;

        public CheckboxListener(Course c) {
            course = c;
        }

        // MODIFIES: this
        // EFFECTS: adds/removes course from degree
        @Override
        public void actionPerformed(ActionEvent e) {
            JCheckBox checkBox = (JCheckBox) e.getSource();
            if (checkBox.getModel().isSelected()) {
                degree.addCourse(course);
            } else {
                degree.removeCourse(course);
            }
            System.out.println(degree.getDegreeCourses());
        }
    }
}
