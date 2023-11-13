package com.company;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

public class AllTasks extends JFrame {
    private HomeworkController controller;
    private JTextArea[] textAreas;

    public AllTasks(HomeworkController controller) {
        this.controller = controller;

        setTitle("All tasks:");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 625);

        JPanel panel = new JPanel(new BorderLayout());
        JPanel taskPanel = new JPanel(new GridLayout(5, 5));
        textAreas = new JTextArea[25];

        for (int i = 0; i < 25; i++)
        {
            textAreas[i] = new JTextArea(15, 15);
            textAreas[i].setEditable(false);
            textAreas[i].setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
            taskPanel.add(textAreas[i]);
        }

        JLabel urgency = new JLabel("                                                                                                                           Urgency");
        urgency.setForeground(Color.WHITE);
        JLabel importance = new JLabel ("Importance");
        importance.setForeground(Color.WHITE);

        JMenuBar menuBar = new JMenuBar();
        JMenu switchMenu = new JMenu("Menu");
        JMenuItem addViewMenuItem = new JMenuItem("Add Homework");
        addViewMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                controller.toggleAddHomeworkView();
                controller.toggleAllTasks();
            }
        });

        JMenuItem listViewItem = new JMenuItem("Homework List");
        listViewItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                controller.toggleListView();
                controller.toggleAllTasks();
            }
        });

        JMenuItem calendarViewItem = new JMenuItem("Calendar");
        calendarViewItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                controller.toggleCalendarView();
                controller.toggleAllTasks();
            }
        });

        switchMenu.add(addViewMenuItem);
        switchMenu.add(listViewItem);
        switchMenu.add(calendarViewItem);
        menuBar.add(switchMenu);
        setJMenuBar(menuBar);

        panel.add(taskPanel, BorderLayout.CENTER);
        panel.add(urgency, BorderLayout.SOUTH);
        panel.add(importance, BorderLayout.WEST);
        panel.setBackground(Color.BLACK);
        add(panel);
        pack();
        setVisible(true);
        updateTasks();
    }
    public void updateTasks() {

        HomeworkTask[] tasks = controller.getTasks().toArray(new HomeworkTask[controller.getTasks().size()]);
        int[] urgencies = Arrays.stream(controller.urgencyOfATask()).toArray();

        StringBuilder s15 = new StringBuilder();
        StringBuilder s25 = new StringBuilder();
        StringBuilder s35 = new StringBuilder();
        StringBuilder s45 = new StringBuilder();
        StringBuilder s55 = new StringBuilder();
        StringBuilder s14 = new StringBuilder();
        StringBuilder s24 = new StringBuilder();
        StringBuilder s34 = new StringBuilder();
        StringBuilder s44 = new StringBuilder();
        StringBuilder s54 = new StringBuilder();
        StringBuilder s13 = new StringBuilder();
        StringBuilder s23 = new StringBuilder();
        StringBuilder s33 = new StringBuilder();
        StringBuilder s43 = new StringBuilder();
        StringBuilder s53 = new StringBuilder();
        StringBuilder s12 = new StringBuilder();
        StringBuilder s22 = new StringBuilder();
        StringBuilder s32 = new StringBuilder();
        StringBuilder s42 = new StringBuilder();
        StringBuilder s52 = new StringBuilder();
        StringBuilder s11 = new StringBuilder();
        StringBuilder s21 = new StringBuilder();
        StringBuilder s31 = new StringBuilder();
        StringBuilder s41 = new StringBuilder();
        StringBuilder s51 = new StringBuilder();

        for (int i = 0; i < tasks.length; i++)
        {
            if(urgencies[i] == 5 && tasks[i].getImportance() == 5)
                s55.append(tasks[i].getTask()).append("\n");

            if(urgencies[i] == 4 && tasks[i].getImportance() == 5)
                s45.append(tasks[i].getTask()).append("\n");

            if(urgencies[i] == 3 && tasks[i].getImportance() == 5)
                s35.append(tasks[i].getTask()).append("\n");

            if(urgencies[i] == 2 && tasks[i].getImportance() == 5)
                s25.append(tasks[i].getTask()).append("\n");

            if(urgencies[i] == 1 && tasks[i].getImportance() == 5)
                s15.append(tasks[i].getTask()).append("\n");

            if(urgencies[i] == 5 && tasks[i].getImportance() == 4)
                s54.append(tasks[i].getTask()).append("\n");

            if(urgencies[i] == 4 && tasks[i].getImportance() == 4)
                s44.append(tasks[i].getTask()).append("\n");

            if(urgencies[i] == 3 && tasks[i].getImportance() == 4)
                s34.append(tasks[i].getTask()).append("\n");

            if(urgencies[i] == 2 && tasks[i].getImportance() == 4)
                s24.append(tasks[i].getTask()).append("\n");

            if(urgencies[i] == 1 && tasks[i].getImportance() == 4)
                s14.append(tasks[i].getTask()).append("\n");

            if(urgencies[i] == 5 && tasks[i].getImportance() == 3)
                s53.append(tasks[i].getTask()).append("\n");

            if(urgencies[i] == 4 && tasks[i].getImportance() == 3)
                s43.append(tasks[i].getTask()).append("\n");

            if(urgencies[i] == 3 && tasks[i].getImportance() == 3)
                s33.append(tasks[i].getTask()).append("\n");

            if(urgencies[i] == 2 && tasks[i].getImportance() == 3)
                s23.append(tasks[i].getTask()).append("\n");

            if(urgencies[i] == 1 && tasks[i].getImportance() == 3)
                s13.append(tasks[i].getTask()).append("\n");

            if(urgencies[i] == 5 && tasks[i].getImportance() == 2)
                s52.append(tasks[i].getTask()).append("\n");

            if(urgencies[i] == 4 && tasks[i].getImportance() == 2)
                s42.append(tasks[i].getTask()).append("\n");

            if(urgencies[i] == 3 && tasks[i].getImportance() == 2)
                s32.append(tasks[i].getTask()).append("\n");

            if(urgencies[i] == 2 && tasks[i].getImportance() == 2)
                s22.append(tasks[i].getTask()).append("\n");

            if(urgencies[i] == 1 && tasks[i].getImportance() == 2)
                s12.append(tasks[i].getTask()).append("\n");

            if(urgencies[i] == 5 && tasks[i].getImportance() == 1)
                s51.append(tasks[i].getTask()).append("\n");

            if(urgencies[i] == 4 && tasks[i].getImportance() == 1)
                s41.append(tasks[i].getTask()).append("\n");

            if(urgencies[i] == 3 && tasks[i].getImportance() == 1)
                s31.append(tasks[i].getTask()).append("\n");

            if(urgencies[i] == 2 && tasks[i].getImportance() == 1)
                s21.append(tasks[i].getTask()).append("\n");

            if(urgencies[i] == 1 && tasks[i].getImportance() == 1)
                s11.append(tasks[i].getTask()).append("\n");
        }

        /*for (int u = 0; u < tasks.length; u++)
        {
            for (int i = 0; i < tasks.length; i++)
            {
                textAreas[urgencies[u] - 1][tasks[i].getImportance() - 1].setText(s11.toString());
            }
        }*/
        textAreas[4].setText(s55.toString());
        textAreas[3].setText(s45.toString());
        textAreas[2].setText(s35.toString());
        textAreas[1].setText(s25.toString());
        textAreas[0].setText(s15.toString());
        textAreas[9].setText(s54.toString());
        textAreas[8].setText(s44.toString());
        textAreas[7].setText(s34.toString());
        textAreas[6].setText(s24.toString());
        textAreas[5].setText(s14.toString());
        textAreas[14].setText(s53.toString());
        textAreas[13].setText(s43.toString());
        textAreas[12].setText(s33.toString());
        textAreas[11].setText(s23.toString());
        textAreas[10].setText(s13.toString());
        textAreas[19].setText(s52.toString());
        textAreas[18].setText(s42.toString());
        textAreas[17].setText(s32.toString());
        textAreas[16].setText(s22.toString());
        textAreas[15].setText(s12.toString());
        textAreas[14].setText(s51.toString());
        textAreas[24].setText(s41.toString());
        textAreas[23].setText(s31.toString());
        textAreas[22].setText(s21.toString());
        textAreas[21].setText(s11.toString());
    }
}
