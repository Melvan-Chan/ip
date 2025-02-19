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
    public String listTasks() {
        return Ui.showList(tasks, taskCount);
    }

    /**
     * Marks a task as completed.
     *
     * @param input The user input containing the task number.
     */
    public String markTask(String input) throws VeronicaException {
        int taskIndex = Integer.parseInt(input.substring(5)) - 1;
        if (taskIndex >= 0 && taskIndex < taskCount) {
            tasks[taskIndex].markAsComplete();
            return Ui.showTaskMarkedMessage(tasks[taskIndex]);
        } else {
            return ("UHOH! Task number does not exist.");
        }
    }

    /**
     * Marks a task as incomplete.
     *
     * @param input The user input containing the task number.
     * @throws VeronicaException If the task number is invalid.
     */
    public String unmarkTask(String input) throws VeronicaException {
        int taskIndex = Integer.parseInt(input.substring(7)) - 1;
        if (taskIndex >= 0 && taskIndex < taskCount) {
            tasks[taskIndex].markAsIncomplete();
            return Ui.showTaskUnmarkedMessage(tasks[taskIndex]);
        } else {
            return ("UHOH! Task number does not exist.");
        }
    }

    /**
     * Removes a task or clears all tasks.
     *
     * @param input The user input specifying which task to remove.
     * @throws VeronicaException If the task number is invalid.
     */
    public String removeTask(String input) throws VeronicaException {
        String argument = input.substring(7).trim();
        if (argument.equalsIgnoreCase("all")) {
            taskCount = 0;
            tasks = new Task[Veronica.MAX_TASK_SIZE];
            return Ui.showTaskRemovedAllMessage();
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

                return Ui.showTaskRemovedMessage(removedTask, taskCount);
            } else {
                return ("UHOH! Task number does not exist.");
            }
        }
    }

    /**
     * Adds a new To-Do task to the list.
     *
     * @param input The user input containing the task description.
     * @throws VeronicaException If the description is empty.
     */
    public String addTodo(String input) throws VeronicaException {
        String taskDescription = input.substring(5).trim();
        if (taskDescription.isEmpty()) {
            return ("UHOH! Description can't be empty.");
        }
        tasks[taskCount++] = new ToDo(taskDescription);
        return Ui.showTaskAddedMessage(tasks[taskCount - 1], taskCount);
    }

    /**
     * Adds a new Deadline task to the list.
     *
     * @param input The user input containing the task description and due date.
     * @throws VeronicaException If the format is incorrect or missing required details.
     */
    public String addDeadline(String input) throws VeronicaException {
        String[] parts = input.substring(9).split(" /by ");
        if (parts.length == 2 && (new Deadline(parts[0], parts[1])).isDateAllowed()) {
            Deadline currTask = new Deadline(parts[0], parts[1]);
            tasks[taskCount++] = currTask;
            return Ui.showTaskAddedMessage(tasks[taskCount - 1], taskCount);
        } else {
            return "UHOH! Invalid format detected. Use: deadline <task> /by <date>";
        }
    }

    /**
     * Adds a new Event task to the list.
     *
     * @param input The user input containing the event description, start date, and end date.
     * @throws VeronicaException If the format is incorrect or missing required details.
     */
    public String addEvent(String input) throws VeronicaException {
        String[] parts = input.substring(6).split(" /from | /to ");
        if (parts.length == 3 && (new Event(parts[0], parts[1], parts[2])).isDateAllowed()) {
            Event currTask = new Event(parts[0], parts[1], parts[2]);
            tasks[taskCount++] = currTask;
            return Ui.showTaskAddedMessage(tasks[taskCount - 1], taskCount);
        } else {
            return ("UHOH! Invalid format detected. Use: event <task> /from <start> /to <end>");
        }
    }

    /**
     * Finds and displays tasks containing the given keyword.
     *
     * @param input User input with the "find" command and keyword.
     * @throws VeronicaException If the keyword is empty.
     */
    public String findTasks(String input) throws VeronicaException {
        String taskKeyword = input.substring(5).trim();
        if (taskKeyword.isEmpty()) {
            return ("UHOH! Keyword description can't be empty.");
        }

        List<Task> matchingTasks = new ArrayList<Task>();
        for (int i = 0; i < taskCount; i++) {
            if(tasks[i].getDescription().toLowerCase().contains(taskKeyword.toLowerCase())) {
                matchingTasks.add(tasks[i]);
            }
        }

        if (matchingTasks.isEmpty()) {
            return Ui.showNoMatchingTask(taskKeyword);
        } else {
            return Ui.showMatchingTask(matchingTasks, taskKeyword);
        }
    }

    /**
     * Saves the current tasks to storage and exits the program.
     */
    public String exitProgram() {
        storage.saveTasks(tasks, taskCount);
        return Ui.showGoodbyeMessage();
    }
}
