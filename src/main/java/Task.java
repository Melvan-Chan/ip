public class Task {
    protected String description;
    protected boolean isDone;
    protected TaskType type;

    public Task(String description, TaskType type) {
        this.description = description;
        this.isDone = false;
        this.type = type;
    }

    public String getStatus() {
        return this.isDone ? "X" : " ";
    }

    public String getDescription() {
        return this.description;
    }

    public void markAsComplete() {
        this.isDone = true;
    }

    public void markAsIncomplete() {
        this.isDone = false;
    }

    public String toString() {
        return "[" + type.name().charAt(0) + "][" + this.getStatus() + "] " + this.description;
    }
}
