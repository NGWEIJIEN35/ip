package discitrack.command;

import discitrack.storage.Storage;
import discitrack.task.Task;
import discitrack.task.TaskList;
import discitrack.ui.Ui;
import java.io.IOException;

public class AddCommand extends Command {
    private final Task task;

    public AddCommand(Task task) {
        this.task = task;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws IOException {
        tasks.add(task);
        storage.save(tasks.asList());
        ui.showTaskAdded(task, tasks.size());
    }
}
