import java.io.BufferedWriter;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Storage {
    private final String filePath;
    private int taskCount = 0;
    private static final int MAX_TASK_SIZE = 100;
    private static final DateTimeFormatter INPUT_FORMAT = DateTimeFormatter.ofPattern("d/M/yyyy HHmm");
    private static final DateTimeFormatter OUTPUT_FORMAT = DateTimeFormatter.ofPattern("d MMM yyyy, h:mma");


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

    public int getTaskCount() {
        return this.taskCount;
    }

    // Save tasks to file
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

    // Load tasks from file
    public Task[] loadTasks() {
        Task[] tasks = new Task[MAX_TASK_SIZE]; // Fixed array size
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
    // Concert a line from file to a Task object
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
                    DateTimeFormatter correctFormat = DateTimeFormatter.ofPattern("d/M/yyyy HHmm");
                    String formattedDate = dateTime.format(correctFormat); // Convert to correct format
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
                    DateTimeFormatter correctFormat = DateTimeFormatter.ofPattern("d/M/yyyy HHmm");
                    String formattedFrom = fromDateTime.format(correctFormat);
                    String formattedTo = toDateTime.format(correctFormat);
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
