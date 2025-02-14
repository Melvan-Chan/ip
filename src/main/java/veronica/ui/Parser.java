package veronica.ui;

import veronica.task.TaskManager;
import veronica.main.VeronicaException;

public class Parser {
    private final TaskManager taskManager;
    private boolean isActive;

    public Parser() {
        this.taskManager = new TaskManager();
        isActive = true;
    }

    public boolean getIsActive() {
        return isActive;
    }

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
            case "find" -> {
                taskManager.findTasks(userInput);
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