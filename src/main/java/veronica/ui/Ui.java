package veronica.ui;

import veronica.task.Task;

import java.util.List;

    /**
     * Handles user interface interactions and displays messages for Veronica.
     */

public class Ui {

    /**
     * Displays a greeting message with available commands.
     */
    public static String showGreetMessage() {
        return """
                ______________________________________________
                 Hello! I'm Veronica. Tony Stark create me after Jarvis.
                 A little bit about me is that my name means 'she who brings the victory'.
                
                 Here's my command list:
                 - todo <task>: Adds task to the list.
                 - deadline <task> /by <date>: Adds deadline to the list with a due date.
                 - event <task> /from <date> /to <date>: Adds event to the list with a start/end date.'
                    > Each date's format must be <dd/MM/yyyy> <time> e.g 16/12/1991 1800
                 - list: List's all the tasks in the list.
                 - mark <no. of task>: Marks the task specified.
                 - unmark <no. of task>: Unmarks the task specified.
                 - remove <no. of task>: Removes the task specified.
                
                 What can I do for you?
                ______________________________________________
                """;
    }

    /**
     * Displays a goodbye message when the user exits.
     */
    public static String showGoodbyeMessage() {
        return "Bye. Hope to see you again soon!";
    }

    /**
     * Displays an error message.
     * <p>
     * The error message to be displayed.
     */
    public static String showErrorMessage(String... messages) {
        return "UHOH! Error: " + String.join(" ", messages);
    }


    /**
     * Displays the list of tasks.
     *
     * @param tasks The array of tasks.
     * @param taskCount The number of tasks in the list.
     */
    public static String showList(Task[] tasks, int taskCount) {
        String output = "List of current tasks\n";
        output += ("______________________________________________\n");
        if (taskCount == 0) {
            output += ("     List is empty at the moment.\n");
        } else {
            StringBuilder listBuilder = new StringBuilder(output);
            for (int i = 0; i < taskCount; ++i) {
                listBuilder.append("     ").append(i + 1).append(". ").append(tasks[i]).append("\n");
            }
            output = listBuilder.toString();
        }
        output += ("______________________________________________");
        return output;
    }

    /**
     * Displays a message when a task is added.
     *
     * @param task The task that was added.
     * @param taskCount The total number of tasks after adding.
     */
    public static String showTaskAddedMessage(Task task, int taskCount) {
        String output = "\n";
        output += ("______________________________________________\n");
        output += ("     Alright, I've added this to the list.\n");
        output += ("     " + task + "\n");
        output += ("     Now, you've got " + taskCount + " tasks in the list!\n");
        output += ("______________________________________________\n");
        return output;
    }

    /**
     * Displays a message when a task is removed.
     *
     * @param task      The task that was removed.
     * @param taskCount The total number of tasks remaining.
     */
    public static String showTaskRemovedMessage(Task task, int taskCount) {
        String output = "\n";
        output += ("______________________________________________\n");
        output += ("     Removed " + task + "\n");
        output += ("     Now, you've got " + taskCount + " tasks left.\n");
        output += ("______________________________________________\n");
        return output;
    }

        /**
         * Displays a message when all tasks are removed.
         */
    public static String showTaskRemovedAllMessage() {
        String output = "\n";
        output += ("______________________________________________\n");
        output += ("     Removed all the tasks in this list.\n");
        output += ("______________________________________________\n");
        return output;
    }

        /**
         * Displays a message when a task is marked as completed.
         *
         * @param task The task that was marked as completed.
         */
    public static String showTaskMarkedMessage(Task task) {
        String output = "\n";
        output += ("______________________________________________\n");
        output += ("     Great job! Marking this task as completed!\n");
        output += ("     " + task + "\n");
        output += ("______________________________________________\n");
        return output;
    }

        /**
         * Displays a message when a task is unmarked as incomplete.
         *
         * @param task The task that was unmarked.
         */
    public static String showTaskUnmarkedMessage(Task task) {
        String output = "\n";
        output += ("______________________________________________\n");
        output += ("     Alright! Marking this task as uncompleted!\n");
        output += ("     " + task + "\n");
        output += ("______________________________________________\n");
        return output;
    }

    public static String showNoMatchingTask(String message) {
        String output = "\n";
        output += ("______________________________________________\n");
        output += ("     No such task found with the given keyword: '" + message + "'\n");
        output += ("______________________________________________\n");
        return output;
    }

    public static String showMatchingTask(List<Task> matchingTasks, String message) {
        StringBuilder output = new StringBuilder("\n");
        output.append("______________________________________________\n");
        output.append("     Showing all task with the keyword: '").append(message).append("'\n");
        for (int i = 0; i < matchingTasks.size(); i++) {
            output.append("     ").append(i + 1).append(". ").append(matchingTasks.get(i)).append("\n");
        }
        output.append("______________________________________________\n");
        return output.toString();
    }
}