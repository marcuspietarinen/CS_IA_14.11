package com.company;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class AddHomeworkView extends JFrame {
    private HomeworkController controller;
    private JTextField taskField;
    private JTextField deadlineField;
    private JTextField deleteField;
    private JTextField editField;
    private JTextField toBeEditedField;
    private JComboBox<String> typeComboBox;
    private JSpinner importanceSpinner;

    public AddHomeworkView(HomeworkController controller) {
        this.controller = controller;

        setTitle("Add Homework");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1600, 400);

        JPanel panel = new JPanel(new GridLayout(0, 3));
        JPanel addPanel = new JPanel(new GridLayout(5,1));
        addPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));

        JPanel editPanel = new JPanel(new GridLayout(3, 1));
        editPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        JPanel deletePanel = new JPanel(new GridLayout(2, 1));
        deletePanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));

        JLabel taskLabel = new JLabel("Task:");
        taskField = new JTextField(20);

        JLabel deadlineLabel = new JLabel("Deadline: (dd.MM.yyyy)");
        deadlineField = new JTextField(20);

        JLabel typeLabel = new JLabel("Type:");
        String[] types = {"Homework", "Exam", "IA", "EE", "University Application"};
        typeComboBox = new JComboBox<>(types);

        JLabel importanceLabel = new JLabel("Importance:");
        SpinnerNumberModel spinnerModel = new SpinnerNumberModel(1, 1, 5, 1);
        importanceSpinner = new JSpinner(spinnerModel);

        JLabel deleteLabel = new JLabel("Delete task: (write the name of the task you want to delete)");
        deleteField = new JTextField(20);

        JLabel emptyAddLabel = new JLabel();
        JLabel emptyDeleteLabel = new JLabel();
        JLabel emptyEditLabel = new JLabel();

        JLabel toBeEditedLabel = new JLabel("Reschedule task: (write the name of the task you want to reschedule)");
        JLabel editLabel = new JLabel ("Enter new deadline: (dd.MM.yyyy)");
        editField = new JTextField(20);
        toBeEditedField = new JTextField(20);

        JButton deleteButton = new JButton("Delete");
        deleteButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                String toBeDeleted = deleteField.getText();
                for (int i = 0; i < controller.getTasks().size(); i++)
                {
                    if (toBeDeleted.equals(controller.getTasks().get(i).getTask()))
                    {
                        controller.getTasks().remove(controller.getTasks().get(i));
                    }
                }
                deleteField.setText("");
                controller.updateListView();
                controller.updateCalendar();
                controller.updateAllTasks();
            }
        });

        JButton addButton = new JButton("Add");
        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String task = taskField.getText();
                String deadline = deadlineField.getText();
                String type = (String) typeComboBox.getSelectedItem();
                int importance = (int) importanceSpinner.getValue();

                if (!isValidDeadlineFormat(deadline)) {
                    // Show an error message to the user
                    JOptionPane.showMessageDialog(null, "Invalid deadline format. Please use dd.MM.yyyy.");
                    return; // Do not proceed further
                }
                // inform the controller about latest changes and ask to update the program
                controller.addTask(new HomeworkTask(task, deadline, type, importance));
                controller.updateListView();
                controller.updateCalendar();
                controller.updateAllTasks();

                taskField.setText("");
                deadlineField.setText("");
                typeComboBox.setSelectedIndex(0);
                importanceSpinner.setValue(1);
            }
        });



        JButton editButton = new JButton("Reschedule");
        editButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String toBeRescheduled = toBeEditedField.getText();
                String newDeadline = editField.getText();
                if (!isValidDeadlineFormat(newDeadline)) {
                    // Show an error message to the user
                    JOptionPane.showMessageDialog(null, "Invalid deadline format. Please use dd.MM.yyyy.");
                    return; // Do not proceed further
                }

                for (int i = 0; i < controller.getTasks().size(); i++)
                {
                    if (toBeRescheduled.equals(controller.getTasks().get(i).getTask()))
                    {
                        // adding a new tasks with an updated deadline, and deleting the old one
                        controller.addTask(new HomeworkTask(controller.getTasks().get(i).getTask(), newDeadline, controller.getTasks().get(i).getType(), controller.getTasks().get(i).getImportance()));
                        controller.getTasks().remove(controller.getTasks().get(i));
                    }
                }
                toBeEditedField.setText("");
                editField.setText("");
                controller.updateListView();
                controller.updateCalendar();
                controller.updateAllTasks();
            }
        });

        addPanel.add(taskLabel);
        addPanel.add(taskField);
        addPanel.add(deadlineLabel);
        addPanel.add(deadlineField);
        addPanel.add(typeLabel);
        addPanel.add(typeComboBox);
        addPanel.add(importanceLabel);
        addPanel.add(importanceSpinner);
        addPanel.add(emptyAddLabel);
        addPanel.add(addButton);
        deletePanel.add(deleteLabel);
        deletePanel.add(deleteField);
        deletePanel.add(emptyDeleteLabel);
        deletePanel.add(deleteButton);
        editPanel.add(toBeEditedLabel);
        editPanel.add(toBeEditedField);
        editPanel.add(editLabel);
        editPanel.add(editField);
        editPanel.add(emptyEditLabel);
        editPanel.add(editButton);

        panel.add(addPanel, BorderLayout.WEST);
        panel.add(editPanel, BorderLayout.CENTER);
        panel.add(deletePanel, BorderLayout.EAST);
        panel.setBackground(Color.BLACK);

        add(panel);
        setVisible(true);

        // Create the menu bar and menu items
        JMenuBar menuBar = new JMenuBar();
        JMenu switchMenu = new JMenu("Menu");
        JMenuItem listViewItem = new JMenuItem("Task list");
        listViewItem.addActionListener(new ActionListener() {
            // make listView visible and addHomeworkView invisible
            public void actionPerformed(ActionEvent e) {
                controller.toggleListView();
                controller.toggleAddHomeworkView();
            }
        });

        JMenuItem calendarViewItem = new JMenuItem("Calendar");
        calendarViewItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                controller.toggleCalendarView();
                controller.toggleAddHomeworkView();
            }
        });

        JMenuItem allTasksItem = new JMenuItem("All Tasks");
        allTasksItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                controller.toggleAllTasks();
                controller.toggleAddHomeworkView();
            }
        });


        switchMenu.add(listViewItem);
        switchMenu.add(calendarViewItem);
        switchMenu.add(allTasksItem);
        menuBar.add(switchMenu);
        setJMenuBar(menuBar);

        setVisible(true);
    }

    // method to check the deadline format
    private boolean isValidDeadlineFormat(String deadline) {
        try {
            // Attempt to parse the entered deadline
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
            LocalDate.parse(deadline, formatter);

            return true; // Parsing successful, format is valid
        } catch (DateTimeParseException e) {
            return false; // Parsing failed, format is invalid
        }
    }
}
