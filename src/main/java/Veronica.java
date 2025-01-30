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
