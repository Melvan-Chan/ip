import java.util.Scanner;

public class Veronica {
    public static void main(String[] args) {
        String greet = "____________________________________________________________\n"
                + " Hello! I'm Veronica. Tony Stark create me after Jarvis.\n"
                + " A little bit about me is that my name means 'she who brings the victory'.\n"
                + " What can I do for you?\n"
                + "____________________________________________________________\n";
        System.out.println(greet);

        Scanner sc = new Scanner(System.in); // Set up to read user input

        while (true) {
            String userInput = sc.nextLine(); // Read user input
            if (userInput.equalsIgnoreCase("bye")) {
                System.out.println("Veronica: Bye. Hope to see you again soon! ");
                break;
            }
            System.out.println("Veronica: " + userInput);
        }
        sc.close();
    }
}
