package discitrack.command;

import discitrack.storage.Storage;
import discitrack.task.TaskList;
import discitrack.ui.Ui;
import java.time.LocalDate;

public class CheckDateCommand extends Command {
    private final LocalDate date;

    public CheckDateCommand(LocalDate date) {
        this.date = date;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        ui.showDateMatches(tasks.findTasksByDate(date));
    }
}
