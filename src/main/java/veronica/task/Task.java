package veronica.task;

import veronica.misc.Storage;

import java.time.LocalDateTime;

public abstract class Task {
    protected String description;
    protected boolean isDone;
    protected TaskType type;
    protected boolean isDateAllowed;

    public Task(String description, TaskType type) {
        this.description = description;
        this.isDone = false;
        this.type = type;
        this.isDateAllowed = false;
    }

    public String getStatus() {
        return this.isDone ? "X" : " ";
    }

    public String getDescription() {
        return this.description;
    }

    public boolean isDateAllowed() { return this.isDateAllowed; }

    public void markAsComplete() {
        this.isDone = true;
    }

    public void markAsIncomplete() {
        this.isDone = false;
    }

    public static String formatDateWithSuffix(LocalDateTime dateTime) {
        int day = dateTime.getDayOfMonth();
        String suffix = getDaySuffix(day);
        return day + suffix + " of " + dateTime.format(Storage.OUTPUT_FORMAT);
    }

    public static String getDaySuffix(int day) {
        if (day >= 11 && day <= 13) return "th";
        return switch (day % 10) {
            case 1 -> "st";
            case 2 -> "nd";
            case 3 -> "rd";
            default -> "th";
        };
    }

    public String toString() {
        return "[" + type.name().charAt(0) + "] | [" + this.getStatus() + "] | " + this.description;
    }

    public abstract String toFileString();
}
