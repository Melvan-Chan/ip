package veronica.task;

public class ToDo extends Task {

    public ToDo(String description) {
        super(description, TaskType.TODO);
    }

    @Override
    public String toString() {
        return super.toString();
    }

    @Override
    public String toFileString() {
        return super.toString();
    }
}
