public class Task {
    protected String description;
    protected boolean isDone;

    public Task(String description) {
        this.description = description;
        this.isDone = false;
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
        return "[" + this.getStatus() + "] " + this.description;
    }
}
