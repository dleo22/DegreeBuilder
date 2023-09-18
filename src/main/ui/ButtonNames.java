package ui;

// enumeration of Button Names used throughout GUI
public enum ButtonNames {
    ADD("Add"),
    EXIT("EXIT"),
    LOAD("Load"),
    SAVE("Save"),
    SELECT("SELECT"),
    SET("SET"),
    DEGREE("Degree"),
    COURSE("Course"),
    REFRESH("Refresh"),
    RESET("Reset"),
    REMOVE("Remove"),
    VIEW("View and select courses");

    private final String name;

    // Constructs a ButtonName object with given name
    ButtonNames(String name) {
        this.name = name;
    }

    public String getValue() {
        return name;
    }
}
