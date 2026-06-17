import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class TaskInputPanel extends JPanel {
    private CalendarApp app;
    private JTextField taskField;
    private JTextField dateField;
    private JButton addButton;
    private JLabel statusLabel;

    public TaskInputPanel(CalendarApp app) {
        this.app = app;
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createTitledBorder("Add Task"));
        setMaximumSize(new Dimension(250, Integer.MAX_VALUE));
        setPreferredSize(new Dimension(250, 200));
        setBackground(new Color(250, 250, 250));

        // Task label and input
        JLabel taskLabel = new JLabel("Task:");
        taskLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        add(taskLabel);
        add(Box.createVerticalStrut(5));

        taskField = new JTextField();
        taskField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        add(taskField);
        add(Box.createVerticalStrut(15));

        // Date label and input
        JLabel dateLabel = new JLabel("Date (yyyy-MM-dd):");
        dateLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        add(dateLabel);
        add(Box.createVerticalStrut(5));

        dateField = new JTextField();
        dateField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        dateField.setText(LocalDate.now().toString());
        add(dateField);
        add(Box.createVerticalStrut(15));

        // Add button
        addButton = new JButton("Add Task");
        addButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        addButton.setMaximumSize(new Dimension(150, 40));
        addButton.addActionListener(e -> handleAddTask());
        add(addButton);

        // Status label
        statusLabel = new JLabel();
        statusLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        statusLabel.setForeground(new Color(0, 100, 0));
        add(Box.createVerticalStrut(15));
        add(statusLabel);

        add(Box.createVerticalGlue());
    }

    private void handleAddTask() {
        String taskText = taskField.getText().trim();
        String dateText = dateField.getText().trim();

        if (taskText.isEmpty()) {
            statusLabel.setText("Please enter a task.");
            statusLabel.setForeground(new Color(200, 0, 0));
            return;
        }

        try {
            LocalDate date = LocalDate.parse(dateText, DateTimeFormatter.ISO_DATE);
            app.addTask(date, taskText);
            taskField.setText("");
            dateField.setText(LocalDate.now().toString());
            statusLabel.setText("Task added successfully!");
            statusLabel.setForeground(new Color(0, 100, 0));
        } catch (DateTimeParseException ex) {
            statusLabel.setText("Invalid date format.");
            statusLabel.setForeground(new Color(200, 0, 0));
        }
    }
}
