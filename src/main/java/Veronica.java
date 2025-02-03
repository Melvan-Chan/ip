import java.util.Scanner;

public class Veronica {
    public static void main(String[] args) {
        String greet = """
                ____________________________________________________________
                 Hello! I'm Veronica. Tony Stark create me after Jarvis.
                 A little bit about me is that my name means 'she who brings the victory'.
                 What can I do for you?
                ____________________________________________________________
                """;
        System.out.println(greet);

        Scanner sc = new Scanner(System.in);    // Set up to read user input
        Task[] tasks = new Task[100];           // Task object to store user tasks
        int taskCount = 0;                      // Track number of tasks added

        while (true) {
            String userInput = "";
            if (sc.hasNextLine()) {
                userInput = sc.nextLine(); // Read user input
            } else {
                break;
            }
            if (userInput.equalsIgnoreCase("bye")) {
                System.out.println("Veronica: Bye. Hope to see you again soon! ");
                break;
            } else if (userInput.equalsIgnoreCase("list")) {
                System.out.println("____________________________________________________________");
                for (int i = 0; i < taskCount; ++i) {
                    System.out.println((i + 1) + ". " + (tasks[i]));
                }
                System.out.println("____________________________________________________________");
            } else if (userInput.startsWith("mark ")) {
                int taskIndex = Integer.parseInt(userInput.substring(5)) - 1;
                if (taskIndex >= 0 && taskIndex < taskCount) {
                    tasks[taskIndex].markAsComplete();
                    System.out.println("____________________________________________________________");
                    System.out.println("Veronica: Marking this task as completed!");
                    System.out.println(tasks[taskIndex]);
                    System.out.println("____________________________________________________________");
                } else {
                    System.out.println("____________________________________________________________");
                    System.out.println("Veronica: This task does not exist! Please try again.");
                    System.out.println("____________________________________________________________");
                }
            } else if (userInput.startsWith("unmark ")) {
                int taskIndex = Integer.parseInt(userInput.substring(7)) - 1;
                if (taskIndex >= 0 && taskIndex < taskCount) {
                    tasks[taskIndex].markAsIncomplete();
                    System.out.println("____________________________________________________________");
                    System.out.println("Veronica: Unmarking this task as incomplete!");
                    System.out.println(tasks[taskIndex]);
                    System.out.println("____________________________________________________________");
                } else {
                    System.out.println("____________________________________________________________");
                    System.out.println("Veronica: This task does not exist! Please try again.");
                    System.out.println("____________________________________________________________");
                }
            } else if (userInput.startsWith("remove ")) {
                int taskIndex = Integer.parseInt(userInput.substring(7)) - 1;
                System.out.println("____________________________________________________________");
                System.out.println("Veronica: Removing this task from the list!");
                System.out.println(tasks[taskIndex].getDescription());
                System.out.println("____________________________________________________________");

                for (int i = taskIndex; i < taskCount; ++i) {
                    tasks[i] = tasks[i + 1];
                }
                taskCount--;
            } else if (userInput.startsWith("todo ")) {
                String taskDescription = userInput.substring(5);

                tasks[taskCount++] = new ToDo(taskDescription);

                System.out.println("____________________________________________________________");
                System.out.println("Veronica: Alright, I've added this to the list.");
                System.out.println(tasks[taskCount - 1]);
                System.out.println("Now, you've got " + taskCount + " tasks in the list!");
                System.out.println("____________________________________________________________");

            } else if (userInput.startsWith("deadline ")) {
                String[] parts = userInput.substring(9).split(" /by ");

                if (parts.length == 2) {
                    tasks[taskCount++] = new Deadline(parts[0], parts[1]);
                    System.out.println("____________________________________________________________");
                    System.out.println("Veronica: Alright, I've added this to the list.");
                    System.out.println(tasks[taskCount - 1]);
                    System.out.println("Now, you've got " + taskCount + " tasks in the list!");
                    System.out.println("____________________________________________________________");
                } else {
                    System.out.println("____________________________________________________________");
                    System.out.println("Veronica: Invalid format. Use: deadline <task> /by <date>");
                    System.out.println("____________________________________________________________");
                }
            }
            else if (userInput.startsWith("event ")) {

                String[] parts = userInput.substring(6).split(" /from | /to ");

                if (parts.length == 3) {
                    tasks[taskCount++] = new Event(parts[0], parts[1], parts[2]);
                    System.out.println("____________________________________________________________");
                    System.out.println("Veronica: Alright, I've added this to the list.");
                    System.out.println(tasks[taskCount - 1]);
                    System.out.println("Now, you've got " + taskCount + " tasks in the list!");
                    System.out.println("____________________________________________________________");
                } else {
                    System.out.println("____________________________________________________________");
                    System.out.println("Veronica: Invalid format. Use: event <task> /from <start> /to <end>");
                    System.out.println("____________________________________________________________");
                }
            } else {
                if (taskCount < 100) {
                    tasks[taskCount++] = new Task(userInput);

                    System.out.println("____________________________________________________________");
                    System.out.println("Veronica: Added " + userInput + " to the list.");
                    System.out.println("____________________________________________________________");
                } else {
                    System.out.println("Veronica: Sorry, I'm unable to store more than 100 tasks!");
                }
            }
        }
        sc.close();
    }
}
