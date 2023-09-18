package ui.frames;

import model.Degree;
import model.exceptions.DuplicateElementException;
import model.exceptions.EmptyStringException;
import model.exceptions.OutsideLimitException;
import ui.ButtonNames;
import ui.MainAppGUI;

import javax.swing.*;
import java.awt.*;

// Represents a JFrame which allows user to add a degree to database of degrees
public class AddDegreeFrame extends JFrame {
    private final MainAppGUI controller;
    private String name;
    private String faculty;
    private int type;

    // REQUIRES: MainAppGui that controls the main app
    // EFFECTS: creates add degree frame which adds a degree
    public AddDegreeFrame(MainAppGUI controller) {
        super("Add Degree");
        this.controller = controller;
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(new Dimension(400, 300));

        setLayout(new GridLayout(5,1));
        placeNameField();
        placeFacultyField();
        placeTypeField();
        placeAddButton();

        setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: adds a panel where user can input degree's name
    private void placeNameField() {
        name = "";
        JPanel buttonRow = new JPanel();
        JButton btn = new JButton(ButtonNames.SET.getValue());
        JLabel nameLabel = new JLabel("");
        JTextField nameField = new JTextField("Enter name here");
        buttonRow.setLayout(new FlowLayout());

        buttonRow.add(btn);
        buttonRow.add(nameField);
        buttonRow.add(nameLabel);
        buttonRow.setSize(WIDTH,HEIGHT / 6);
        btn.addActionListener(e -> {
            if (!nameField.getText().isEmpty()) {
                name = nameField.getText();
                nameLabel.setText("Name set to: " + nameField.getText());
            } else {
                nameLabel.setText("ERROR! Name set to empty");
            }
        });

        this.add(buttonRow);
    }

    // MODIFIES: this
    // EFFECTS: adds a panel where user can input degree's faculty
    private void placeFacultyField() {
        faculty = "";
        JPanel buttonRow = new JPanel();
        JButton btn = new JButton(ButtonNames.SET.getValue());
        JLabel facultyLabel = new JLabel("");
        JTextField facultyField = new JTextField("Enter Faculty here");
        buttonRow.setLayout(new FlowLayout());

        buttonRow.add(btn);
        buttonRow.add(facultyField);
        buttonRow.add(facultyLabel);
        buttonRow.setSize(WIDTH,HEIGHT / 6);
        btn.addActionListener(e -> {
            faculty = facultyField.getText();
            facultyLabel.setText("Faculty set to: " + facultyField.getText());
        });

        this.add(buttonRow);
    }

    // MODIFIES: this
    // EFFECTS: adds a radio panel where user can select degree's type
    private void placeTypeField() {
        type = 0;
        ButtonGroup group = new ButtonGroup();
        JRadioButton undergradButton = new JRadioButton("Undergraduate");
        JRadioButton pgraduateButton = new JRadioButton("Postgraduate");
        JRadioButton doctorateButton = new JRadioButton("Doctorate");

        undergradButton.addActionListener(e -> type = 1);
        pgraduateButton.addActionListener(e -> type = 2);
        doctorateButton.addActionListener(e -> type = 3);

        group.add(undergradButton);
        group.add(pgraduateButton);
        group.add(doctorateButton);

        JPanel radioPanel = new JPanel();
        radioPanel.add(undergradButton);
        radioPanel.add(pgraduateButton);
        radioPanel.add(doctorateButton);
        radioPanel.setSize(WIDTH,HEIGHT / 6);

        this.add(radioPanel);
    }

    // MODIFIES: this, controller
    // EFFECT: adds an add degree button which checks degree's parameters
    //     before adding it to degrees in MainAppGui controller
    private void placeAddButton() {
        JPanel buttonRow = new JPanel();
        JLabel msg = new JLabel("");
        JButton addButton = new JButton((ButtonNames.ADD.getValue()));
        buttonRow.setLayout(new FlowLayout());

        buttonRow.add(addButton);
        buttonRow.add(msg);
        buttonRow.setSize(WIDTH,HEIGHT / 6);
        addButton.addActionListener(e -> {
            try {
                Degree d = new Degree(name);
                d.setFaculty(faculty);
                d.setDegreeType(type);
                controller.addDegree(d);
                msg.setText(name + " degree added successfully");
            } catch (EmptyStringException exception) {
                msg.setText("Faculty has no name");
            } catch (OutsideLimitException exception) {
                msg.setText("No degree type selected");
            } catch (DuplicateElementException exception) {
                msg.setText("Degree already in database");
            }
        });

        this.add(buttonRow);
    }
}

