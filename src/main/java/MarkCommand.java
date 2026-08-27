import java.io.IOException;

public class MarkCommand extends Command {
    private final int taskNumber;

    public MarkCommand(int taskNumber) {
        this.taskNumber = taskNumber;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws DisciTrackException, IOException {
        Task task = tasks.get(taskNumber);
        task.markAsDone();
        storage.save(tasks.asList());
        ui.showTaskMarked(task);
    }
}
