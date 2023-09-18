package ui;

import model.Event;
import model.EventLog;

public class ConsolePrinter implements LogPrinter {
    public ConsolePrinter() {
        printLog(EventLog.getInstance());
    }

    @Override
    public void printLog(EventLog el) {
        System.out.println("Printing EventLog:\n\n");
        for (Event next : el) {
            System.out.println(next.toString() + "\n\n");
        }
    }
}
