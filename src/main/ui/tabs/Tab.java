package ui.tabs;

import ui.MainAppGUI;

import javax.swing.*;
import java.awt.*;

// Abstract class tab which represents a JPanel held within the MainApp
public abstract class Tab extends JPanel {
    private final MainAppGUI controller;

    //REQUIRES: MainAppGUI controller that holds this tab
    // EFFECTS: constructs a JPanel within the main controller which holds this tab
    public Tab(MainAppGUI controller) {
        this.controller = controller;
    }

    // EFFECTS: formats button in JFrame panel
    public JPanel formatButtonRow(JButton b) {
        JPanel p = new JPanel();
        p.setLayout(new FlowLayout());
        p.add(b);

        return p;
    }

    public MainAppGUI getController() {
        return controller;
    }
}
