import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Deadline extends Task {
    protected LocalDateTime by;
    private static final DateTimeFormatter INPUT_FORMAT = DateTimeFormatter.ofPattern("d/M/yyyy HHmm");
    private static final DateTimeFormatter OUTPUT_FORMAT = DateTimeFormatter.ofPattern("MMM yyyy, h:mma");

    public Deadline(String description, String by) {
        super(description, TaskType.DEADLINE);
        try {
            this.by = LocalDateTime.parse(by, INPUT_FORMAT);
        } catch (DateTimeParseException e) {
            System.out.println("     Invalid date format! Please use 'd/M/yyyy HHmm'. Example: 16/12/1991 1800");
            this.by = null;
        }
    }

    @Override
    public String toString() {
        String formattedDate = (by != null) ? formatDateWithSuffix(by) : "Invalid date";
        return super.toString() + " | [by: " + formattedDate + "]";
    }

    @Override
    public String toFileString() {
        return super.toString() + " | [by: " + by + "]";
    }

    private static String formatDateWithSuffix(LocalDateTime dateTime) {
        int day = dateTime.getDayOfMonth();
        String suffix = getDaySuffix(day);
        return day + suffix + " of " + dateTime.format(OUTPUT_FORMAT);
    }

    private static String getDaySuffix(int day) {
        if (day >= 11 && day <= 13) return "th";
        switch (day % 10) {
            case 1: return "st";
            case 2: return "nd";
            case 3: return "rd";
            default: return "th";
        }
    }
}
