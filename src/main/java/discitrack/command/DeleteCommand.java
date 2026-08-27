package discitrack.command;

import discitrack.exception.DisciTrackException;
import discitrack.storage.Storage;
import discitrack.task.Task;
import discitrack.task.TaskList;
import discitrack.ui.Ui;
import java.io.IOException;

public class DeleteCommand extends Command {
    private final int taskNumber;

    public DeleteCommand(int taskNumber) {
        this.taskNumber = taskNumber;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws DisciTrackException, IOException {
        Task removedTask = tasks.delete(taskNumber);
        storage.save(tasks.asList());
        ui.showTaskDeleted(removedTask, tasks.size());
    }
}
