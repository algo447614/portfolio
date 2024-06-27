***ALTERNATIVE TO PYTHON NOTE PROJECT, WHERE IT IS STRICTLY A NOTEBOOK-LIKE OPEN STRUCTURE***

Dynamic Note Taking Application

This project is a Java Swing application named "Menu Application" that provides functionalities to create and manage log entries. The application allows users to enter course information, title, and body content, and log these entries along with timestamps and background colors. Additionally, it includes features for sorting, filtering, and saving logs to a file.
Features
Main Features

    Print Date/Time: Appends the current date and time to the text area.
    Save to Log: Saves the current content of the text area to a log file (log.txt) and logs the event with a timestamp and background color.
    Change Background Color: Allows changing the background color of the text area border through basic color options, a random color option, and a custom color chooser.
    Exit: Closes the application.

Filter and Sort Options

    Sort by Date: Sorts log entries by their timestamps.
    Sort by Title: Sorts log entries by their titles.
    Sort by Color: Sorts log entries by their background colors.
    Filter by Course: Filters log entries based on the course.

Classes and Methods
MenuApplication Class

    Fields:
        textArea: JTextArea for entering course, title, and body content.
        topPanel: JPanel for additional components.
        logPanel: JPanel for displaying log entries.
        borderPanel: JPanel surrounding the text area with a colored border.
        splitPane: JSplitPane separating the log panel and the text area.
        currentBackgroundColor: Color for the text area border.
        courseSections: Map to organize log entries by course.
        PROMPT_TEXT: Constant prompt text template for the text area.
        logEntries: List of LogEntry objects representing the saved logs.

    Constructor: Initializes the frame, sets up the menu bar, creates main panels, and adds action listeners for menu items.

    Methods:
        moveCursorToBodyLine(): Moves the cursor to the body line in the text area.
        printDateTime(): Appends the current date and time to the text area.
        saveToLogFile(): Saves the current content of the text area to a log file and logs the event.
        changeToRandomColor(): Changes the background color of the border panel to a random color.
        changeToBasicColor(String colorName): Changes the background color of the border panel to a basic color.
        chooseCustomColor(): Opens a color chooser dialog to select a custom background color.
        logSaveEvent(String course, String title, String body, Color color, Date timestamp): Logs a save event with details.
        showLogContentDialog(String logContent): Displays the content of a log entry in a dialog.
        sortLogsByDate(): Sorts log entries by date.
        sortLogsByTitle(): Sorts log entries by title.
        sortLogsByColor(): Sorts log entries by color.
        filterLogsByCourse(): Filters log entries by course.
        refreshLogPanel(): Refreshes the log panel to display sorted/filtered log entries.

PromptFilter Class (Inner Class)

    Methods:
        insertString(FilterBypass fb, int offset, String string, AttributeSet attr): Inserts a string into the document if the offset is valid.
        replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs): Replaces a string in the document if the offset is valid.
        remove(FilterBypass fb, int offset, int length): Removes a string from the document if the offset is valid.

LogEntry Class (Inner Class)

    Fields:
        course: Course name.
        title: Log entry title.
        body: Log entry body content.
        color: Background color.
        timestamp: Timestamp of the log entry.
        logEntryPanel: JPanel representing the log entry in the log panel.

    Constructor: Initializes the log entry with the given details.

    Methods:
        getCourse(): Returns the course name.
        getTitle(): Returns the log entry title.
        getColor(): Returns the background color.
        getTimestamp(): Returns the timestamp.
        getLogEntryPanel(): Returns the JPanel representing the log entry.

Usage

    Run the Application:
        Compile the Java file:

        sh

javac mod_3_ct/MenuApplication.java

Run the application:

sh

        java mod_3_ct.MenuApplication

    Use the Menu Options:
        Options:
            Print Date/Time: Append the current date and time to the text area.
            Save to log.txt: Save the text area content to log.txt and log the event.
            Change Background Color: Change the background color of the text area border.
                Random: Set a random background color.
                Basic Colors: Choose from predefined basic colors.
                Custom...: Choose a custom color using a color chooser dialog.
            Exit: Exit the application.
        Filter/Sort:
            Sort by Date: Sort log entries by date.
            Sort by Title: Sort log entries by title.
            Sort by Color: Sort log entries by background color.
            Filter by Course: Filter log entries by course.

Requirements

    Java Development Kit (JDK) 8 or higher

License

This project is licensed under the MIT License.
Author

Ali - Gondicka@gmail.com

Feel free to reach out with any questions or suggestions!
