package discitrack.command;

import discitrack.exception.DisciTrackException;
import discitrack.storage.Storage;
import discitrack.task.Task;
import discitrack.task.TaskList;
import discitrack.ui.Ui;
import java.io.IOException;

/**
 * Marks an existing task as not done.
 */
public class UnmarkCommand extends Command {
    private final int taskNumber;

    /**
     * Creates an unmark command for the given task number.
     *
     * @param taskNumber the one-based number of the task to unmark
     */
    public UnmarkCommand(int taskNumber) {
        this.taskNumber = taskNumber;
    }

    @Override
    /**
     * Marks the task as not done, saves the updated list, and shows the unmarked task.
     *
     * @param tasks the task list containing the task
     * @param ui the UI used to show the result
     * @param storage the storage used to save the updated list
     * @throws DisciTrackException if the task number is invalid
     * @throws IOException if saving the updated list fails
     */
    public void execute(TaskList tasks, Ui ui, Storage storage) throws DisciTrackException, IOException {
        Task task = tasks.get(taskNumber);
        task.markAsUndone();
        storage.save(tasks.asList());
        ui.showTaskUnmarked(task);
    }
}
