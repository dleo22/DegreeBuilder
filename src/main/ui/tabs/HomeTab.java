package ui.tabs;

import model.exceptions.InvalidFileException;
import ui.ButtonNames;
import ui.ConsolePrinter;
import ui.LogPrinter;
import ui.MainAppGUI;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import static java.lang.System.exit;

// Represents a JPanel Tab within MainApp controller that allows user to load/save data to file
public class HomeTab extends Tab {
    private static final String INIT_GREETING = "Greetings!\t";
    private JLabel greeting;

    // EFFECTS: constructs a home tab for console with buttons and a greeting
    public HomeTab(MainAppGUI controller) {
        super(controller);
        setLayout(new GridLayout(4,1));

        placeGreeting();
        placeHomeButtons();
        placeCourseButton();
        placeExitButton();
    }

    //MODIFIES: this
    // EFFECTS: creates greeting at top of console
    private void placeGreeting() {
        JPanel panel = new JPanel();
        greeting = new JLabel(INIT_GREETING,JLabel.CENTER);
        panel.add(greeting);
        try {
            Image image = ImageIO.read(new File("./data/smiley-face.png"));
            image = image.getScaledInstance(60, 60,  java.awt.Image.SCALE_SMOOTH);
            panel.add(new JLabel(new ImageIcon(image)));
        } catch (IOException e) {
            throw new RuntimeException("Unable to read file");
        }
        panel.setSize(WIDTH,HEIGHT / 3);
        this.add(panel);
    }

    //MODIFIES: this
    // EFFECTS: creates Degree and Course buttons that change greeting message
    private void placeHomeButtons() {
        JButton b1 = new JButton(ButtonNames.LOAD.getValue());
        JButton b2 = new JButton(ButtonNames.SAVE.getValue());

        JPanel buttonRow = formatButtonRow(b1);
        buttonRow.add(b2);
        buttonRow.setSize(WIDTH,HEIGHT / 6);

        b1.addActionListener(e -> {
            loadFromFile();
            greeting.setText("Loaded data from file");
        });

        b2.addActionListener(e -> {
            saveToFile();
            greeting.setText("Saved data to file");
        });

        this.add(buttonRow);
    }

    //MODIFIES: controller
    // EFFECTS: loads data from file, creates pop-up message if exception is thrown
    private void loadFromFile() {
        try {
            getController().loadMainApp();
        } catch (IOException e) {
            infoBox("File not found", "ERROR 404");
        } catch (InvalidFileException e) {
            infoBox("Invalid File Read", "INVALID FILE");
        }
    }

    //MODIFIES: controller
    // EFFECTS: saves data to file, creates pop-up message if exception is thrown
    private void saveToFile() {
        try {
            getController().saveMainApp();
        } catch (FileNotFoundException e) {
            infoBox("File not found", "ERROR 404");
        }
    }

    private static void infoBox(String infoMessage, String titleBar) {
        JOptionPane.showMessageDialog(null, infoMessage, "InfoBox: " + titleBar, JOptionPane.INFORMATION_MESSAGE);
    }

    //MODIFIES: this
    // EFFECTS: constructs a status button that switches to the Course tab on the console
    @SuppressWarnings("methodlength")
    private void placeCourseButton() {
        JPanel statusBlock = new JPanel();
        JButton courseButton = new JButton(ButtonNames.COURSE.getValue());
        JButton degreeButton = new JButton(ButtonNames.DEGREE.getValue());
        statusBlock.add(formatButtonRow(degreeButton));
        statusBlock.add(formatButtonRow(courseButton));

        courseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String buttonPressed = e.getActionCommand();
                if (buttonPressed.equals(ButtonNames.COURSE.getValue())) {
                    getController().getTabbedPane().setSelectedIndex(MainAppGUI.COURSE_TAB_INDEX);
                }
            }
        });
        degreeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String buttonPressed = e.getActionCommand();
                if (buttonPressed.equals(ButtonNames.DEGREE.getValue())) {
                    getController().getTabbedPane().setSelectedIndex(MainAppGUI.DEGREE_TAB_INDEX);
                }
            }
        });
        this.add(statusBlock);
    }

    //MODIFIES: this
    // EFFECTS: creates exit button that exits the program and prints EventLog to console
    private void placeExitButton() {
        JButton btn = new JButton(ButtonNames.EXIT.getValue());
        btn.addActionListener(e -> {
            LogPrinter lp = new ConsolePrinter();
            exit(0);
        });

        this.add(btn);
    }
}
