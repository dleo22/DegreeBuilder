package ui.frames;

import model.Course;
import model.Degree;
import model.exceptions.DuplicateElementException;
import model.exceptions.EmptyStringException;
import model.exceptions.OutsideLimitException;
import ui.ButtonNames;
import ui.MainAppGUI;

import javax.swing.*;
import java.awt.*;

// Represents a JFrame which allows user to add a course to database of courses
public class AddCourseFrame extends JFrame {
    private final MainAppGUI controller;

    private static final String EMPTY_FIELD_MESSAGE = "ERROR - Empty Field";
    private static final String NUMBER_FORMAT_MESSAGE = "ERROR - Field must be an integer";

    private String subject;
    private int code;
    private int credit;

    // REQUIRES: MainAppGui that controls main app
    // EFFECTS: creates add course frame which adds a course
    public AddCourseFrame(MainAppGUI controller) {
        super("Add Course");
        this.controller = controller;
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(new Dimension(400,300));

        setLayout(new GridLayout(4,1));

        placeSubjField();
        placeCodeField();
        placeCredField();
        placeAddButton();

        setVisible(true);
    }

    //MODIFIES: this
    // EFFECTS: allows user to input subject code
    private void placeSubjField() {
        JPanel panel = new JPanel();
        JButton btn = new JButton(ButtonNames.SET.getValue());
        JLabel label = new JLabel();
        JTextField field = new JTextField("Enter Subject Code here");
        panel.setLayout(new FlowLayout());

        panel.add(btn);
        panel.add(label);
        panel.add(field);

        btn.addActionListener(e -> {
            if (field.getText().isEmpty() || field.getText().equals("Enter Subject Code here")) {
                label.setText(EMPTY_FIELD_MESSAGE);
            } else {
                subject = field.getText();
                label.setText("Subject Code set to: " + subject);
            }
        });
        this.add(panel);
    }

    //MODIFIES: this
    // EFFECTS: allows user to input course code
    private void placeCodeField() {
        JPanel panel = new JPanel();
        JButton btn = new JButton(ButtonNames.SET.getValue());
        JLabel label = new JLabel();
        JTextField field = new JTextField("Enter Course Code here");
        panel.setLayout(new FlowLayout());

        panel.add(btn);
        panel.add(label);
        panel.add(field);

        btn.addActionListener(e -> {
            if (field.getText().isEmpty() || field.getText().equals("Enter Course Code here")) {
                label.setText(EMPTY_FIELD_MESSAGE);
            } else {
                try {
                    code = Integer.parseInt(field.getText());
                    label.setText("Course Credits set to: " + code);
                } catch (NumberFormatException exception) {
                    label.setText(NUMBER_FORMAT_MESSAGE);
                }
            }
        });
        this.add(panel);
    }

    // MODIFIES: this
    // EFFECTS: allows user to input course credits
    private void placeCredField() {
        JPanel panel = new JPanel();
        JButton btn = new JButton(ButtonNames.SET.getValue());
        JLabel label = new JLabel();
        JTextField field = new JTextField("Enter Course Credits here");
        panel.setLayout(new FlowLayout());

        panel.add(btn);
        panel.add(label);
        panel.add(field);

        btn.addActionListener(e -> {
            if (field.getText().isEmpty() || field.getText().equals("Enter Course Credits here")) {
                label.setText(EMPTY_FIELD_MESSAGE);
            } else {
                try {
                    credit = Integer.parseInt(field.getText());
                    label.setText("Course Credits set to: " + credit);
                } catch (NumberFormatException exception) {
                    label.setText(NUMBER_FORMAT_MESSAGE);
                }
            }
        });
        this.add(panel);
    }

    // MODIFIES: this, controller
    // EFFECTS: adds course to controller courses if course is valid
    private void placeAddButton() {
        JPanel panel = new JPanel();
        JLabel msg = new JLabel("");
        JButton addButton = new JButton((ButtonNames.ADD.getValue()));

        panel.add(addButton);
        panel.add(msg);
        addButton.addActionListener(e -> {
            try {
                Course c = new Course(subject, code, credit);
                controller.addCourse(c);
                msg.setText(subject + " " + code + " Added Successfully");
            } catch (NullPointerException exception) {
                msg.setText("A field has not been filled in");
            } catch (DuplicateElementException exception) {
                msg.setText("Course already in database");
            }
        });

        this.add(panel);
    }
}
