package com.company;

public class Main {

    public static void main(String[] args) {
        // MODEL
        HomeworkModel model = new HomeworkModel();

        // CONTROLLER
        HomeworkController controller = new HomeworkController(model);

        try{
            Runtime.getRuntime().addShutdownHook(new Thread(() -> {controller.saveTasks();}));
        }
        catch (Exception e){
            System.out.println("got exception " + e);
            System.exit (1);
        }
    }
}
