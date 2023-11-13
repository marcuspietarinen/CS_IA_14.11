package com.company;
import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class HomeworkController {
    private HomeworkModel model;
    private HomeworkTask task;
    private AddHomeworkView addView;
    private HomeworkListView listView;
    private CalendarView calendarView;
    private AllTasks allTasks;

    public HomeworkController(HomeworkModel model) {
        this.model = model;
        this.addView = new AddHomeworkView(this);
        this.listView = new HomeworkListView(this);
        this.calendarView = new CalendarView(this);
        this.allTasks = new AllTasks(this);

        if (file.exists()) {
            restoreTasks();
            updateListView();
            updateCalendar();
            updateAllTasks();
        }

        addView.setVisible(false);
        listView.setVisible(true);
        calendarView.setVisible(false);
        allTasks.setVisible(false);
    }

    String itemName = "items.ser";
    File file = new File(itemName);

    public void saveTasks()
    {
        try {
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(itemName));
            out.writeObject(model.getTasks());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void restoreTasks()
    {
        try {
            ObjectInputStream in = new ObjectInputStream(new FileInputStream(itemName));
            List<HomeworkTask> restoredTasks = (List<HomeworkTask>) in.readObject();
            model.setTasks(restoredTasks);
            in.close();
        }
        catch(IOException e) {
            e.printStackTrace();
        }
        catch(ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void updateListView() {
        listView.updateTaskList();
    }

    public void updateCalendar() { calendarView.updateCalendar(); }

    public void updateAllTasks() { allTasks.updateTasks(); }

    public List<HomeworkTask> getTasks()
    {
        return model.getTasks();
    }

    public void addTask (HomeworkTask task)
    {
        model.addTask(task);
    }


    public void toggleListView() {
        boolean isVisible = listView.isVisible();
        listView.setVisible(!isVisible);
    }

    public void toggleCalendarView() {
        boolean isVisible = calendarView.isVisible();
        calendarView.setVisible(!isVisible);
    }

    public void toggleAllTasks() {
        boolean isVisible = allTasks.isVisible();
        allTasks.setVisible(!isVisible);
    }

    public void toggleAddHomeworkView() {
        boolean isVisible = addView.isVisible();
        addView.setVisible(!isVisible);
    }


    // SIMPLIFY IN FUTURE
//    public void toggleView(String view) {
//        if (view.equals("list"))
//    }

    public List<HomeworkTask> forTomorrow() {
        //IMAGINE YOU NEED THESE IN THE FUTURE:
        //tasks due tomorrow
        //tasks due overmorrow
        //tasks due yesterday
        //tasks due next Monday

//        INSTEAD OF THIS:
//        model.forOvermorrow();
//        model.forYesterday();
//        model.forNextMonday();
//        return model.forTomorrow();

//        DO THIS:
//            model.forDate(new Date());

        return model.forTomorrow();
    }


    public List<HomeworkTask> longTermProjects(){
        return model.longTermProjects();
    }

    public List<HomeworkTask> doNow(){
        return model.doNow();
    }

    public List<HomeworkTask> late(){
        return model.late();
    }

    public List<HomeworkTask> sortByDeadline() { return model.sortByDeadline(); }

    public int[] urgencyOfATask()
    {
        return model.urgencyOfATask();
    }
}
