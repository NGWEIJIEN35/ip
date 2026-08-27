package discitrack.command;

import discitrack.storage.Storage;
import discitrack.task.TaskList;
import discitrack.ui.Ui;

public class ByeCommand extends Command {
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        ui.showBye();
    }

    @Override
    public boolean isExit() {
        return true;
    }
}
