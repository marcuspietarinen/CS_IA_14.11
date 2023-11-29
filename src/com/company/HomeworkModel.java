package com.company;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

// Model class holding the data related to homework tasks
public class HomeworkModel implements Serializable{
    private List<HomeworkTask> tasks;

    public HomeworkModel() {
        tasks = new ArrayList<>();
    }

    public void addTask(HomeworkTask task) {
        tasks.add(task);
    }

    public void setTasks(List<HomeworkTask> tasks) {
        this.tasks = tasks;
    }

    public List<HomeworkTask> getTasks() {
        return tasks;
    }

    public List<HomeworkTask> forTomorrow() {
        HomeworkTask[] toBeEvaluated = late().toArray(new HomeworkTask[late().size()]);
        List<HomeworkTask> tasksDueTomorrow = new ArrayList<>();
        LocalDate[] localDates = new LocalDate[toBeEvaluated.length];

        // Filling the localDate array based on the deadlines of the tasks
        for (int i = 0; i < toBeEvaluated.length; i++)
        {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
            localDates[i] = LocalDate.parse(toBeEvaluated[i].getDeadline(), formatter);
        }

        // adding tasks to the list if the deadline of the task is before overmorrow
        for(int i = 0; i < localDates.length; i++)
        {
            if (localDates[i].isBefore(LocalDate.now().plusDays(2)))
                tasksDueTomorrow.add(toBeEvaluated[i]);
        }
        return tasksDueTomorrow;
    }

    public List<HomeworkTask> doNow () {
        HomeworkTask[] toBeEvaluated = late().toArray(new HomeworkTask[late().size()]);
        LocalDate[] localDates = new LocalDate[toBeEvaluated.length];
        List<HomeworkTask> finalTasks = new ArrayList<>();
        int[] urgencyValues = new int[tasks.size()];

        // Filling the localDate array based on the deadlines of the tasks
        for (int i = 0; i < toBeEvaluated.length; i++)
        {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
            localDates[i] = LocalDate.parse(toBeEvaluated[i].getDeadline(), formatter);
        }

        for (int i = 0; i < localDates.length; i++)
        {
            // assigning the urgency values based on the deadlines
            if (localDates[i].isBefore(LocalDate.now().plusDays(2)))
                urgencyValues[i] = 5;
            else if (localDates[i].isBefore(LocalDate.now().plusDays(4)))
                urgencyValues[i] = 4;
            else if (localDates[i].isBefore(LocalDate.now().plusDays(8)))
                urgencyValues[i] = 3;
            else if (localDates[i].isBefore(LocalDate.now().plusDays(15)))
                urgencyValues[i] = 2;
            else if (localDates[i].isAfter(LocalDate.now().plusDays(15)))
                urgencyValues[i] = 1;
        }

        int[] qualificationValues = new int[toBeEvaluated.length];
        // multiplies the corresponding importance and urgency values and adds them into the new array
        for (int i = 0; i < toBeEvaluated.length; i++)
        {
            qualificationValues[i] = toBeEvaluated[i].getImportance() * urgencyValues[i];
        }

        boolean changed = true;

        // looping through the elements of the task array while the multiplied values are not in order
        while (changed)
        {
            changed = false;
            for (int j = 1; j < qualificationValues.length; j++)
            {
                if(qualificationValues[j - 1] > qualificationValues[j])
                {
                    // swapping the places of localDates and the corresponding tasks
                    swapInt(qualificationValues, j - 1, j);
                    swapHomeworktask(toBeEvaluated, j - 1, j);
                    changed = true;
                }
            }
        }

        // getting maximum 3 tasks from the end of the sorted array (biggest values) to the list
        for (int k = qualificationValues.length - 1; k >= 0 && k >= qualificationValues.length - 3; k--)
        {
            finalTasks.add(toBeEvaluated[k]);
        }
        return finalTasks;
    }

