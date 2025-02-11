import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Event extends Task {
    protected LocalDateTime from;
    protected LocalDateTime to;

    public Event(String description, String from, String to) {
        super(description, TaskType.EVENT);
        try {
            this.from = LocalDateTime.parse(from, Storage.INPUT_FORMAT);
            this.to = LocalDateTime.parse(to, Storage.INPUT_FORMAT);
            this.dateAllowed = true;
        } catch (DateTimeParseException e) {
            System.out.println("     Invalid date format! Please use 'd/M/yyyy HHmm'. Example: 16/12/1991 1800");
            this.from = null;
            this.to = null;
            this.dateAllowed = false;
        }
    }

    @Override
    public String toString() {
        String formattedFrom = (from != null) ? formatDateWithSuffix(from) : "Invalid date";
        String formattedTo = (to != null) ? formatDateWithSuffix(to) : "Invalid date";
        return super.toString() + " | [from: " + formattedFrom + " | to: " + formattedTo + "]";
    }

    @Override
    public String toFileString() {
        return super.toString() + " | [from: " + from + " | to: " + to + "]";
    }

    private static String formatDateWithSuffix(LocalDateTime dateTime) {
        int day = dateTime.getDayOfMonth();
        String suffix = getDaySuffix(day);
        return day + suffix + " of " + dateTime.format(Storage.OUTPUT_FORMAT);
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
