package com.company;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class HomeworkListView extends JFrame{
    private HomeworkController controller;
    private JTextArea forTomorrowArea;
    private JTextArea otherSuggestionsArea;
    private JTextArea longTermProjectsArea;


    public HomeworkListView(HomeworkController controller) {
        this.controller = controller;

        setTitle("Homework List");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 625);

        JPanel panel = new JPanel(new GridLayout(0, 3));
        JLabel forTomorrowLabel = new JLabel("For Tomorrow");
        forTomorrowLabel.setForeground(Color.WHITE);
        forTomorrowArea = new JTextArea(10, 20);
        forTomorrowArea.setEditable(false);
        forTomorrowArea.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));

        JLabel otherSuggestionsLabel = new JLabel("Other Suggestions");
        otherSuggestionsLabel.setForeground(Color.WHITE);
        otherSuggestionsArea = new JTextArea(10, 20);
        otherSuggestionsArea.setEditable(false);
        otherSuggestionsArea.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));

        JLabel longTermProjectsLabel = new JLabel("Long-Term Projects");
        longTermProjectsLabel.setForeground(Color.WHITE);
        longTermProjectsArea = new JTextArea(10, 20);
        longTermProjectsArea.setEditable(false);
        longTermProjectsArea.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));

        panel.add(forTomorrowLabel);
        panel.add(otherSuggestionsLabel);
        panel.add(longTermProjectsLabel);
        panel.add(forTomorrowArea);
        panel.add(otherSuggestionsArea);
        panel.add(longTermProjectsArea);
        panel.setBackground(Color.BLACK);

        add(panel);


        // Creating the menu bar and menu items
        JMenuBar menuBar = new JMenuBar();
        JMenu switchMenu = new JMenu("Menu");
        JMenuItem addViewMenuItem = new JMenuItem("Add Tasks");
        addViewMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                controller.toggleAddHomeworkView();
                controller.toggleListView();
            }
        });

        JMenuItem calendarViewItem = new JMenuItem("Calendar");
        calendarViewItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                controller.toggleCalendarView();
                controller.toggleListView();
            }
        });

        JMenuItem allTasksItem = new JMenuItem("All Tasks");
        allTasksItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                controller.toggleAllTasks();
                controller.toggleListView();
            }
        });

        switchMenu.add(addViewMenuItem);
        switchMenu.add(calendarViewItem);
        switchMenu.add(allTasksItem);
        menuBar.add(switchMenu);
        setJMenuBar(menuBar);

        setVisible(true);

        updateTaskList();
    }

    // Method to update the displayed tasks
    public void updateTaskList() {

        // Getting the tasks to display from the controller that retrieves them from the model
        List<HomeworkTask> dueTomorrow = controller.forTomorrow();
        StringBuilder sb1 = new StringBuilder();

        List<HomeworkTask> suggestions = controller.doNow();
        StringBuilder sb2 = new StringBuilder();

        List<HomeworkTask> dontForgetThese = controller.longTermProjects();
        StringBuilder sb3 = new StringBuilder();

        // loop through the tasks and add relevant information to the string builders
        for (HomeworkTask task : dueTomorrow) {
            sb1.append(task.getTask()).append("\n");
        }

        for (HomeworkTask task : suggestions) {
            sb2.append(task.getTask()).append("\n");
        }


        for (HomeworkTask task : dontForgetThese) {
            sb3.append(task.getTask()).append(" ").append(task.getDeadline()).append("\n");
        }

        // set the tasks to be displayed
        forTomorrowArea.setText(sb1.toString());
        otherSuggestionsArea.setText(sb2.toString());
        longTermProjectsArea.setText(sb3.toString());
    }
}