    public int[] urgencyOfATask() {
        HomeworkTask[] toBeEvaluated = late().toArray(new HomeworkTask[late().size()]);
        LocalDate[] localDates = new LocalDate[toBeEvaluated.length];
        int[] urgencyValues = new int[tasks.size()];

        // Filling the localDate array based on the deadlines of the tasks
        for (int i = 0; i < toBeEvaluated.length; i++)
        {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
            localDates[i] = LocalDate.parse(toBeEvaluated[i].getDeadline(), formatter);
        }

        for (int i = 0; i < toBeEvaluated.length; i++)
        {
            // assigning the urgency values based on the deadlines
            if (localDates[i].isBefore(LocalDate.now().plusDays(2)))
                urgencyValues[i] = 5;
            else if (localDates[i].isBefore(LocalDate.now().plusDays(4)))
                urgencyValues[i] = 4;
            else if (localDates[i].isBefore(LocalDate.now().plusDays(8)))
                urgencyValues[i] = 3;
            else if (localDates[i].isBefore(LocalDate.now().plusDays(15)))
                urgencyValues[i] = 2;
            else if (localDates[i].isAfter(LocalDate.now().plusDays(15)))
                urgencyValues[i] = 1;
        }
        return urgencyValues;
    }

    public List<HomeworkTask> sortByDeadline() {
        HomeworkTask[] toBeSorted = late().toArray(new HomeworkTask[late().size()]);
        LocalDate[] localDates = new LocalDate[toBeSorted.length];
        List<HomeworkTask> tasksByDeadline = new ArrayList<>();

        // Filling the localDate array based on the deadlines of the tasks
        for (int i = 0; i < toBeSorted.length; i++)
        {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
            localDates[i] = LocalDate.parse(toBeSorted[i].getDeadline(), formatter);
        }

        boolean changed = true;

        while (changed)
        {
            // looping through the elements of the task array while the localDates are not in order
            changed = false;
            for (int i = 1; i < toBeSorted.length; i++)
            {
                if (localDates[i - 1].isAfter(localDates[i]))
                {
                    // swapping the places of localDates and the corresponding tasks
                    swapLocalDate(localDates, i, i - 1);
                    swapHomeworktask(toBeSorted, i, i - 1);
                    changed = true;
                }
            }
        }
        for (int i = 0; i < toBeSorted.length; i++)
        {
            // adding the elements into the list after being sorted by the deadlines
            tasksByDeadline.add(toBeSorted[i]);
        }
        return tasksByDeadline;
    }

    public List<HomeworkTask> longTermProjects () {
        HomeworkTask[] toBeChecked = late().toArray(new HomeworkTask[late().size()]);
        List<HomeworkTask> dontForgetThese = new ArrayList<>();

        // Checking if the task type is IA, EE or University application, if yes, the task will be added to a list
        for (int i = 0; i < toBeChecked.length; i++)
        {
            if (toBeChecked[i].getType().equals("IA") || toBeChecked[i].getType().equals("EE") || toBeChecked[i].getType().equals("University Application"))
            {
                dontForgetThese.add(toBeChecked[i]);
            }
        }
        return dontForgetThese;
    }

    public List<HomeworkTask> late () {
        HomeworkTask[] toBeChecked = tasks.toArray(new HomeworkTask[tasks.size()]);
        List<HomeworkTask> lateTasks = new ArrayList<>();
        LocalDate[] localDates = new LocalDate[toBeChecked.length];

        // Filling the localDate array based on the deadlines of the tasks
        for (int i = 0; i < toBeChecked.length; i++)
        {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
            localDates[i] = LocalDate.parse(toBeChecked[i].getDeadline(), formatter);
        }

        // Checking if the deadlines of the tasks are after now, if yes, the task will be added to a list
        for (int i = 0; i < toBeChecked.length; i++)
        {
            if (localDates[i].isAfter(LocalDate.now().minusDays(1)))
                lateTasks.add(toBeChecked[i]);
        }
        return lateTasks;
    }

    public static void swapHomeworktask (HomeworkTask[] arr, int i, int j)
    {
        HomeworkTask tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }

    public static void swapInt (int[] arr, int i, int j)
    {
        int tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }

    public static void swapLocalDate (LocalDate[] arr, int i, int j)
    {
        LocalDate tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }
}
