package veronica.ui;

import veronica.task.TaskManager;
import veronica.main.VeronicaException;

/**
 * Processes user commands and delegates actions to the TaskManager.
 */
public class Parser {
    private final TaskManager taskManager;
    private boolean isActive;

    /**
     * Constructs a Parser instance and initializes the TaskManager.
     */
    public Parser() {
        this.taskManager = new TaskManager();
        isActive = true;
    }

    /**
     * Returns whether the parser is still active.
     *
     * @return True if active, false if the program is exiting.
     */
    public boolean getIsActive() {
        return isActive;
    }

    /**
     * Processes user commands and executes corresponding task operations.
     *
     * @param userInput The command entered by the user.
     */
    public String processUserCommands(String userInput) {
        try {

            String[] parts = userInput.split(" ",2);
            String command = parts[0].toLowerCase();

            switch (command) {
            case "bye" -> {
                this.isActive = false;
                return taskManager.exitProgram();
            }
            case "list" -> {
                return taskManager.listTasks();
            }
            case "mark" -> {
                return taskManager.markTask(userInput);
            }
            case "unmark" -> {
                return taskManager.unmarkTask(userInput);
            }
            case "remove" -> {
                return taskManager.removeTask(userInput);
            }
            case "todo" -> {
                return taskManager.addTodo(userInput);
            }
            case "deadline" -> {
                return taskManager.addDeadline(userInput);
            }
            case "event" -> {
                return taskManager.addEvent(userInput);
            }
            case "find" -> {
                return taskManager.findTasks(userInput);
            }
            default -> {
                return ("UHOH! I'm sorry, but I've no idea what you mean! Please try again.");
            }
            }
        } catch (VeronicaException e) {
            return Ui.showErrorMessage(e.getMessage());
        }
    }
}