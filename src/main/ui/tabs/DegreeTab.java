package ui.tabs;

import model.Degree;
import ui.ButtonNames;
import ui.MainAppGUI;
import ui.frames.AddDegreeFrame;
import ui.frames.EditDegreeFrame;

import javax.swing.*;
import java.awt.*;

// Represents a JPanel Tab within MainApp controller that allows user to edit/add degrees to database
public class DegreeTab extends Tab {
    private static final String INSTRUCTIONS_TEXT = "Select a degree "
            + "from the database or click 'add degree'";
    private static final String REFRESH_GEN_MESSAGE = "Loaded Degrees";
    private static final String REFRESH_EMPTY_MESSAGE = "No degrees found";

    private final JLabel reportMessage;
    private final JComboBox cb;

    // REQUIRES: MainAppGui controller that holds this tab
    // EFFECTS: creates degree tab with buttons
    public DegreeTab(MainAppGUI controller) {
        super(controller);
        placeRefreshButton();

        JLabel instructions = new JLabel(INSTRUCTIONS_TEXT);
        add(instructions);

        JPanel degreesPanel = new JPanel();
        reportMessage = new JLabel("");
        cb = new JComboBox();
        degreesPanel.setLayout(new FlowLayout());
        degreesPanel.add(reportMessage);
        degreesPanel.add(cb);
        add(degreesPanel);

        placeSelectButton();
        placeAddButton();

    }

    // MODIFIES: this
    // EFFECTS: adds a generate report button that prints degrees as combo box
    private void placeRefreshButton() {
        JButton refreshButton = new JButton(ButtonNames.REFRESH.getValue());

        refreshButton.addActionListener(e -> {
            String buttonPressed = e.getActionCommand();
            if (buttonPressed.equals(ButtonNames.REFRESH.getValue())) {
                if (getController().getDegrees().isEmpty()) {
                    cb.removeAllItems();
                    reportMessage.setText(REFRESH_EMPTY_MESSAGE);
                } else {
                    reportMessage.setText(REFRESH_GEN_MESSAGE);
                    cb.removeAllItems();
                    for (Degree d : getController().getDegrees()) {
                        cb.addItem(d.toString());
                    }
                }
            }
        });
        this.add(refreshButton);
    }

    // MODIFIES: this
    // EFFECTS: adds a select degree button that opens a new pop-up window
    //      for user to edit a degree
    private void placeSelectButton() {
        JButton selectButton = new JButton(ButtonNames.SELECT.getValue());

        selectButton.addActionListener(e -> {
            new EditDegreeFrame(getController(),getController().getDegrees().get(cb.getSelectedIndex()));
        });
        this.add(selectButton);
    }

    // MODIFIES: this
    // EFFECTS: adds an add degree button that opens a new pop-up window
    //      for user to add a degree
    private void placeAddButton() {
        JButton addButton = new JButton(ButtonNames.ADD.getValue());

        addButton.addActionListener(e -> {
            new AddDegreeFrame(getController());
        });
        this.add(addButton);
    }
}
