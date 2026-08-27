package discitrack.task;

import discitrack.exception.DisciTrackException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Stores the user's tasks and provides operations on the task list.
 */
public class TaskList {
    private final List<Task> tasks;

    /**
     * Creates an empty task list.
     */
    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    /**
     * Creates a task list using the given tasks.
     *
     * @param tasks the initial tasks in the list
     */
    public TaskList(List<Task> tasks) {
        this.tasks = tasks;
    }

    /**
     * Adds a task to the end of the list.
     *
     * @param task the task to add
     */
    public void add(Task task) {
        tasks.add(task);
    }

    /**
     * Gets the task at the given one-based task number.
     *
     * @param taskNumber the one-based number of the task to get
     * @return the task at the given task number
     * @throws DisciTrackException if the task number is outside the list
     */
    public Task get(int taskNumber) throws DisciTrackException {
        validateTaskNumber(taskNumber);
        return tasks.get(taskNumber - 1);
    }

    /**
     * Deletes the task at the given one-based task number.
     *
     * @param taskNumber the one-based number of the task to delete
     * @return the removed task
     * @throws DisciTrackException if the task number is outside the list
     */
    public Task delete(int taskNumber) throws DisciTrackException {
        validateTaskNumber(taskNumber);
        return tasks.remove(taskNumber - 1);
    }

    /**
     * Returns the number of tasks in the list.
     *
     * @return the task count
     */
    public int size() {
        return tasks.size();
    }

    /**
     * Returns the tasks as a list.
     *
     * @return the underlying list of tasks
     */
    public List<Task> asList() {
        return tasks;
    }

    /**
     * Finds tasks that are relevant to the given date.
     *
     * @param date the date to search for
     * @return tasks with a deadline on the date or an event boundary on the date
     */
    public List<Task> findTasksByDate(LocalDate date) {
        List<Task> tasksMatchedDate = new ArrayList<>();

        for (Task task : tasks) {
            if (task instanceof Deadlines && ((Deadlines) task).getTime().equals(date)) {
                tasksMatchedDate.add(task);
            }

            if (task instanceof Events) {
                Events event = (Events) task;
                if (event.getFrom().equals(date) || event.getTo().equals(date)) {
                    tasksMatchedDate.add(task);
                }
            }
        }

        return tasksMatchedDate;
    }

    /**
     * Checks that a one-based task number refers to an existing task.
     *
     * @param taskNumber the task number to validate
     * @throws DisciTrackException if the task number is outside the list
     */
    private void validateTaskNumber(int taskNumber) throws DisciTrackException {
        if (taskNumber < 1 || taskNumber > tasks.size()) {
            throw new DisciTrackException("UHOH! Please enter a valid task number!");
        }
    }
}
