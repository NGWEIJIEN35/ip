package discitrack;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TaskList {
    private final List<Task> tasks;

    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    public TaskList(List<Task> tasks) {
        this.tasks = tasks;
    }

    public void add(Task task) {
        tasks.add(task);
    }

    public Task get(int taskNumber) throws DisciTrackException {
        validateTaskNumber(taskNumber);
        return tasks.get(taskNumber - 1);
    }

    public Task delete(int taskNumber) throws DisciTrackException {
        validateTaskNumber(taskNumber);
        return tasks.remove(taskNumber - 1);
    }

    public int size() {
        return tasks.size();
    }

    public List<Task> asList() {
        return tasks;
    }

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

    private void validateTaskNumber(int taskNumber) throws DisciTrackException {
        if (taskNumber < 1 || taskNumber > tasks.size()) {
            throw new DisciTrackException("UHOH! Please enter a valid task number!");
        }
    }
}
