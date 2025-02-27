package veronica.misc;

import veronica.task.Deadline;
import veronica.task.Event;
import veronica.task.Task;
import veronica.task.ToDo;
import veronica.main.Veronica;

import java.io.BufferedWriter;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Handles loading and saving of tasks to a file for persistence.
 */
public class Storage {
    private final String filePath;
    private int taskCount = 0;
    public static final DateTimeFormatter INPUT_FORMAT = DateTimeFormatter.ofPattern("d/M/yyyy HHmm");
    public static final DateTimeFormatter OUTPUT_FORMAT = DateTimeFormatter.ofPattern("d MMM yyyy, h:mma");

    /**
     * Constructs a Storage instance with the specified file path.
     * If the file does not exist, it creates a new file.
     *
     * @param filePath The file path where tasks are stored.
     */
    public Storage(String filePath) {
        this.filePath = filePath;

        File file = new File(this.filePath);
        if (!file.exists()) {
            try {
                file.getParentFile().mkdirs();      // Ensure "data" folder exists
                file.createNewFile();               // Create file if not present
            } catch (Exception e) {
                System.out.println("Error while creating file: " + e.getMessage());
            }
        }
    }

    /**
     * Returns the number of tasks currently stored.
     *
     * @return The count of tasks.
     */
    public int getTaskCount() {
        return this.taskCount;
    }

    /**
     * Saves the given tasks to a file.
     *
     * @param tasks     The array of tasks to be saved.
     * @param taskCount The number of tasks to be saved.
     */
    public void saveTasks(Task[] tasks, int taskCount) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(this.filePath))) {
            for (int i = 0; i < taskCount; i++) {
                writer.write(tasks[i].toFileString());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving tasks: " + e.getMessage());
        }
    }

    /**
     * Loads tasks from a file and returns them as an array.
     *
     * @return An array of tasks loaded from the file.
     */
    public Task[] loadTasks() {
        Task[] tasks = new Task[Veronica.MAX_TASK_SIZE]; // Fixed array size
        taskCount = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader(this.filePath))) {
            String line;
            while ((line = reader.readLine()) != null && taskCount < 100) {
                Task task = parseTask(line);
                if (task != null) {
                    tasks[taskCount++] = task;
                }
            }
        } catch (IOException e) {
            System.out.println("Error loading tasks: " + e.getMessage());
        }
        return tasks;
    }

    /**
     * Parses a line from the file and converts it into a Task object.
     *
     * @param line The line representing a task.
     * @return The corresponding Task object, or null if the format is invalid.
     */
    private Task parseTask(String line) {
        String[] parts = line.split(" \\| "); // Split based on " | "
        if (parts.length < 3) {
            System.out.println("Invalid task format: " + line);
            return null;
        }

        String type = parts[0]; // [T], [D], or [E]
        boolean isDone = parts[1].equals("[X]");
        String description = parts[2];

        switch (type) {
            case "[T]":
                ToDo todo = new ToDo(description);
                if (isDone) {
                    todo.markAsComplete();
                }
                return todo;

            case "[D]":
                if (parts.length < 4) return null; // Ensure deadline has date
                try {
                    String cleanDate = parts[3].replace("[by: ", "").replace("]", "").trim(); // Remove unwanted characters
                    LocalDateTime dateTime = LocalDateTime.parse(cleanDate);
                    String formattedDate = dateTime.format(INPUT_FORMAT); // Convert to correct format
                    Deadline deadline = new Deadline(description, formattedDate);
                    if (isDone) {
                        deadline.markAsComplete();
                    }
                    return deadline;
                } catch (DateTimeParseException e) {
                    System.out.println("Invalid date format in saved data: " + parts[3]);
                    return null;
                }

            case "[E]":
                if (parts.length < 5) return null; // Ensure event has start & end time
                try {
                    String cleanFrom = parts[3].replace("[from: ", ""); // Remove unwanted characters
                    String cleanTo = parts[4].replace("to: ", "").replace("]", "").trim();
                    LocalDateTime fromDateTime = LocalDateTime.parse(cleanFrom);
                    LocalDateTime toDateTime = LocalDateTime.parse(cleanTo);
                    String formattedFrom = fromDateTime.format(INPUT_FORMAT);
                    String formattedTo = toDateTime.format(INPUT_FORMAT);
                    Event event = new Event(description, formattedFrom, formattedTo);
                    if (isDone) {
                        event.markAsComplete();
                    }
                    return event;
                } catch (DateTimeParseException e) {
                    System.out.println("Invalid event date format in saved data: " + parts[3] + " to " + parts[4]);
                    return null;
                }

            default:
                System.out.println("Unknown task type: " + type);
                return null;
        }
    }
}
