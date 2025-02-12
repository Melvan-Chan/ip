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
            if (userInput.equalsIgnoreCase("bye")) {
                Ui.showGoodbyeMessage();
                this.isActive = false;
                taskManager.exitProgram();
            } else if (userInput.equalsIgnoreCase("list")) {
                taskManager.listTasks();
            } else if (userInput.startsWith("mark ")) {
                taskManager.markTask(userInput);
            } else if (userInput.startsWith("unmark ")) {
                taskManager.unmarkTask(userInput);
            } else if (userInput.startsWith("remove ")) {
                taskManager.removeTask(userInput);
            } else if (userInput.startsWith("todo ")) {
                taskManager.addTodo(userInput);
            } else if (userInput.startsWith("deadline ")) {
                taskManager.addDeadline(userInput);
            } else if (userInput.startsWith("event ")) {
                taskManager.addEvent(userInput);
            } else {
                throw new VeronicaException("UHOH! I'm sorry, but I've no idea what you mean! Please try again.");
            }
        } catch (VeronicaException e) {
            Ui.showErrorMessage(e.getMessage());
        }
    }
}