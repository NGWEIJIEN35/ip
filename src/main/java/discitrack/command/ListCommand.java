package discitrack.command;

import discitrack.storage.Storage;
import discitrack.task.TaskList;
import discitrack.ui.Ui;

public class ListCommand extends Command {
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        ui.showTaskList(tasks.asList());
    }
}
