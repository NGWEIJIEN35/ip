import java.io.IOException;

public class UnmarkCommand extends Command {
    private final int taskNumber;

    public UnmarkCommand(int taskNumber) {
        this.taskNumber = taskNumber;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws DisciTrackException, IOException {
        Task task = tasks.get(taskNumber);
        task.markAsUndone();
        storage.save(tasks.asList());
        ui.showTaskUnmarked(task);
    }
}
