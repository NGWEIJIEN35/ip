package discitrack.task;

/**
 * Represents a task with an activity description and completion status.
 */
public class Task {
    private String activity; //tasks that the user needs to finish
    private boolean status;  //whether it is done or not

    /**
     * Creates a task with the given activity description.
     *
     * @param activity the activity represented by the task
     */
    public Task(String activity) {
        this.activity = activity;
        this.status = false;
    }

    /**
     * Returns the activity description of this task.
     *
     * @return the activity description
     */
    public String getActivity() {
        return this.activity;
    }

    /**
     * Returns whether this task has been marked as done.
     *
     * @return true if the task is done, otherwise false
     */
    public boolean isDone() {
        return this.status;
    }
    /**
     * Marks this task as done.
     */
    public void markAsDone() {
        this.status = true;
    }

    /**
     * Marks this task as not done.
     */
    public void markAsUndone() {
        this.status = false;
    }

    /**
     * Returns the display symbol for this task's completion status.
     *
     * @return X if the task is done, otherwise a blank space
     */
    public String checkStatus() {   //to get symbols for checklist boxes
        return this.status ? "X" : " ";
    }

    @Override
    /**
     * Returns the display text for this task.
     *
     * @return the task formatted for display
     */
    public String toString() {
        return "[" + checkStatus() + "] " + this.activity;
    }
}
