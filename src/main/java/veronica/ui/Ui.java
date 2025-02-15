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
    public static void showGreetMessage() {
        String greet = """
                _________________________________________________________________________________________________
                 Hello! I'm Veronica. Tony Stark create me after Jarvis.
                 A little bit about me is that my name means 'she who brings the victory'.
                
                 Here's my command list:
                 - todo <task>: Adds task to the list.
                 - deadline <task> /by <date>: Adds deadline to the list with a due date.
                 - event <task> /from <date> /to <date>: Adds event to the list with a start/end date.'
                    > Each date's format must be dd/MM/yyyy (Time) e.g 2/12/2019 1800
                 - list: List's all the tasks in the list.
                 - mark <no. of task>: Marks the task specified.
                 - unmark <no. of task>: Unmarks the task specified.
                 - remove <no. of task>: Removes the task specified.
                
                 What can I do for you?
                _________________________________________________________________________________________________
                """;
        System.out.println(greet);
    }

    /**
     * Displays a goodbye message when the user exits.
     */
    public static void showGoodbyeMessage() {
        System.out.println("Veronica: Bye. Hope to see you again soon!");
    }

    /**
     * Displays an error message.
     *
     * @param message The error message to be displayed.
     */
    public static void showErrorMessage(String message) {
        System.out.println("    UHOH! Error: " + message);
    }

        /**
         * Displays the list of tasks.
         *
         * @param tasks The array of tasks.
         * @param taskCount The number of tasks in the list.
         */
    public static void showList(Task[] tasks, int taskCount) {
        System.out.println("     _________________________________________________________________________________________________");
        if (taskCount == 0) {
            System.out.println("     Veronica: List is empty at the moment.");
        } else {
            for (int i = 0; i < taskCount; ++i) {
                System.out.println("     " + (i + 1) + ". " + (tasks[i]));
            }
        }
        System.out.println("     _________________________________________________________________________________________________");
    }

        /**
         * Displays a message when a task is added.
         *
         * @param task The task that was added.
         * @param taskCount The total number of tasks after adding.
         */
    public static void showTaskAddedMessage(Task task, int taskCount) {
        System.out.println("     _________________________________________________________________________________________________");
        System.out.println("     Veronica: Alright, I've added this to the list.");
        System.out.println("     " + task);
        System.out.println("     Veronica: Now, you've got " + taskCount + " tasks in the list!");
        System.out.println("     _________________________________________________________________________________________________");
    }

        /**
         * Displays a message when a task is removed.
         *
         * @param task      The task that was removed.
         * @param taskCount The total number of tasks remaining.
         */
    public static void showTaskRemovedMessage(Task task, int taskCount) {
        System.out.println("     _________________________________________________________________________________________________");
        System.out.println("     Veronica: Removed " + task);
        System.out.println("     Veronica: Now, you've got " + taskCount + " tasks left.");
        System.out.println("     _________________________________________________________________________________________________");
    }

        /**
         * Displays a message when all tasks are removed.
         */
    public static void showTaskRemovedAllMessage() {
        System.out.println("     _________________________________________________________________________________________________");
        System.out.println("     Veronica: Removed all the tasks in this list.");
        System.out.println("     _________________________________________________________________________________________________");
    }

        /**
         * Displays a message when a task is marked as completed.
         *
         * @param task The task that was marked as completed.
         */
    public static void showTaskMarkedMessage(Task task) {
        System.out.println("     _________________________________________________________________________________________________");
        System.out.println("     Veronica: Great job! Marking this task as completed!");
        System.out.println("     " + task);
        System.out.println("     _________________________________________________________________________________________________");
    }

        /**
         * Displays a message when a task is unmarked as incomplete.
         *
         * @param task The task that was unmarked.
         */
    public static void showTaskUnmarkedMessage(Task task) {
        System.out.println("     _________________________________________________________________________________________________");
        System.out.println("     Veronica: Alright! Marking this task as uncompleted!");
        System.out.println("     " + task);
        System.out.println("     _________________________________________________________________________________________________");
    }

    public static void showNoMatchingTask(String message) {
        System.out.println("     _________________________________________________________________________________________________");
        System.out.println("     Veronica: No such task found with the given keyword: '" + message + "'");
        System.out.println("     _________________________________________________________________________________________________");
    }

    public static void showMatchingTask(List<Task> matchingTasks, String message) {
        System.out.println("     _________________________________________________________________________________________________");
        System.out.println("     Veronica: Showing all task with the keyword: '" + message + "'");
        for (int i = 0; i < matchingTasks.size(); i++) {
            System.out.println("     " + (i + 1) + ". " + matchingTasks.get(i));
        }
        System.out.println("     _________________________________________________________________________________________________");
    }
}