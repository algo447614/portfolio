Student Management System

This project is a Java-based Student Management System, which includes both a console-based and a GUI-based interface for managing student information. The system allows adding, editing, viewing, and exporting student data, as well as calculating the average GPA of the students. The GUI includes a seating chart feature where students' information can be displayed and repositioned.
Features
Console-Based Interface

    View All Students: Displays a list of all students sorted by name.
    Add New Student: Prompts the user to enter details of a new student and adds them to the list.
    Edit Students: Allows editing the details of existing students.
    View Averages: Calculates and displays the average GPA of all students.
    Save Students to File: Saves the student data to a file named students.txt when exiting the application.

GUI-Based Interface

    Refresh Students: Refreshes the list of students displayed in the table.
    Add Student: Prompts the user to enter details of a new student through input dialogs and adds them to the list.
    Edit Student: Prompts the user to enter the student number to edit, and then allows updating the student's details.
    View Averages: Calculates and displays the average GPA of all students in a dialog box.
    Export Students: Exports the list of students to a user-specified file.
    Seating Chart: Opens a window displaying students' information in movable panels, allowing users to create a seating chart. The positions of the panels are saved when the window is closed.

Classes and Methods
Student Class

    Fields: name, address, GPA, photoPath
    Constructors: Student(String name, String address, double GPA, String photoPath)
    Getters and Setters: For all fields
    Comparable Interface: Compares students by their names.
    Overrides: toString(), equals(), hashCode()

StudentManager Class

    Fields: students (List of Student objects)
    Methods:
        addStudent(Student student): Adds a student to the list.
        sortStudentsByName(): Sorts the list of students by name.
        getStudents(): Returns the list of students.
        viewStudents(): Displays the list of students in the console.
        editStudent(int index, Scanner scanner): Edits the details of a student.
        calculateAverageGPA(): Calculates and returns the average GPA.
        saveStudentsToFile(String filename): Saves the list of students to a file.
        showMainMenu(): Displays the main menu and handles user input.

StudentManagerGUI Class

    Fields: studentManager, tableModel, seatingChartFrame
    Methods:
        initializeUI(): Sets up the GUI components.
        refreshStudentList(): Refreshes the table with the list of students.
        addStudent(): Prompts the user to add a new student.
        editStudent(): Prompts the user to edit an existing student's details.
        viewAverages(): Displays the average GPA in a dialog box.
        exportStudents(): Exports the list of students to a file.
        openSeatingChart(): Opens a seating chart window.
        saveSeatingChart(JFrame frame): Saves the positions of the student panels.
        loadSeatingChart(JFrame frame): Loads the positions of the student panels from a file.

StudentPanel Class

    Fields: initialClick, student
    Methods:
        StudentPanel(Student student): Constructor to initialize the panel with student information.
        getStudent(): Returns the student associated with the panel.
        mousePressed(MouseEvent e): Records the initial click position for dragging.
        mouseDragged(MouseEvent e): Handles the dragging of the panel within bounds.

Usage

    Console Application:
        Run the StudentManager class.
        Follow the prompts in the console to view, add, edit, and manage students.

    GUI Application:
        Run the StudentManagerGUI class.
        Use the buttons in the GUI to manage students and create a seating chart.

Requirements

    Java Development Kit (JDK) 8 or higher



File Structure

mod_8/
├── Student.java
├── StudentManager.java
├── StudentManagerGUI.java
├── StudentPanel.java

License

This project is licensed under the MIT License.
Author

Ali - gondicka@gmail.com

Feel free to reach out with any questions or suggestions!
