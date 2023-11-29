package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class CalendarView extends JFrame {
    private HomeworkController controller;
    private JTextArea[] weekdays;
    private LocalDate selectedWeekStart;

    public CalendarView (HomeworkController controller) {
        super("Calendar");
        this.controller = controller;
        this.selectedWeekStart = LocalDate.now().with(DayOfWeek.MONDAY);


        JPanel calendarPanel = new JPanel();

        JPanel weekPanel = new JPanel();
        weekPanel.setLayout(new GridLayout(0, 7));

        setTitle("Calendar");
        setSize(1000, 625);

        JLabel mondayLabel = new JLabel("Mon.");
        mondayLabel.setForeground(Color.WHITE);
        JLabel tuesdayLabel = new JLabel("Tue.");
        tuesdayLabel.setForeground(Color.WHITE);
        JLabel wednesdayLabel = new JLabel("Wed.");
        wednesdayLabel.setForeground(Color.WHITE);
        JLabel thursdayLabel = new JLabel("Thu.");
        thursdayLabel.setForeground(Color.WHITE);
        JLabel fridayLabel = new JLabel("Fri.");
        fridayLabel.setForeground(Color.WHITE);
        JLabel saturdayLabel = new JLabel("Sat.");
        saturdayLabel.setForeground(Color.WHITE);
        JLabel sundayLabel = new JLabel("Sun.");
        sundayLabel.setForeground(Color.WHITE);


        LocalDate currentDate = selectedWeekStart;


        JPanel buttonPanel = new JPanel();

        // Creating buttons for navigating weeks
        JButton prevWeekButton = new JButton("Previous Week");
        prevWeekButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                // check whether the user is allowed to navigate any further
                if(selectedWeekStart.isAfter(currentDate))
                {
                    selectedWeekStart = selectedWeekStart.minusWeeks(1);
                }
                updateCalendar();
            }
        });

        JButton nextWeekButton = new JButton("Next Week");
        nextWeekButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

                // check whether the user is allowed to navigate any further
                if (selectedWeekStart.plusWeeks(1).isBefore(LocalDate.parse(controller.sortByDeadline().get(controller.sortByDeadline().size()-1).getDeadline(), formatter)))
                {
                    selectedWeekStart = selectedWeekStart.plusWeeks(1);
                }
                updateCalendar();
            }
        });

        buttonPanel.add(prevWeekButton);
        buttonPanel.add(nextWeekButton);
        buttonPanel.setBackground(Color.BLACK);


        weekPanel.add(mondayLabel);
        weekPanel.add(tuesdayLabel);
        weekPanel.add(wednesdayLabel);
        weekPanel.add(thursdayLabel);
        weekPanel.add(fridayLabel);
        weekPanel.add(saturdayLabel);
        weekPanel.add(sundayLabel);
        weekPanel.setBackground(Color.BLACK);

        // Holds text areas for each day of the week.
        weekdays = new JTextArea[7];
        for (int i = 0; i < 7; i++)
        {
            weekdays[i] = new JTextArea(15, 10);
            weekdays[i].setEditable(false);
            weekdays[i].setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
            weekPanel.add(weekdays[i]);
        }


        // Creating the menu bar and menu items
        JMenuBar menuBar = new JMenuBar();
        JMenu switchMenu = new JMenu("Menu");
        JMenuItem addViewMenuItem = new JMenuItem("Add Homework");
        addViewMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                controller.toggleAddHomeworkView();
                controller.toggleCalendarView();
            }
        });
        JMenuItem listViewMenuItem = new JMenuItem("Homework List");
        listViewMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                controller.toggleListView();
                controller.toggleCalendarView();
            }
        });

        JMenuItem allTasksItem = new JMenuItem("All Tasks");
        allTasksItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                controller.toggleAllTasks();
                controller.toggleCalendarView();
            }
        });

        calendarPanel.add(buttonPanel);
        calendarPanel.add(weekPanel);
        calendarPanel.setBackground(Color.BLACK);
        add(calendarPanel);
        switchMenu.add(addViewMenuItem);
        switchMenu.add(listViewMenuItem);
        switchMenu.add(allTasksItem);
        menuBar.add(switchMenu);
        setJMenuBar(menuBar);
        pack();
        setVisible(true);

        updateCalendar();
    }

    // Method to update the displayed calendar
    public void updateCalendar() {
        HomeworkTask[] realTasks = controller.sortByDeadline().toArray(new HomeworkTask[controller.sortByDeadline().size()]);
        LocalDate[] localDates = new LocalDate[realTasks.length];

        // Filling the localDate array based on the deadlines of the tasks
        for (int i = 0; i < realTasks.length; i++) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
            localDates[i] = LocalDate.parse(realTasks[i].getDeadline(), formatter);
        }

        // Clear all text areas to prepare for updating
        for (int i = 0; i < 7; i++) {
            weekdays[i].setText("");
        }

        for (int i = 0; i < realTasks.length; i++) {

            // check if the task's deadline falls within the selected week
            if (localDates[i].isAfter(selectedWeekStart.minusDays(1))
                    && localDates[i].isBefore(selectedWeekStart.plusDays(6))) {

                // get the day of the week (0-6) and adjust for array indexing
                int dayOfWeek = localDates[i].getDayOfWeek().getValue() % 7;

                // add the relevant task details to the corresponding day's text area
                weekdays[dayOfWeek - 1].append(realTasks[i].getTask() + " " + realTasks[i].getDeadline() + "\n");
            }
        }
    }
}