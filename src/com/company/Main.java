package com.company;

// The main class initiating the application
public class Main {

    public static void main(String[] args) {
        // Creating the model for managing homework tasks
        HomeworkModel model = new HomeworkModel();

        // Creating the controller to manage interactions between model and views
        HomeworkController controller = new HomeworkController(model);

        try{
            // Adding a shutdown hook to save tasks when the application exits
            Runtime.getRuntime().addShutdownHook(new Thread(() -> {controller.saveTasks();}));
        }
        catch (Exception e){
            // Handling exceptions gracefully if saving tasks fails
            System.out.println("got exception " + e);
            System.exit (1);
        }
    }
}
