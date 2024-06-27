package mod_3_ct;

import javax.swing.*;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

public class MenuApplication extends JFrame {
    private static final long serialVersionUID = 1L;
    private JTextArea textArea;
    private JPanel topPanel;
    private JPanel logPanel;
    private JPanel borderPanel;
    private JSplitPane splitPane;
    private Color currentBackgroundColor = Color.GRAY; // Initial border color
    private Map<String, JPanel> courseSections;
    private static final String PROMPT_TEXT = "Course:\nTitle:\nBody:\n\n";
    private List<LogEntry> logEntries;

    public MenuApplication() {
        // Setup frame
        setTitle("Menu Application");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Initialize course sections map and log entries list
        courseSections = new HashMap<>();
        logEntries = new ArrayList<>();

        // Create menu bar
        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("Options ▼"); // Added arrow to indicate dropdown menu

        // Create menu items
        JMenuItem menuItem1 = new JMenuItem("Print Date/Time");
        JMenuItem menuItem2 = new JMenuItem("Save to log.txt");
        JMenuItem menuItem4 = new JMenuItem("Exit");

        // Create change color menu
        JMenu changeColorMenu = new JMenu("Change Background Color");
        JMenuItem randomColorItem = new JMenuItem("Random");
        JMenu basicColorsMenu = new JMenu("Basic Colors");
        JMenuItem customColorItem = new JMenuItem("Custom...");

        // Add basic color options
        String[] basicColors = {"Red", "Green", "Blue", "Yellow", "Orange", "Pink"};
        for (String colorName : basicColors) {
            JMenuItem colorItem = new JMenuItem(colorName);
            colorItem.addActionListener(e -> changeToBasicColor(colorName));
            basicColorsMenu.add(colorItem);
        }

        // Add action listeners for color options
        randomColorItem.addActionListener(e -> changeToRandomColor());
        customColorItem.addActionListener(e -> chooseCustomColor());

        // Add color options to change color menu
        changeColorMenu.add(randomColorItem);
        changeColorMenu.add(basicColorsMenu);
        changeColorMenu.add(customColorItem);

        // Add menu items to menu
        menu.add(menuItem1);
        menu.add(menuItem2);
        menu.add(changeColorMenu);
        menu.add(menuItem4);

        // Add menu to menu bar
        menuBar.add(menu);

        // Create filter/sort menu
        JMenu filterSortMenu = new JMenu("Filter/Sort ▼");
        JMenuItem sortByDate = new JMenuItem("Sort by Date");
        JMenuItem sortByTitle = new JMenuItem("Sort by Title");
        JMenuItem sortByColor = new JMenuItem("Sort by Color");
        JMenuItem filterByCourse = new JMenuItem("Filter by Course");

        // Add action listeners for filter/sort options
        sortByDate.addActionListener(e -> sortLogsByDate());
        sortByTitle.addActionListener(e -> sortLogsByTitle());
        sortByColor.addActionListener(e -> sortLogsByColor());
        filterByCourse.addActionListener(e -> filterLogsByCourse());

        filterSortMenu.add(sortByDate);
        filterSortMenu.add(sortByTitle);
        filterSortMenu.add(sortByColor);
        filterSortMenu.add(filterByCourse);

        menuBar.add(filterSortMenu);

        setJMenuBar(menuBar);

        // Create main panel with BorderLayout
        JPanel mainPanel = new JPanel(new BorderLayout());

        // Create top panel for additional components (if needed)
        topPanel = new JPanel();
        topPanel.setPreferredSize(new Dimension(600, 50)); // Adjust the height as needed
        topPanel.setLayout(new BorderLayout());

        // Create text area for the body content
        textArea = new JTextArea(PROMPT_TEXT);
        textArea.setBackground(Color.WHITE);
        ((AbstractDocument) textArea.getDocument()).setDocumentFilter(new PromptFilter());
        moveCursorToBodyLine(); // Move cursor to the correct position

        // Create border panel to surround the text area
        borderPanel = new JPanel(new BorderLayout());
        borderPanel.setBorder(BorderFactory.createLineBorder(currentBackgroundColor, 10)); // 10-pixel border
        borderPanel.add(textArea, BorderLayout.CENTER);

        JScrollPane textScrollPane = new JScrollPane(borderPanel);

        // Create log panel for logging timestamps and background color
        logPanel = new JPanel();
        logPanel.setLayout(new BoxLayout(logPanel, BoxLayout.Y_AXIS));
        JScrollPane logScrollPane = new JScrollPane(logPanel);
        logScrollPane.setPreferredSize(new Dimension(600, 100));

        // Create split pane to separate log panel and text area
        splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, logScrollPane, textScrollPane);
        splitPane.setDividerLocation(0.3); // Set the initial divider location
        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(splitPane, BorderLayout.CENTER);

