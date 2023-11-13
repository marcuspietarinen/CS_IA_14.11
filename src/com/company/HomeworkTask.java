package com.company;

import java.io.Serializable;

public class HomeworkTask implements Serializable {
    private String task;
    private String deadline;
    private String type;
    private int importance;

    public HomeworkTask(String task, String deadline, String type, int importance) {
        this.task = task;
        this.deadline = deadline;
        this.type = type;
        this.importance = importance;
    }

    public String getTask() {
        return task;
    }

    public String getDeadline() {
        return deadline;
    }

    public String getType() {
        return type;
    }

    public int getImportance() {
        return importance;
    }

}
