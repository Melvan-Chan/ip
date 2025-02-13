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
    public void processUserCommands(String userInput) {
        try {

            String[] parts = userInput.split(" ",2);
            String command = parts[0].toLowerCase();

            switch (command) {
            case "bye" -> {
                Ui.showGoodbyeMessage();
                this.isActive = false;
                taskManager.exitProgram();
            }
            case "list" -> {
                taskManager.listTasks();
            }
            case "mark" -> {
                taskManager.markTask(userInput);
            }
            case "unmark" -> {
                taskManager.unmarkTask(userInput);
            }
            case "remove" -> {
                taskManager.removeTask(userInput);
            }
            case "todo" -> {
                taskManager.addTodo(userInput);
            }
            case "deadline" -> {
                taskManager.addDeadline(userInput);
            }
            case "event" -> {
                taskManager.addEvent(userInput);
            }
            default -> {
                throw new VeronicaException("UHOH! I'm sorry, but I've no idea what you mean! Please try again.");
            }
            }
        } catch (VeronicaException e) {
            Ui.showErrorMessage(e.getMessage());
        }
    }
}