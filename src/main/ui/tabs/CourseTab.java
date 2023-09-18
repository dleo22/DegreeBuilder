package ui.tabs;

import model.Course;
import ui.ButtonNames;
import ui.MainAppGUI;
import ui.frames.AddCourseFrame;
import ui.frames.EditCourseFrame;

import javax.swing.*;
import java.awt.*;

// Represents a JPanel Tab within the MainApp controller that allows user to edit/add courses from database
public class CourseTab extends Tab {
    private static final String INSTRUCTIONS_TEXT = "Select a course "
            + "from the database or click ADD";
    private static final String REFRESH_EMPTY_MESSAGE = "No courses found";
    private static final String REFRESH_GEN_MESSAGE = "Loaded Courses";
    private final JLabel msg;
    private final JComboBox cb;


    // REQUIRES: MainAppGui controller that holds this tab
    // EFFECTS: creates report tab with buttons
    public CourseTab(MainAppGUI controller) {
        super(controller);
        placeRefreshButton();

        JLabel instructions = new JLabel(INSTRUCTIONS_TEXT);
        add(instructions);

        JPanel coursesPanel  = new JPanel();
        msg = new JLabel("");
        cb = new JComboBox();
        JButton selectBtn = new JButton(ButtonNames.SELECT.getValue());
        coursesPanel.setLayout(new FlowLayout());

        selectBtn.addActionListener(e -> {
            new EditCourseFrame(getController(), getController().getCourses().get(cb.getSelectedIndex()));
        });

        coursesPanel.add(msg);
        coursesPanel.add(cb);
        coursesPanel.add(selectBtn);
        add(coursesPanel);

        placeAddButton();
    }

    // MODIFIES: this
    // EFFECTS: adds a generate report button that prints courses as combo box
    private void placeRefreshButton() {
        JButton refreshButton = new JButton(ButtonNames.REFRESH.getValue());
        refreshButton.addActionListener(e -> {
            if (getController().getCourses().isEmpty()) {
                cb.removeAllItems();
                msg.setText(REFRESH_EMPTY_MESSAGE);
            } else {
                msg.setText(REFRESH_GEN_MESSAGE);
                cb.removeAllItems();
                for (Course c : getController().getCourses()) {
                    cb.addItem(c.toString());
                }
            }
        });
        this.add(refreshButton);
    }

    // MODIFIES: this
    // EFFECTS: adds an add course button that opens a new pop-up window
    //      for user to add a new course
    private void placeAddButton() {
        JButton addButton = new JButton(ButtonNames.ADD.getValue());
        addButton.addActionListener(e -> {
            new AddCourseFrame(getController());
        });
        this.add(addButton);
    }
}
