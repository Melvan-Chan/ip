package veronica.main;

import veronica.ui.Parser;
import veronica.ui.Ui;

import java.util.Scanner;

public class Veronica {
    public static final int MAX_TASK_SIZE = 100;
    public static final String FILE_PATH = "data/veronica_tasks.txt";
    private static final Parser parser = new Parser();

    public static void main(String[] args) {
        Ui.showGreetMessage();

        Scanner sc = new Scanner(System.in);            // Set up to read user input
        while (sc.hasNextLine()) {
            parser.processUserCommands(sc.nextLine());  // Read user input
            if (!parser.getIsActive())
                break;
        }
        sc.close();
    }
}