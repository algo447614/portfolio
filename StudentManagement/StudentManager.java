package mod_8;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class StudentManager {
    private List<Student> students;

    public StudentManager() {
        students = new LinkedList<>();
    }

    public void addStudent(Student student) {
        students.add(student);
    }

    public void sortStudentsByName() {
        Collections.sort(students);
    }

    public List<Student> getStudents() {
        return students;
    }

    public void viewStudents() {
        if (students.isEmpty()) {
            System.out.println("No students available.");
            return;
        }

        sortStudentsByName();
        for (int i = 0; i < students.size(); i++) {
            Student student = students.get(i);
            System.out.println((i + 1) + ". " + student.getName() + ", GPA: " + student.getGPA());
        }
    }

    public void editStudent(int index, Scanner scanner) {
        if (index < 1 || index > students.size()) {
            System.out.println("Invalid student number.");
            return;
        }

        Student student = students.get(index - 1);

        System.out.print("Enter new name (or press Enter to keep \"" + student.getName() + "\"): ");
        String newName = scanner.nextLine();
        if (!newName.trim().isEmpty()) {
            student.setName(newName);
        }

        System.out.print("Enter new address (or press Enter to keep \"" + student.getAddress() + "\"): ");
        String newAddress = scanner.nextLine();
        if (!newAddress.trim().isEmpty()) {
            student.setAddress(newAddress);
        }

        System.out.print("Enter new GPA (or press Enter to keep \"" + student.getGPA() + "\"): ");
        String newGPAInput = scanner.nextLine();
        if (!newGPAInput.trim().isEmpty()) {
            try {
                double newGPA = Double.parseDouble(newGPAInput);
                if (newGPA >= 0.0 && newGPA <= 4.0) {
                    student.setGPA(newGPA);
                } else {
                    System.out.println("Invalid GPA. Keeping the previous value.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Keeping the previous GPA.");
            }
        }

        System.out.print("Enter new photo path (or press Enter to keep \"" + student.getPhotoPath() + "\"): ");
        String newPhotoPath = scanner.nextLine();
        if (!newPhotoPath.trim().isEmpty()) {
            student.setPhotoPath(newPhotoPath);
        }
    }

    public double calculateAverageGPA() {
        if (students.isEmpty()) {
            return 0.0;
        }

        double totalGPA = 0.0;
        for (Student student : students) {
            totalGPA += student.getGPA();
        }
        return totalGPA / students.size();
    }

    public void saveStudentsToFile(String filename) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (Student student : students) {
                writer.write(student.toString());
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }
    }

    public void showMainMenu() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\nMain Menu:");
            System.out.println("1. View All Students");
            System.out.println("2. Add New Student");
            System.out.println("3. Edit Students");
            System.out.println("4. View Averages");
            System.out.println("5. Exit");

            System.out.print("Choose an option: ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    viewStudents();
                    break;
                case "2":
                    addNewStudent(scanner);
                    break;
                case "3":
                    handleEditStudentsOptions(scanner);
                    break;
                case "4":
                    double averageGPA = calculateAverageGPA();
                    System.out.println("Average GPA: " + averageGPA);
                    break;
                case "5":
                    saveStudentsToFile("students.txt");
                    System.out.println("Student data has been saved to 'students.txt'.");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void handleEditStudentsOptions(Scanner scanner) {
        while (true) {
            System.out.println("\nEdit Options:");
            System.out.println("1. Edit an Existing Student");
            System.out.println("2. Add a New Student");
            System.out.println("3. Main Menu");
            System.out.println("4. Exit");

            System.out.print("Choose an option: ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    System.out.print("Enter the student number to edit: ");
                    try {
                        int studentNumber = Integer.parseInt(scanner.nextLine());
                        editStudent(studentNumber, scanner);
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid number.");
                    }
                    break;
                case "2":
                    addNewStudent(scanner);
                    break;
                case "3":
                    return;
                case "4":
                    saveStudentsToFile("students.txt");
                    System.out.println("Student data has been saved to 'students.txt'.");
                    System.exit(0);
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void addNewStudent(Scanner scanner) {
        System.out.print("Enter student name: ");
        String name = scanner.nextLine();

        System.out.print("Enter student address: ");
        String address = scanner.nextLine();

        double GPA = 0;
        boolean validGPA = false;
        while (!validGPA) {
            System.out.print("Enter student GPA: ");
            String gpaInput = scanner.nextLine();
            try {
                GPA = Double.parseDouble(gpaInput);
                if (GPA < 0.0 || GPA > 4.0) {
                    System.out.println("Please enter a GPA between 0.0 and 4.0.");
                } else {
                    validGPA = true;
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid GPA. Please enter a numeric value.");
            }
        }

        System.out.print("Enter photo path: ");
        String photoPath = scanner.nextLine();

        Student student = new Student(name, address, GPA, photoPath);
        addStudent(student);
        System.out.println("Student added successfully.");
    }

    public static void main(String[] args) {
        StudentManager manager = new StudentManager();
        manager.showMainMenu();
    }
}
