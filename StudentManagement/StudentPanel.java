package mod_8;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.List;

public class StudentManagerGUI extends JFrame {
    private StudentManager studentManager;
    private DefaultTableModel tableModel;
    private JFrame seatingChartFrame;

    public StudentManagerGUI(StudentManager studentManager) {
        this.studentManager = studentManager;
        initializeUI();
    }

    private void initializeUI() {
        setTitle("Student Manager");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel panel = new JPanel();
        JButton viewButton = new JButton("Refresh Students");
        JButton addButton = new JButton("Add Student");
        JButton editButton = new JButton("Edit Student");
        JButton averageButton = new JButton("View Averages");
        JButton exportButton = new JButton("Export Students");
        JButton seatingChartButton = new JButton("Seating Chart");

        viewButton.addActionListener(e -> refreshStudentList());
        addButton.addActionListener(e -> addStudent());
        editButton.addActionListener(e -> editStudent());
        averageButton.addActionListener(e -> viewAverages());
        exportButton.addActionListener(e -> exportStudents());
        seatingChartButton.addActionListener(e -> openSeatingChart());

        panel.add(viewButton);
        panel.add(addButton);
        panel.add(editButton);
        panel.add(averageButton);
        panel.add(exportButton);
        panel.add(seatingChartButton);

        add(panel, BorderLayout.NORTH);

        // Initialize table model and table
        tableModel = new DefaultTableModel();
        tableModel.addColumn("Name");
        tableModel.addColumn("Address");
        tableModel.addColumn("GPA");
        tableModel.addColumn("Photo");

        JTable table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        refreshStudentList(); // Populate the table with initial data

        // Save the seating chart when the main window is closed
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                if (seatingChartFrame != null && seatingChartFrame.isVisible()) {
                    saveSeatingChart(seatingChartFrame);
                }
            }
        });
    }

    private void refreshStudentList() {
        studentManager.sortStudentsByName();
        List<Student> students = studentManager.getStudents();
        tableModel.setRowCount(0); // Clear existing rows

        for (Student student : students) {
            tableModel.addRow(new Object[]{student.getName(), student.getAddress(), student.getGPA(), student.getPhotoPath()});
        }
    }

    private void addStudent() {
        String name = JOptionPane.showInputDialog(this, "Enter Name:");
        if (name == null || name.trim().isEmpty()) return;

        String address = JOptionPane.showInputDialog(this, "Enter Address:");
        if (address == null || address.trim().isEmpty()) return;

        double gpa = 0;
        while (true) {
            String gpaInput = JOptionPane.showInputDialog(this, "Enter GPA:");
            if (gpaInput == null) return;
            try {
                gpa = Double.parseDouble(gpaInput);
                if (gpa < 0 || gpa > 4) {
                    JOptionPane.showMessageDialog(this, "Please enter a valid GPA between 0 and 4.");
                } else {
                    break;
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Please enter a valid number for GPA.");
            }
        }

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Select Photo");
        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            String photoPath = fileChooser.getSelectedFile().getAbsolutePath();
            studentManager.addStudent(new Student(name, address, gpa, photoPath));
            refreshStudentList(); // Refresh the table with new data
        }
    }

    private void editStudent() {
        String studentNumber = JOptionPane.showInputDialog(this, "Enter student number to edit:");
        int index = Integer.parseInt(studentNumber) - 1;
        Student student = studentManager.getStudents().get(index);

        String name = JOptionPane.showInputDialog(this, "Enter Name:", student.getName());
        if (name == null || name.trim().isEmpty()) return;
        student.setName(name);

        String address = JOptionPane.showInputDialog(this, "Enter Address:", student.getAddress());
        if (address == null || address.trim().isEmpty()) return;
        student.setAddress(address);

        double gpa = 0;
        while (true) {
            String gpaInput = JOptionPane.showInputDialog(this, "Enter GPA:", student.getGPA());
            if (gpaInput == null) return;
            try {
                gpa = Double.parseDouble(gpaInput);
                if (gpa < 0 || gpa > 4) {
                    JOptionPane.showMessageDialog(this, "Please enter a valid GPA between 0 and 4.");
                } else {
                    break;
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Please enter a valid number for GPA.");
            }
        }
        student.setGPA(gpa);

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Select Photo");
        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            String photoPath = fileChooser.getSelectedFile().getAbsolutePath();
            student.setPhotoPath(photoPath);
        }

        refreshStudentList(); // Refresh the table with updated data
    }

    private void viewAverages() {
        double averageGPA = studentManager.calculateAverageGPA();
        JOptionPane.showMessageDialog(this, "Average GPA: " + averageGPA);
    }

    private void exportStudents() {
        JFileChooser fileChooser = new JFileChooser();
        int option = fileChooser.showSaveDialog(this);
        if (option == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                for (Student student : studentManager.getStudents()) {
                    writer.write(student.toString());
                    writer.newLine();
                }
                JOptionPane.showMessageDialog(this, "Students exported successfully.");
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "Error exporting students: " + e.getMessage());
            }
        }
    }

    private void openSeatingChart() {
        if (seatingChartFrame != null && seatingChartFrame.isVisible()) {
            seatingChartFrame.toFront();
            return;
        }

        seatingChartFrame = new JFrame("Seating Chart");
        seatingChartFrame.setSize(800, 600);
        seatingChartFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        seatingChartFrame.setLayout(null); // Use null layout for absolute positioning

        List<Student> students = studentManager.getStudents();
        for (Student student : students) {
            StudentPanel studentPanel = new StudentPanel(student);
            seatingChartFrame.add(studentPanel);
        }

        // Load existing seating chart positions
        loadSeatingChart(seatingChartFrame);

        // Save the positions of the student panels when the seating chart window is closed
        seatingChartFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                saveSeatingChart(seatingChartFrame);
            }
        });

        seatingChartFrame.setVisible(true);
    }

    private void saveSeatingChart(JFrame frame) {
        Component[] components = frame.getContentPane().getComponents();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("seating_chart.txt"))) {
            for (Component component : components) {
                if (component instanceof StudentPanel) {
                    StudentPanel panel = (StudentPanel) component;
                    Student student = panel.getStudent();
                    Point location = panel.getLocation();
                    writer.write(student.getName() + "," + location.x + "," + location.y);
                    writer.newLine();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadSeatingChart(JFrame frame) {
        File file = new File("seating_chart.txt");
        if (file.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split(",");
                    String name = parts[0];
                    int x = Integer.parseInt(parts[1]);
                    int y = Integer.parseInt(parts[2]);

                    for (Component component : frame.getContentPane().getComponents()) {
                        if (component instanceof StudentPanel) {
                            StudentPanel panel = (StudentPanel) component;
                            if (panel.getStudent().getName().equals(name)) {
                                panel.setLocation(x, y);
                                break;
                            }
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        StudentManager studentManager = new StudentManager();
        SwingUtilities.invokeLater(() -> {
            StudentManagerGUI gui = new StudentManagerGUI(studentManager);
            gui.setVisible(true);
        });
    }
}
