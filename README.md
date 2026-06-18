# Calendar App - Java GUI

A simple calendar application built with Java Swing that allows users to manage tasks on a calendar.

## Features

- **Task Input Panel**: Add tasks with a specific date
- **Calendar View**: Display a month view with all tasks
- **Month Navigation**: Move between months with previous/next buttons
- **Task Display**: Tasks are displayed on their respective calendar dates

## Components

### CalendarApp.java
Main frame that orchestrates the application. Contains:
- Navigation between months
- Task storage
- Main layout with task input and calendar panels

### TaskInputPanel.java
Top-left panel for adding new tasks:
- Text field for task description
- Date input field (format: yyyy-MM-dd)
- Add button to submit the task
- Status messages

### CalendarPanel.java
Center panel displaying the calendar:
- 7x7 grid layout (7 days × weeks)
- Day headers (Sun-Sat)
- Calendar cells for each day of the month
- Automatic empty cells for days outside the current month

### CalendarCellPanel.java
Individual calendar cell:
- Displays date and day of week
- Shows all tasks for that date
- Color-coded for easy reading

## How to Run

1. Compile all Java files:
   ```bash
   javac *.java
   ```

2. Run the application:
   ```bash
   java CalendarApp
   ```

## Usage

1. Enter a task description in the "Task" field
2. Select or enter a date in drop down menu
3. Click "Add Task" to add the task to the calendar
4. Use the Previous/Next buttons to navigate between months
5. Tasks will appear on their respective dates in the calendar
6. Click the red "X" next to tasks to remove them

