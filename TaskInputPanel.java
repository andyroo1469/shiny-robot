import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.YearMonth;

public class TaskInputPanel extends JPanel {
    private CalendarApp app;
    private JTextField taskField;
    private JComboBox<Integer> dayCombo;
    private JComboBox<String> monthCombo;
    private JComboBox<Integer> yearCombo;
    private JButton addButton;
    private JLabel statusLabel;

    private static final String[] MONTHS = {
        "January", "February", "March", "April", "May", "June",
        "July", "August", "September", "October", "November", "December"
    };

    public TaskInputPanel(CalendarApp app) {
        this.app = app;
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createTitledBorder("Add Task"));
        setMaximumSize(new Dimension(250, Integer.MAX_VALUE));
        setPreferredSize(new Dimension(250, 350));
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

        // Date label
        JLabel dateLabel = new JLabel("Date:");
        dateLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        add(dateLabel);
        add(Box.createVerticalStrut(5));

        // Day dropdown
        JLabel dayLabel = new JLabel("Day:");
        dayLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        add(dayLabel);
        dayCombo = new JComboBox<>();
        dayCombo.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        populateDays(LocalDate.now().getDayOfMonth(), LocalDate.now().getMonth().getValue(), LocalDate.now().getYear());
        add(dayCombo);
        add(Box.createVerticalStrut(10));

        // Month dropdown
        JLabel monthLabel = new JLabel("Month:");
        monthLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        add(monthLabel);
        monthCombo = new JComboBox<>(MONTHS);
        monthCombo.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        monthCombo.setSelectedIndex(LocalDate.now().getMonth().getValue() - 1);
        monthCombo.addActionListener(e -> updateDays());
        add(monthCombo);
        add(Box.createVerticalStrut(10));

        // Year dropdown
        JLabel yearLabel = new JLabel("Year:");
        yearLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        add(yearLabel);
        yearCombo = new JComboBox<>();
        yearCombo.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        int currentYear = LocalDate.now().getYear();
        for (int i = currentYear - 5; i <= currentYear + 10; i++) {
            yearCombo.addItem(i);
        }
        yearCombo.setSelectedItem(currentYear);
        yearCombo.addActionListener(e -> updateDays());
        add(yearCombo);
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

    private void populateDays(int selectedDay, int month, int year) {
        dayCombo.removeAllItems();
        int daysInMonth = YearMonth.of(year, month).lengthOfMonth();
        for (int i = 1; i <= daysInMonth; i++) {
            dayCombo.addItem(i);
        }
        dayCombo.setSelectedItem(Math.min(selectedDay, daysInMonth));
    }

    private void updateDays() {
        int month = monthCombo.getSelectedIndex() + 1;
        int year = (int) yearCombo.getSelectedItem();
        int currentDay = (int) dayCombo.getSelectedItem();
        populateDays(currentDay, month, year);
    }

    private void handleAddTask() {
        String taskText = taskField.getText().trim();

        if (taskText.isEmpty()) {
            statusLabel.setText("Please enter a task.");
            statusLabel.setForeground(new Color(200, 0, 0));
            return;
        }

        int day = (int) dayCombo.getSelectedItem();
        int month = monthCombo.getSelectedIndex() + 1;
        int year = (int) yearCombo.getSelectedItem();

        try {
            LocalDate date = LocalDate.of(year, month, day);
            app.addTask(date, taskText);
            taskField.setText("");
            statusLabel.setText("Task added successfully!");
            statusLabel.setForeground(new Color(0, 100, 0));
        } catch (Exception ex) {
            statusLabel.setText("Error adding task.");
            statusLabel.setForeground(new Color(200, 0, 0));
        }
    }
}
