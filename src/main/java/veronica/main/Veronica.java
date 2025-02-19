package veronica.main;

import veronica.ui.Parser;

import java.util.Scanner;

public class Veronica {
    public static final int MAX_TASK_SIZE = 100;
    public static final String FILE_PATH = "data/veronica_tasks.txt";
    private static final Parser parser = new Parser();

    public static void main(String[] args) {

    }

    /**
     * Generates a response for the user's chat message.
     */
    public static String getResponse(String input) {
        return "Veronica: " + parser.processUserCommands(input);
    }
}