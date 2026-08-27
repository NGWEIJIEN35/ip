package discitrack.command;

import discitrack.storage.Storage;
import discitrack.task.Task;
import discitrack.task.TaskList;
import discitrack.ui.Ui;
import java.io.IOException;

/**
 * Adds a new task to the task list.
 */
public class AddCommand extends Command {
    private final Task task;

    /**
     * Creates an add command for the given task.
     *
     * @param task the task to add
     */
    public AddCommand(Task task) {
        this.task = task;
    }

    @Override
    /**
     * Adds the task, saves the updated list, and shows the added task.
     *
     * @param tasks the task list to add to
     * @param ui the UI used to show the result
     * @param storage the storage used to save the updated list
     * @throws IOException if saving the updated list fails
     */
    public void execute(TaskList tasks, Ui ui, Storage storage) throws IOException {
        tasks.add(task);
        storage.save(tasks.asList());
        ui.showTaskAdded(task, tasks.size());
    }
}
