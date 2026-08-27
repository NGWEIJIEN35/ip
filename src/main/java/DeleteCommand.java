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
