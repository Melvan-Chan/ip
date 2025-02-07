import java.util.List;
import java.util.Scanner;

public class Veronica {
    private static final String FILE_PATH = "data/veronica_tasks.txt";
    private static final Storage storage = new Storage(FILE_PATH);
    static Task[] tasks = new Task[100];           // Task object to store user tasks
    static int taskCount = 0;                      // Track number of tasks added


    public static void main(String[] args) {
        tasks = storage.loadTasks();    // Should load an array into tasks
        taskCount = storage.getTaskCount();
        greetUser();
        processUserCommands();
    }

    private static void greetUser() {
        String greet = """
                _________________________________________________________________________________________________
                 Hello! I'm Veronica. Tony Stark create me after Jarvis.
                 A little bit about me is that my name means 'she who brings the victory'.
                
                 Here's my command list:
                 - todo <task>: Add's task to the list.
                 - deadline <task> /by <date>: Add's deadline to the list with a due date.
                 - event <task> /from <start> /to <end>: Add's event to the list with a start/end date..'
                 - list: List's all the tasks in the list.
                 - mark <no. of task>: Marks the task specified.
                 - unmark <no. of task>: Unmarks the task specified.
                 - remove <no. of task>: Removes the task specified.
                
                 What can I do for you?
                _________________________________________________________________________________________________
                """;
        System.out.println(greet);
    }
    private static void processUserCommands() {
        Scanner sc = new Scanner(System.in);    // Set up to read user input

        while (true) {
            String userInput = "";
            if (sc.hasNextLine()) {
                userInput = sc.nextLine(); // Read user input
            } else {
                break;
            }
            try {
                if (userInput.equalsIgnoreCase("bye")) {
                    System.out.println("Veronica: Bye. Hope to see you again soon! ");
                    storage.saveTasks(tasks, taskCount);
                    break;
                } else if (userInput.equalsIgnoreCase("list")) {
                    System.out.println("     _________________________________________________________________________________________________");
                    if (taskCount == 0) {
                        System.out.println("     Veronica: List is empty at the moment.");
                    } else {
                        for (int i = 0; i < taskCount; ++i) {
                            System.out.println("     " + (i + 1) + ". " + (tasks[i]));
                        }
                    }
                    System.out.println("     _________________________________________________________________________________________________");
                } else if (userInput.startsWith("mark ")) {
                    try {
                        int taskIndex = Integer.parseInt(userInput.substring(5)) - 1;
                        if (taskIndex >= 0 && taskIndex < taskCount) {
                            tasks[taskIndex].markAsComplete();
                            System.out.println("     _________________________________________________________________________________________________");
                            System.out.println("     Veronica: Marking this task as completed!");
                            System.out.println("     " + tasks[taskIndex]);
                            System.out.println("     _________________________________________________________________________________________________");
                        } else {
                            throw new VeronicaException("UHOH! I'm afraid that the task number does not exist! Please try again.");
                        }
                    } catch (NumberFormatException e) {
                        throw new VeronicaException("UHOH! Please enter a valid number after 'done'.");
                    }
                } else if (userInput.startsWith("unmark ")) {
                    try {
                        int taskIndex = Integer.parseInt(userInput.substring(7)) - 1;
                        if (taskIndex >= 0 && taskIndex < taskCount) {
                            tasks[taskIndex].markAsIncomplete();
                            System.out.println("     _________________________________________________________________________________________________");
                            System.out.println("     Veronica: Unmarking this task as incomplete!");
                            System.out.println("     " + tasks[taskIndex]);
                            System.out.println("     _________________________________________________________________________________________________");
                        } else {
                            System.out.println("     _________________________________________________________________________________________________");
                            System.out.println("     Veronica: This task does not exist! Please try again.");
                            System.out.println("     _________________________________________________________________________________________________");
                        }
                    } catch (NumberFormatException e) {
                        throw new VeronicaException("UHOH! I'm afraid that the task number does not exist! Please try again.");
                    }
                } else if (userInput.startsWith("remove ")) { // Remove task
                    try {
                        int taskIndex = Integer.parseInt(userInput.substring(7)) - 1;
                        if (taskIndex >= 0 && taskIndex < taskCount) {
                            System.out.println("     _________________________________________________________________________________________________");
                            System.out.println("     Veronica: Removed " + tasks[taskIndex]);
                            for (int i = taskIndex; i < taskCount - 1; i++) {
                                tasks[i] = tasks[i + 1];
                            }
                            taskCount--;
                            System.out.println("     Veronica: Now, you've got " + taskCount + " tasks in the list!");
                            System.out.println("     _________________________________________________________________________________________________");

                        } else {
                            throw new VeronicaException("UHOH! Task number does not exist.");
                        }
                    } catch (NumberFormatException e) {
                        throw new VeronicaException("UHOH! Please enter a valid number after 'remove'.");
                    }
                } else if (userInput.startsWith("todo ")) {
                    String taskDescription = userInput.substring(5).trim();
                    if (taskDescription.isEmpty()) {
                        throw new VeronicaException("UHOH! Description can't be empty! Please try again.");
                    }
                    tasks[taskCount++] = new ToDo(taskDescription);

                    System.out.println("     _________________________________________________________________________________________________");
                    System.out.println("     Veronica: Alright, I've added this to the list.");
                    System.out.println("     " + tasks[taskCount - 1]);
                    System.out.println("     Veronica: Now, you've got " + taskCount + " tasks in the list!");
                    System.out.println("     _________________________________________________________________________________________________");

                } else if (userInput.startsWith("deadline ")) {
                    String[] parts = userInput.substring(9).split(" /by ");

                    if (parts.length == 2) {
                        tasks[taskCount++] = new Deadline(parts[0], parts[1]);
                        System.out.println("     _________________________________________________________________________________________________");
                        System.out.println("     Veronica: Alright, I've added this to the list.");
                        System.out.println("     " + tasks[taskCount - 1]);
                        System.out.println("     Veronica: Now, you've got " + taskCount + " tasks in the list!");
                        System.out.println("     _________________________________________________________________________________________________");
                    } else {
                        throw new VeronicaException("UHOH! Invalid format detected. Use: deadline <task> /by <date>");
                    }
                }
                else if (userInput.startsWith("event ")) {

                    String[] parts = userInput.substring(6).split(" /from | /to ");

                    if (parts.length == 3) {
                        tasks[taskCount++] = new Event(parts[0], parts[1], parts[2]);
                        System.out.println("     _________________________________________________________________________________________________");
                        System.out.println("     Veronica: Alright, I've added this to the list.");
                        System.out.println("     " + tasks[taskCount - 1]);
                        System.out.println("     Veronica: Now, you've got " + taskCount + " tasks in the list!");
                        System.out.println("     _________________________________________________________________________________________________");
                    } else {
                        throw new VeronicaException("UHOH! Invalid format detected. Use: event <task> /from <start> /to <end>");
                    }
                } else {
                    throw new VeronicaException("UHOH! I'm sorry, but I've no idea what you mean! Please try again.");
                }
            } catch (VeronicaException errorMsg) {
                System.out.println("     _________________________________________________________________________________________________");
                System.out.println("     Veronica: " + errorMsg.getMessage());
                System.out.println("     _________________________________________________________________________________________________");
            }
        }
        sc.close();
    }

    private static void listTasks() {

    }

    private static void markTask(String input) throws VeronicaException {

    }

    private static void unmarkTask(String input) throws VeronicaException {

    }

    private static void removeTask(String input) throws VeronicaException {

    }

    private static void addTodo(String input) throws VeronicaException {

    }

    private static void addDeadline(String input) throws VeronicaException {

    }

    private static void addEvent(String input) throws VeronicaException {

    }

    private static void saveTasksToFile() {

    }
}
