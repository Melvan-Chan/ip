package veronica.task;

import veronica.misc.Storage;
import veronica.ui.Ui;
import veronica.main.Veronica;
import veronica.main.VeronicaException;

import java.util.ArrayList;
import java.util.List;

public class TaskManager {
    private Task[] tasks;
    private int taskCount;
    private static final Storage storage = new Storage(Veronica.FILE_PATH);

    public TaskManager() {
        tasks = storage.loadTasks();
        this.taskCount = storage.getTaskCount();
    }

    public void listTasks() {
        Ui.showList(tasks, taskCount);
    }

    public void markTask(String input) throws VeronicaException {
        int taskIndex = Integer.parseInt(input.substring(5)) - 1;
        if (taskIndex >= 0 && taskIndex < taskCount) {
            tasks[taskIndex].markAsComplete();
            Ui.showTaskMarkedMessage(tasks[taskIndex]);
        } else {
            throw new VeronicaException("UHOH! task.Task number does not exist.");
        }
    }

    public void unmarkTask(String input) throws VeronicaException {
        int taskIndex = Integer.parseInt(input.substring(7)) - 1;
        if (taskIndex >= 0 && taskIndex < taskCount) {
            tasks[taskIndex].markAsIncomplete();
            Ui.showTaskUnmarkedMessage(tasks[taskIndex]);
        } else {
            throw new VeronicaException("UHOH! task.Task number does not exist.");
        }
    }

    public void removeTask(String input) throws VeronicaException {
        String argument = input.substring(7).trim();
        if (argument.equalsIgnoreCase("all")) {
            taskCount = 0;
            tasks = new Task[Veronica.MAX_TASK_SIZE];
            Ui.showTaskRemovedAllMessage();
        } else {
            int taskIndex = Integer.parseInt(argument) - 1;
            if (taskIndex >= 0 && taskIndex < taskCount) {
                Task removedTask = tasks[taskIndex];

                // Shift tasks left
                for (int i = taskIndex; i < taskCount - 1; i++) {
                    tasks[i] = tasks[i + 1];
                }
                tasks[taskCount - 1] = null; // Clear last reference
                taskCount--;

                Ui.showTaskRemovedMessage(removedTask, taskCount);
            } else {
                throw new VeronicaException("UHOH! task.Task number does not exist.");
            }
        }
    }

    public void addTodo(String input) throws VeronicaException {
        String taskDescription = input.substring(5).trim();
        if (taskDescription.isEmpty()) {
            throw new VeronicaException("UHOH! Description can't be empty.");
        }
        tasks[taskCount++] = new ToDo(taskDescription);
        Ui.showTaskAddedMessage(tasks[taskCount - 1], taskCount);
    }

    public void addDeadline(String input) throws VeronicaException {
        String[] parts = input.substring(9).split(" /by ");
        if (parts.length == 2) {
            Deadline currTask = new Deadline(parts[0], parts[1]);

            if (currTask.getDateAllowed()) {
                tasks[taskCount++] = currTask;
                Ui.showTaskAddedMessage(tasks[taskCount - 1], taskCount);
            }
        } else {
            throw new VeronicaException("UHOH! Invalid format detected. Use: deadline <task> /by <date>");
        }
    }

    public void addEvent(String input) throws VeronicaException {
        String[] parts = input.substring(6).split(" /from | /to ");
        if (parts.length == 3) {
            Event currTask = new Event(parts[0], parts[1], parts[2]);

            if (currTask.getDateAllowed()) {
                tasks[taskCount++] = currTask;
                Ui.showTaskAddedMessage(tasks[taskCount - 1], taskCount);
            }
        } else {
            throw new VeronicaException("UHOH! Invalid format detected. Use: event <task> /from <start> /to <end>");
        }
    }

    public void findTasks(String input) throws VeronicaException {
        String taskKeyword = input.substring(5).trim();
        if (taskKeyword.isEmpty()) {
            throw new VeronicaException("UHOH! Keyword description can't be empty.");
        }

        List<Task> matchingTasks = new ArrayList<Task>();
        for (int i = 0; i < taskCount; i++) {
            if(tasks[i].getDescription().toLowerCase().contains(taskKeyword.toLowerCase())) {
                matchingTasks.add(tasks[i]);
            }
        }

        if (matchingTasks.isEmpty()) {
            Ui.showNoMatchingTask(taskKeyword);
        } else {
            Ui.showMatchingTask(matchingTasks, taskKeyword);
        }
    }

    public void exitProgram() {
        storage.saveTasks(tasks, taskCount);
    }
}
