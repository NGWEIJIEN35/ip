package discitrack.task;

/**
 * Represents a task with an activity description and completion status.
 */
public class Task {
    private final String activity;
    private boolean isDone;

    /**
     * Creates a task with the given activity description.
     *
     * @param activity the activity represented by the task.
     */
    public Task(String activity) {
        this.activity = activity;
        this.isDone = false;
    }

    /**
     * Returns the activity description of this task.
     *
     * @return the activity description.
     */
    public String getActivity() {
        return this.activity;
    }

    /**
     * Returns whether this task has been marked as done.
     *
     * @return true if the task is done, otherwise false.
     */
    public boolean isDone() {
        return this.isDone;
    }

    /**
     * Marks this task as done.
     */
    public void markAsDone() {
        this.isDone = true;
    }

    /**
     * Marks this task as not done.
     */
    public void markAsUndone() {
        this.isDone = false;
    }

    /**
     * Returns the display symbol for this task's completion status.
     *
     * @return X if the task is done, otherwise a blank space.
     */
    public String getStatusIcon() {
        return this.isDone ? "X" : " ";
    }

    /**
     * Returns the display text for this task.
     *
     * @return the task formatted for display.
     */
    @Override
    public String toString() {
        return "[" + getStatusIcon() + "] " + this.activity;
    }
}
