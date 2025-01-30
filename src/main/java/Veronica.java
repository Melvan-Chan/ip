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

        Scanner sc = new Scanner(System.in); // Set up to read user input
        String[] tasks = new String[100];    // Array to store user tasks
        int taskCount = 0;                   // Track number of tasks added

        while (true) {
            String userInput = sc.nextLine(); // Read user input
            if (userInput.equalsIgnoreCase("bye")) {
                System.out.println("Veronica: Bye. Hope to see you again soon! ");
                break;
            }
            else if (userInput.equalsIgnoreCase("list")) {
                System.out.println("____________________________________________________________");
                for (int i = 0; i < taskCount; ++i) {
                    System.out.println((i + 1) + ". " + tasks[i]);
                }
                System.out.println("____________________________________________________________");
            }
            else {
                if (taskCount < 100) {
                    tasks[taskCount++] = userInput;

                    System.out.println("____________________________________________________________");
                    System.out.println("Veronica: Added " + userInput + " to the list.");
                    System.out.println("____________________________________________________________");
                }
                else {
                    System.out.println("Veronica: Sorry, I'm unable to store more than 100 tasks!");
                }
            }
        }
        sc.close();
    }
}
