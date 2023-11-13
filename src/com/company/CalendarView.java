package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

/*
idea for implementation:
figure out the parsing errors first
A random monday is set as the base date, not possible to go more in the past.
instead of weekdays, a date would be shown in the caption.
Each date would have its own text area where the needed data is assigned.
week would change automatically every 7 days.

 */

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

        // Create buttons for navigating weeks
        JButton prevWeekButton = new JButton("Previous Week");
        prevWeekButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
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

        weekdays = new JTextArea[7];
        for (int i = 0; i < 7; i++)
        {
            weekdays[i] = new JTextArea(15, 10);
            weekdays[i].setEditable(false);
            weekdays[i].setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
            weekPanel.add(weekdays[i]);
        }


        // Create the menu bar and menu items
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

        updateCalendar(); // Update the calendar initially
    }

    public void updateCalendar() {
        // copy the listview way to make tasks visible;
        HomeworkTask[] realTasks = controller.sortByDeadline().toArray(new HomeworkTask[controller.sortByDeadline().size()]);
        LocalDate[] localDates = new LocalDate[realTasks.length];

        for (int i = 0; i < realTasks.length; i++) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
            localDates[i] = LocalDate.parse(realTasks[i].getDeadline(), formatter);
        }

        for (int i = 0; i < 7; i++) {
            weekdays[i].setText("");
        }

        for (int i = 0; i < realTasks.length; i++) {
            if (localDates[i].isAfter(selectedWeekStart.minusDays(1))
                    && localDates[i].isBefore(selectedWeekStart.plusDays(6))) {
                int dayOfWeek = localDates[i].getDayOfWeek().getValue() % 7; // Adjust for array indexing
                weekdays[dayOfWeek].append(realTasks[i].getTask() + " " + realTasks[i].getDeadline() + "\n");
            }
        }
    }
}

/*StringBuilder sbMon = new StringBuilder();
        StringBuilder sbTue = new StringBuilder();
        StringBuilder sbWed = new StringBuilder();
        StringBuilder sbThu = new StringBuilder();
        StringBuilder sbFri = new StringBuilder();
        StringBuilder sbSat = new StringBuilder();
        StringBuilder sbSun = new StringBuilder();*/
/* for (int i = 0; i < realTasks.length; i++)
        {
            if (localDates[i].getDayOfWeek().equals(DayOfWeek.MONDAY))
                sbMon.append(realTasks[i].getTask()).append(realTasks[i].getDeadline()).append("\n");

            if (localDates[i].getDayOfWeek().equals(DayOfWeek.TUESDAY))
                sbTue.append(realTasks[i].getTask()).append(realTasks[i].getDeadline()).append("\n");

            if (localDates[i].getDayOfWeek().equals(DayOfWeek.WEDNESDAY))
                sbWed.append(realTasks[i].getTask()).append(realTasks[i].getDeadline()).append("\n");

            if (localDates[i].getDayOfWeek().equals(DayOfWeek.THURSDAY))
                sbThu.append(realTasks[i].getTask()).append(realTasks[i].getDeadline()).append("\n");

            if (localDates[i].getDayOfWeek().equals(DayOfWeek.FRIDAY))
                sbFri.append(realTasks[i].getTask()).append(realTasks[i].getDeadline()).append("\n");

            if (localDates[i].getDayOfWeek().equals(DayOfWeek.SATURDAY))
                sbSat.append(realTasks[i].getTask()).append(realTasks[i].getDeadline()).append("\n");

            if (localDates[i].getDayOfWeek().equals(DayOfWeek.SUNDAY))
                sbSun.append(realTasks[i].getTask()).append(realTasks[i].getDeadline()).append("\n");
        }
        weekdays[0].setText(sbMon.toString());
        weekdays[1].setText(sbTue.toString());
        weekdays[2].setText(sbWed.toString());
        weekdays[3].setText(sbThu.toString());
        weekdays[4].setText(sbFri.toString());
        weekdays[5].setText(sbSat.toString());
        weekdays[6].setText(sbSun.toString()); */