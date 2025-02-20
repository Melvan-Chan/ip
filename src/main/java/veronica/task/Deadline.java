package veronica.task;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import veronica.misc.Storage;

public class Deadline extends Task {
    protected LocalDateTime by;

    public Deadline(String description, String by) {
        super(description, TaskType.DEADLINE);
        try {
            this.by = LocalDateTime.parse(by, Storage.INPUT_FORMAT);
            this.isDateAllowed = true;
        } catch (DateTimeParseException e) {
            this.isDateAllowed = false;
            System.out.println("     Invalid date format! Please use 'd/M/yyyy HHmm'. Example: 16/12/1991 1800");
            this.by = null;
        }
    }

    public LocalDateTime getDeadline() {
        return this.by;
    }

    @Override
    public int compareTo(Task other) {
        // First, ensure correct ordering of types (ToDo > Deadline > Event)
        int typeComparison = super.compareTo(other);
        if (typeComparison != 0) {
            return typeComparison;
        }

        // If both are Deadlines, compare by dueDate
        if (other instanceof Deadline) {
            Deadline otherDeadline = (Deadline) other;
            return this.getDeadline().compareTo(otherDeadline.getDeadline());
        }
        return 0;
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

}
