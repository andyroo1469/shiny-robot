import javax.swing.*;
import java.awt.*;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.temporal.TemporalAdjusters;
import java.util.List;

public class CalendarPanel extends JPanel {
    private CalendarApp app;
    private static final String[] DAYS_OF_WEEK = {"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};
    private static final int CELL_HEIGHT = 100;
    private static final int CELL_WIDTH = 140;

    public CalendarPanel(CalendarApp app) {
        this.app = app;
        setLayout(new GridLayout(7, 7, 5, 5));
        setBackground(new Color(245, 245, 245));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        refresh();
    }

    public void refresh() {
        removeAll();
        drawCalendar();
        revalidate();
        repaint();
    }

    private void drawCalendar() {
        // Draw day headers
        for (String day : DAYS_OF_WEEK) {
            JLabel dayHeader = new JLabel(day, SwingConstants.CENTER);
            dayHeader.setFont(new Font("Arial", Font.BOLD, 12));
            dayHeader.setOpaque(true);
            dayHeader.setBackground(new Color(100, 150, 200));
            dayHeader.setForeground(Color.WHITE);
            dayHeader.setPreferredSize(new Dimension(CELL_WIDTH, 30));
            add(dayHeader);
        }

        YearMonth yearMonth = app.getCurrentMonth();
        LocalDate firstDay = yearMonth.atDay(1);
        LocalDate lastDay = yearMonth.atEndOfMonth();

        // Empty cells before first day
        int firstDayOfWeek = firstDay.getDayOfWeek().getValue() % 7;
        for (int i = 0; i < firstDayOfWeek; i++) {
            add(new JPanel());
        }

        // Calendar days
        LocalDate current = firstDay;
        while (!current.isAfter(lastDay)) {
            CalendarCellPanel cellPanel = new CalendarCellPanel(current, app);
            add(cellPanel);
            current = current.plusDays(1);
        }

        // Empty cells after last day
        int remainingCells = 42 - (firstDayOfWeek + (int) java.time.temporal.ChronoUnit.DAYS.between(firstDay, lastDay) + 1);
        for (int i = 0; i < remainingCells; i++) {
            add(new JPanel());
        }
    }
}
