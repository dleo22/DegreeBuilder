package ui.cli;

import java.io.FileNotFoundException;

// Reference: Had used TellerApp as inspiration (https://github.students.cs.ubc.ca/CPSC210/TellerApp.git)
//              Also used JsonSerialisationDemo
public class Main {
    public static void main(String[] args) {
        try {
            new MainApp();
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        }
    }
}
