package veronica.task;

import veronica.misc.Storage;
import veronica.ui.Ui;
import veronica.main.Veronica;
import veronica.main.VeronicaException;

import java.util.ArrayList;
import java.util.List;


/**
 * Manages the list of tasks, including adding, removing, and marking tasks.
 */

public class TaskManager {
    private Task[] tasks;
    private int taskCount;
    private static final Storage storage = new Storage(Veronica.FILE_PATH);

    /**
     * Constructs a TaskManager and loads tasks from storage.
     */
    public TaskManager() {
        tasks = storage.loadTasks();
        this.taskCount = storage.getTaskCount();
    }

    /**
     * Lists all tasks currently in the task manager.
     */
    public void listTasks() {
        Ui.showList(tasks, taskCount);
    }

    /**
     * Marks a task as completed.
     *
     * @param input The user input containing the task number.
     * @throws VeronicaException If the task number is invalid.
     */
    public void markTask(String input) throws VeronicaException {
        int taskIndex = Integer.parseInt(input.substring(5)) - 1;
        if (taskIndex >= 0 && taskIndex < taskCount) {
            tasks[taskIndex].markAsComplete();
            Ui.showTaskMarkedMessage(tasks[taskIndex]);
        } else {
            throw new VeronicaException("UHOH! Task number does not exist.");
        }
    }

    /**
     * Marks a task as incomplete.
     *
     * @param input The user input containing the task number.
     * @throws VeronicaException If the task number is invalid.
     */
    public void unmarkTask(String input) throws VeronicaException {
        int taskIndex = Integer.parseInt(input.substring(7)) - 1;
        if (taskIndex >= 0 && taskIndex < taskCount) {
            tasks[taskIndex].markAsIncomplete();
            Ui.showTaskUnmarkedMessage(tasks[taskIndex]);
        } else {
            throw new VeronicaException("UHOH! Task number does not exist.");
        }
    }

    /**
     * Removes a task or clears all tasks.
     *
     * @param input The user input specifying which task to remove.
     * @throws VeronicaException If the task number is invalid.
     */
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
                throw new VeronicaException("UHOH! Task number does not exist.");
            }
        }
    }

    /**
     * Adds a new To-Do task to the list.
     *
     * @param input The user input containing the task description.
     * @throws VeronicaException If the description is empty.
     */
    public void addTodo(String input) throws VeronicaException {
        String taskDescription = input.substring(5).trim();
        if (taskDescription.isEmpty()) {
            throw new VeronicaException("UHOH! Description can't be empty.");
        }
        tasks[taskCount++] = new ToDo(taskDescription);
        Ui.showTaskAddedMessage(tasks[taskCount - 1], taskCount);
    }

    /**
     * Adds a new Deadline task to the list.
     *
     * @param input The user input containing the task description and due date.
     * @throws VeronicaException If the format is incorrect or missing required details.
     */
    public void addDeadline(String input) throws VeronicaException {
        String[] parts = input.substring(9).split(" /by ");
        if (parts.length == 2) {
            Deadline currTask = new Deadline(parts[0], parts[1]);

            if (currTask.isDateAllowed()) {
                tasks[taskCount++] = currTask;
                Ui.showTaskAddedMessage(tasks[taskCount - 1], taskCount);
            }
        } else {
            throw new VeronicaException("UHOH! Invalid format detected. Use: deadline <task> /by <date>");
        }
    }

    /**
     * Adds a new Event task to the list.
     *
     * @param input The user input containing the event description, start date, and end date.
     * @throws VeronicaException If the format is incorrect or missing required details.
     */
    public void addEvent(String input) throws VeronicaException {
        String[] parts = input.substring(6).split(" /from | /to ");
        if (parts.length == 3) {
            Event currTask = new Event(parts[0], parts[1], parts[2]);

            if (currTask.isDateAllowed()) {
                tasks[taskCount++] = currTask;
                Ui.showTaskAddedMessage(tasks[taskCount - 1], taskCount);
            }
        } else {
            throw new VeronicaException("UHOH! Invalid format detected. Use: event <task> /from <start> /to <end>");
        }
    }

    /**
     * Finds and displays tasks containing the given keyword.
     *
     * @param input User input with the "find" command and keyword.
     * @throws VeronicaException If the keyword is empty.
     */
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

    /**
     * Saves the current tasks to storage and exits the program.
     */
    public void exitProgram() {
        storage.saveTasks(tasks, taskCount);
    }
}