        // Add main panel to the frame
        add(mainPanel);

        // Add action listeners with lambda expressions
        menuItem1.addActionListener(e -> printDateTime());
        menuItem2.addActionListener(e -> saveToLogFile());
        menuItem4.addActionListener(e -> System.exit(0));
    }

    private void moveCursorToBodyLine() {
        SwingUtilities.invokeLater(() -> {
            try {
                int bodyStart = textArea.getLineStartOffset(4); // Move cursor to the line after "Body:"
                textArea.setCaretPosition(bodyStart);
            } catch (BadLocationException e) {
                e.printStackTrace();
            }
        });
    }

    private void printDateTime() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        textArea.append(formatter.format(date) + "\n");
    }

    private void saveToLogFile() {
        String content = textArea.getText();
        String[] lines = content.split("\n", 5);

        if (lines.length < 5 || lines[0].trim().isEmpty() || lines[1].trim().isEmpty() || lines[2].trim().isEmpty() || lines[4].trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in the course, title, and body content.", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String course = lines[0].replace("Course:", "").trim();
        String title = lines[1].replace("Title:", "").trim();
        String body = lines[4].trim();
        Date timestamp = new Date();

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("log.txt", true))) {
            writer.write("Course: " + course);
            writer.newLine();
            writer.write("Title: " + title);
            writer.newLine();
            writer.write("Body: " + body);
            writer.newLine();
            // User feedback for successful save
            JOptionPane.showMessageDialog(this, "Log saved successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);

            // Log the save event in the log panel
            logSaveEvent(course, title, body, currentBackgroundColor, timestamp);

            // Clear the text area after saving
            textArea.setText(PROMPT_TEXT);
            moveCursorToBodyLine(); // Move cursor to the correct position
        } catch (IOException e) {
            // User feedback for error
            JOptionPane.showMessageDialog(this, "Error saving log: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void changeToRandomColor() {
        Random rand = new Random();
        float hue = rand.nextFloat();
        float saturation = 0.9f;
        float brightness = 0.9f;
        currentBackgroundColor = Color.getHSBColor(hue, saturation, brightness);

        // Change background color of border panel
        borderPanel.setBorder(BorderFactory.createLineBorder(currentBackgroundColor, 10));
    }

    private void changeToBasicColor(String colorName) {
        switch (colorName) {
            case "Red":
                currentBackgroundColor = Color.RED;
                break;
            case "Green":
                currentBackgroundColor = Color.GREEN;
                break;
            case "Blue":
                currentBackgroundColor = Color.BLUE;
                break;
            case "Yellow":
                currentBackgroundColor = Color.YELLOW;
                break;
            case "Orange":
                currentBackgroundColor = Color.ORANGE;
                break;
            case "Pink":
                currentBackgroundColor = Color.PINK;
                break;
        }

        // Change background color of border panel
        borderPanel.setBorder(BorderFactory.createLineBorder(currentBackgroundColor, 10));
    }

    private void chooseCustomColor() {
        currentBackgroundColor = JColorChooser.showDialog(this, "Choose Background Color", currentBackgroundColor);

        if (currentBackgroundColor != null) {
            // Change background color of border panel
            borderPanel.setBorder(BorderFactory.createLineBorder(currentBackgroundColor, 10));
        }
    }

    private void logSaveEvent(String course, String title, String body, Color color, Date timestamp) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String formattedTimestamp = formatter.format(timestamp);

        JPanel logEntryPanel = new JPanel();
        logEntryPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

        JLabel timestampLabel = new JLabel("Saved on: " + formattedTimestamp + " | ");
        JLabel titleLabel = new JLabel("Title: " + title);
        logEntryPanel.add(timestampLabel);
        logEntryPanel.add(titleLabel);

        JPanel colorBox = new JPanel();
        colorBox.setBackground(color);
        colorBox.setPreferredSize(new Dimension(20, 20));
        logEntryPanel.add(colorBox);

        // Add mouse listener for double-click to show log content in a non-editable text window
        logEntryPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    showLogContentDialog(body);
                }
            }
        });

        if (!courseSections.containsKey(course)) {
            JPanel coursePanel = new JPanel();
            coursePanel.setLayout(new BoxLayout(coursePanel, BoxLayout.Y_AXIS));
            coursePanel.setBorder(BorderFactory.createTitledBorder(course));
            logPanel.add(coursePanel);
            courseSections.put(course, coursePanel);
        }

        JPanel coursePanel = courseSections.get(course);
        coursePanel.add(logEntryPanel);
        coursePanel.revalidate();
        coursePanel.repaint();

        logEntries.add(new LogEntry(course, title, body, color, timestamp, logEntryPanel));
    }

    private void showLogContentDialog(String logContent) {
        JDialog dialog = new JDialog(this, "Log Content", true);
        dialog.setSize(400, 300);
        dialog.setLocationRelativeTo(this);

        JTextArea logTextArea = new JTextArea(logContent);
        logTextArea.setEditable(false);

        JScrollPane scrollPane = new JScrollPane(logTextArea);
        dialog.add(scrollPane);

        dialog.setVisible(true);
    }

    private void sortLogsByDate() {
        logEntries.sort(Comparator.comparing(LogEntry::getTimestamp));
        refreshLogPanel();
    }

    private void sortLogsByTitle() {
        logEntries.sort(Comparator.comparing(LogEntry::getTitle));
        refreshLogPanel();
    }

    private void sortLogsByColor() {
        logEntries.sort(Comparator.comparing(LogEntry::getColor, Comparator.comparingInt(Color::getRGB)));
        refreshLogPanel();
    }

    private void filterLogsByCourse() {
        String course = JOptionPane.showInputDialog(this, "Enter course to filter:");
        if (course != null && !course.trim().isEmpty()) {
            logEntries.removeIf(logEntry -> !logEntry.getCourse().equalsIgnoreCase(course.trim()));
            refreshLogPanel();
        }
    }

    private void refreshLogPanel() {
        logPanel.removeAll();
        courseSections.clear();

        for (LogEntry logEntry : logEntries) {
            String course = logEntry.getCourse();
            JPanel logEntryPanel = logEntry.getLogEntryPanel();

            if (!courseSections.containsKey(course)) {
                JPanel coursePanel = new JPanel();
                coursePanel.setLayout(new BoxLayout(coursePanel, BoxLayout.Y_AXIS));
                coursePanel.setBorder(BorderFactory.createTitledBorder(course));
                logPanel.add(coursePanel);
                courseSections.put(course, coursePanel);
            }

            JPanel coursePanel = courseSections.get(course);
            coursePanel.add(logEntryPanel);
        }

        logPanel.revalidate();
        logPanel.repaint();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MenuApplication app = new MenuApplication();
            app.setVisible(true);
        });
    }

    // Inner class to implement the DocumentFilter
    private class PromptFilter extends DocumentFilter {
        @Override
        public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
            if (offset >= PROMPT_TEXT.length()) {
                super.insertString(fb, offset, string, attr);
            }
        }

        @Override
        public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
            if (offset >= PROMPT_TEXT.length()) {
                super.replace(fb, offset, length, text, attrs);
            }
        }

        @Override
        public void remove(FilterBypass fb, int offset, int length) throws BadLocationException {
            if (offset >= PROMPT_TEXT.length()) {
                super.remove(fb, offset, length);
            }
        }
    }

    // Inner class to store log entry data
    private class LogEntry {
        private String course;
        private String title;
        private String body;
        private Color color;
        private Date timestamp;
        private JPanel logEntryPanel;

        public LogEntry(String course, String title, String body, Color color, Date timestamp, JPanel logEntryPanel) {
            this.course = course;
            this.title = title;
            this.body = body;
            this.color = color;
            this.timestamp = timestamp;
            this.logEntryPanel = logEntryPanel;
        }

        public String getCourse() {
            return course;
        }

        public String getTitle() {
            return title;
        }

        public Color getColor() {
            return color;
        }

        public Date getTimestamp() {
            return timestamp;
        }

        public JPanel getLogEntryPanel() {
            return logEntryPanel;
        }
    }
}
