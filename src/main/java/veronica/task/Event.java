package veronica.task;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import veronica.misc.Storage;

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

}
