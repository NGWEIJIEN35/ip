package discitrack.command;

import discitrack.exception.DisciTrackException;
import discitrack.storage.Storage;
import discitrack.task.TaskList;
import discitrack.ui.Ui;
import java.io.IOException;

public abstract class Command {
    public abstract void execute(TaskList tasks, Ui ui, Storage storage) throws DisciTrackException, IOException;

    public boolean isExit() {
        return false;
    }
}
