package ui.frames;

import model.Course;
import ui.ButtonNames;
import ui.MainAppGUI;

import javax.swing.*;
import java.awt.*;

// Represents a JFrame that allows user to edit selected course
public class EditCourseFrame extends JFrame {
    private final MainAppGUI controller;
    private final Course course;

    // REQUIRES: MainAppGui that controls the main app
    //      && index is within list of courses
    // EFFECTS: creates edit course frame which edits a course
    public EditCourseFrame(MainAppGUI controller, Course course) {
        super(course.toString());
        this.controller = controller;
        this.course = course;
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(new Dimension(400,300));
        setLayout(new FlowLayout());

        add(new JLabel("Editing " + course));
        placeEditSubjField();
        placeEditCodeField();
        placeEditCredField();
        placeRemoveButton();

        setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: allows user to reset course subject code
    private void placeEditSubjField() {
        JPanel panel = new JPanel();
        JButton btn = new JButton(ButtonNames.RESET.getValue());
        JLabel label = new JLabel("");
        JTextField field = new JTextField("Enter new subject code here");
        panel.setLayout(new FlowLayout());

        panel.add(btn);
        panel.add(label);
        panel.add(field);

        btn.addActionListener(e -> {
            course.setSubject(field.getText());
            label.setText("Subject Code changed to " + field.getText());
        });
        this.add(panel);
    }

    // MODIFIES: this
    // EFFECTS: allows user to reset course code
    private void placeEditCodeField() {
        JPanel panel = new JPanel();
        JButton btn = new JButton(ButtonNames.RESET.getValue());
        JLabel label = new JLabel("");
        JTextField field = new JTextField("Enter new course code here");
        panel.setLayout(new FlowLayout());

        panel.add(btn);
        panel.add(label);
        panel.add(field);

        btn.addActionListener(e -> {
            course.setCode(Integer.parseInt(field.getText()));
            label.setText("Course Code changed to " + field.getText());
        });
        this.add(panel);
    }

    // MODIFIES: this
    // EFFECTS: allows user to reset course credits
    private void placeEditCredField() {
        JPanel panel = new JPanel();
        JButton btn = new JButton(ButtonNames.RESET.getValue());
        JLabel label = new JLabel("");
        JTextField field = new JTextField("Enter new course credit here");
        panel.setLayout(new FlowLayout());

        panel.add(btn);
        panel.add(label);
        panel.add(field);

        btn.addActionListener(e -> {
            course.setCredits(Integer.parseInt(field.getText()));
            label.setText("Course Credits changed to " + field.getText());
        });
        this.add(panel);
    }

    // MODIFIES: this
    // EFFECTS: allows user to remove selected course
    private void placeRemoveButton() {
        JPanel panel = new JPanel();
        JLabel label = new JLabel();
        JButton btn = new JButton(ButtonNames.REMOVE.getValue());
        panel.add(label);
        panel.add(btn);
        btn.addActionListener(e -> {
            label.setText("Removed " + course.getSubject() + " " + course.getCode() + " from database");
            controller.removeCourse(course);
        });
        this.add(panel);
    }
}
