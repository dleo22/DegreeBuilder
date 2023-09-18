package ui.frames;

import model.Degree;
import model.exceptions.EmptyStringException;
import model.exceptions.OutsideLimitException;
import ui.ButtonNames;
import ui.MainAppGUI;

import javax.swing.*;
import java.awt.*;

// Represents a JFrame that allows user to edit given degree
public class EditDegreeFrame extends JFrame {
    private final MainAppGUI controller;
    private final Degree degree;

    // REQUIRES: MainAppGui that controls the main app
    //      && index is within list of degrees
    // EFFECTS: creates edit degree frame which edits a degree
    public EditDegreeFrame(MainAppGUI controller, Degree degree) {
        super(degree.toString());
        this.controller = controller;
        this.degree = degree;
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(new Dimension(400,300));
        setLayout(new FlowLayout());

        add(new JLabel("Editing " + degree));
        placeEditNameField();
        placeEditFacultyField();
        placeEditTypeField();
        placeEditCoursesField();
        placeRemoveButton();

        setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: allows user to reset degree's name
    private void placeEditNameField() {
        JPanel buttonRow = new JPanel();
        JButton btn = new JButton(ButtonNames.RESET.getValue());
        JLabel label = new JLabel("");
        JTextField field = new JTextField("Enter new name here");
        buttonRow.setLayout(new FlowLayout());

        buttonRow.add(btn);
        buttonRow.add(label);
        buttonRow.add(field);
        buttonRow.setSize(WIDTH,HEIGHT / 6);
        btn.addActionListener(e -> {
            try {
                degree.setName(field.getText());
                label.setText("Degree name set to: " + field.getText());
            } catch (EmptyStringException exception) {
                label.setText("Name cannot be empty");
            }
        });
        this.add(buttonRow);
    }

    // MODIFIES: this
    // EFFECTS: allows user to reset degree's faculty
    private void placeEditFacultyField() {
        JPanel buttonRow = new JPanel();
        JButton btn = new JButton(ButtonNames.RESET.getValue());
        JLabel label = new JLabel("");
        JTextField field = new JTextField("Enter new faculty here");
        buttonRow.setLayout(new FlowLayout());

        buttonRow.add(btn);
        buttonRow.add(label);
        buttonRow.add(field);
        buttonRow.setSize(WIDTH,HEIGHT / 6);
        btn.addActionListener(e -> {
            try {
                degree.setFaculty(field.getText());
                label.setText("Degree faculty set to: " + field.getText());
            } catch (EmptyStringException exception) {
                label.setText("Faculty cannot be empty");
            }
        });
        this.add(buttonRow);
    }

    // MODIFIES: this
    // EFFECTS: allows user to reset degree's type
    @SuppressWarnings("methodlength")
    private void placeEditTypeField() {
        ButtonGroup group = new ButtonGroup();
        JRadioButton undergradButton = new JRadioButton("Undergraduate");
        JRadioButton pgraduateButton = new JRadioButton("Postgraduate");
        JRadioButton doctorateButton = new JRadioButton("Doctorate");
        JLabel label = new JLabel("");

        undergradButton.addActionListener(e -> {
            try {
                degree.setDegreeType(1);
                label.setText("Degree set to " + degree.getTypeAsString());
            } catch (OutsideLimitException ex) {
                throw new RuntimeException(ex);
            }
        });
        pgraduateButton.addActionListener(e -> {
            try {
                degree.setDegreeType(2);
                label.setText("Degree set to " + degree.getTypeAsString());
            } catch (OutsideLimitException ex) {
                throw new RuntimeException(ex);
            }
        });
        doctorateButton.addActionListener(e -> {
            try {
                degree.setDegreeType(3);
                label.setText("Degree set to " + degree.getTypeAsString());
            } catch (OutsideLimitException ex) {
                throw new RuntimeException(ex);
            }
        });

        group.add(undergradButton);
        group.add(pgraduateButton);
        group.add(doctorateButton);

        JPanel radioPanel = new JPanel();
        radioPanel.add(undergradButton);
        radioPanel.add(pgraduateButton);
        radioPanel.add(doctorateButton);
        radioPanel.add(label);
        radioPanel.setSize(WIDTH,HEIGHT / 6);

        this.add(radioPanel);
    }

    // MODIFIES: this
    // EFFECTS: allows user to see all courses in user base and select which apply to selected degree
    private void placeEditCoursesField() {
        JButton btn = new JButton(ButtonNames.VIEW.getValue());
        btn.addActionListener(e -> {
            new SelectCoursesFrame(controller, degree);
        });
        this.add(btn);
    }

    // MODIFIES: this
    // EFFECTS: removes degree from database
    private void placeRemoveButton() {
        JPanel panel = new JPanel();
        JLabel label = new JLabel();
        JButton btn = new JButton(ButtonNames.REMOVE.getValue());
        panel.add(label);
        panel.add(btn);
        btn.addActionListener(e -> {
            label.setText("Removed " + degree.getName() + " (" + degree.getFaculty() + ") from database");
            controller.removeDegree(degree);
        });
        this.add(panel);
    }
}
