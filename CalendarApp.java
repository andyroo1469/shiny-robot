import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.*;

public class CalendarApp extends JFrame {
    private YearMonth currentMonth;
    private Map<LocalDate, java.util.List<String>> tasks;
    private CalendarPanel calendarPanel;
    private TaskInputPanel taskInputPanel;
    private JLabel monthLabel;

    public CalendarApp() {
        setTitle("Calendar App");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 700);
        setLocationRelativeTo(null);
        setResizable(true);

        currentMonth = YearMonth.now();
        tasks = new HashMap<>();

        // Create layout
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Center: Calendar panel (must be created before taskInputPanel for proper initialization)
        calendarPanel = new CalendarPanel(this);

        // Top left: Task input panel
        taskInputPanel = new TaskInputPanel(this);
        mainPanel.add(taskInputPanel, BorderLayout.WEST);

        mainPanel.add(calendarPanel, BorderLayout.CENTER);

        // Top: Month navigation
        JPanel navPanel = createNavigationPanel();
        mainPanel.add(navPanel, BorderLayout.NORTH);

        add(mainPanel);
        setVisible(true);
    }

    private JPanel createNavigationPanel() {
        JPanel navPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        navPanel.setBackground(new Color(240, 240, 240));

        JButton prevButton = new JButton("< Previous");
        prevButton.addActionListener(e -> previousMonth());

        monthLabel = new JLabel();
        updateMonthLabel();

        JButton nextButton = new JButton("Next >");
        nextButton.addActionListener(e -> nextMonth());

        navPanel.add(prevButton);
        navPanel.add(monthLabel);
        navPanel.add(nextButton);

        return navPanel;
    }

    public void updateMonthLabel() {
        if (monthLabel != null) {
            monthLabel.setText(currentMonth.getMonth() + " " + currentMonth.getYear());
            monthLabel.setFont(new Font("Arial", Font.BOLD, 16));
            monthLabel.repaint();
        }
    }

    public void previousMonth() {
        currentMonth = currentMonth.minusMonths(1);
        updateMonthLabel();
        calendarPanel.refresh();
    }

    public void nextMonth() {
        currentMonth = currentMonth.plusMonths(1);
        updateMonthLabel();
        calendarPanel.refresh();
    }

    public void addTask(LocalDate date, String task) {
        tasks.computeIfAbsent(date, k -> new ArrayList<>()).add(task);
        calendarPanel.refresh();
    }

    public void removeTask(LocalDate date, String task) {
        java.util.List<String> taskList = tasks.get(date);
        if (taskList != null) {
            taskList.remove(task);
            if (taskList.isEmpty()) {
                tasks.remove(date);
            }
            calendarPanel.refresh();
        }
    }

    public java.util.List<String> getTasksForDate(LocalDate date) {
        return tasks.getOrDefault(date, new ArrayList<>());
    }

    public YearMonth getCurrentMonth() {
        return currentMonth;
    }

    public Map<LocalDate, java.util.List<String>> getTasks() {
        return tasks;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(CalendarApp::new);
    }
}
