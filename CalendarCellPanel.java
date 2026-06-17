import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;

public class CalendarCellPanel extends JPanel {
    private LocalDate date;
    private CalendarApp app;

    public CalendarCellPanel(LocalDate date, CalendarApp app) {
        this.date = date;
        this.app = app;
        setLayout(new BorderLayout(2, 2));
        setBorder(BorderFactory.createLineBorder(new Color(150, 150, 150)));
        setBackground(Color.WHITE);
        setPreferredSize(new Dimension(140, 100));

        // Date info panel
        JPanel dateInfoPanel = createDateInfoPanel();
        add(dateInfoPanel, BorderLayout.NORTH);

        // Tasks panel
        JPanel tasksPanel = createTasksPanel();
        add(tasksPanel, BorderLayout.CENTER);
    }

    private JPanel createDateInfoPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 2));
        panel.setBackground(new Color(230, 240, 250));

        // Date
        JLabel dateLabel = new JLabel(String.valueOf(date.getDayOfMonth()));
        dateLabel.setFont(new Font("Arial", Font.BOLD, 14));
        panel.add(dateLabel);

        // Day of week
        String dayName = date.getDayOfWeek().getDisplayName(TextStyle.SHORT, Locale.US);
        JLabel dayLabel = new JLabel(dayName);
        dayLabel.setFont(new Font("Arial", Font.PLAIN, 10));
        dayLabel.setForeground(new Color(100, 100, 100));
        panel.add(dayLabel);

        return panel;
    }

    private JPanel createTasksPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        List<String> tasksForDate = app.getTasksForDate(date);
        if (tasksForDate.isEmpty()) {
            panel.add(Box.createVerticalGlue());
        } else {
            for (String task : tasksForDate) {
                JPanel taskRow = createTaskRow(task);
                panel.add(taskRow);
            }
            panel.add(Box.createVerticalGlue());
        }

        return panel;
    }

    private JPanel createTaskRow(String task) {
        JPanel taskRow = new JPanel(new BorderLayout(3, 0));
        taskRow.setBackground(Color.WHITE);
        taskRow.setMaximumSize(new Dimension(Integer.MAX_VALUE, 20));

        // Task label
        JLabel taskLabel = new JLabel(">" + task);
        taskLabel.setFont(new Font("Arial", Font.PLAIN, 9));
        taskLabel.setForeground(new Color(50, 100, 200));
        taskRow.add(taskLabel, BorderLayout.CENTER);

        // Delete button
        JButton deleteBtn = new JButton("X");
        deleteBtn.setMargin(new Insets(0, 2, 0, 2));
        deleteBtn.setFont(new Font("Arial", Font.PLAIN, 8));
        deleteBtn.setPreferredSize(new Dimension(20, 16));
        deleteBtn.setBackground(new Color(255, 100, 100));
        deleteBtn.setForeground(Color.WHITE);
        deleteBtn.setFocusPainted(false);
        deleteBtn.setBorder(BorderFactory.createEmptyBorder());
        deleteBtn.addActionListener(e -> app.removeTask(date, task));
        taskRow.add(deleteBtn, BorderLayout.EAST);

        return taskRow;
    }
}
