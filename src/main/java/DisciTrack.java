import java.util.ArrayList;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;

public class DisciTrack {
    private static final String FILE_PATH = "data/discitrack.txt";

    private final Storage storage;
    private final TaskList tasks;
    private final Ui ui;

    public DisciTrack(String filePath) {
        ui = new Ui();
        storage = new Storage(filePath);
        tasks = new TaskList(loadTasks());
    }

    public static void main(String[] args) {
        new DisciTrack(FILE_PATH).run();
    }

    public void run() {
        ui.showGreeting();

        while (true) {
            String command = ui.readCommand();

            try {
                CommandType commandType = Parser.getCommandType(command);
                Parser.validateCommand(command, commandType);

                if (commandType == CommandType.BYE) {
                    ui.showBye();
                    break;
                } else if (commandType == CommandType.LIST) {
                    ui.showTaskList(tasks.asList());
                } else if (commandType == CommandType.MARK) {
                    markTask(command);
                } else if (commandType == CommandType.UNMARK) {
                    unmarkTask(command);
                } else if (commandType == CommandType.CHECKDATE) {
                    showTasksOnDate(command);
                } else if (commandType == CommandType.TODO) {
                    addTask(Parser.parseTodo(command));
                } else if (commandType == CommandType.DEADLINE) {
                    addTask(Parser.parseDeadline(command));
                } else if (commandType == CommandType.EVENT) {
                    addTask(Parser.parseEvent(command));
                } else if (commandType == CommandType.DELETE) {
                    deleteTask(command);
                }
            } catch (DisciTrackException e) {
                ui.showError(e.getMessage());
            } catch (IOException e) {
                ui.showSaveError();
            }
        }
    }

    private ArrayList<Task> loadTasks() {
        try {
            return new ArrayList<>(storage.load());
        } catch (FileNotFoundException e) {
            return new ArrayList<>();
        }
    }

    private void markTask(String command) throws IOException {
        int taskNumber = Parser.getTaskNumber(command, 5);
        Task task = tasks.get(taskNumber);
        task.markAsDone();
        storage.save(tasks.asList());
        ui.showTaskMarked(task);
    }

    private void unmarkTask(String command) throws IOException {
        int taskNumber = Parser.getTaskNumber(command, 7);
        Task task = tasks.get(taskNumber);
        task.markAsUndone();
        storage.save(tasks.asList());
        ui.showTaskUnmarked(task);
    }

    private void showTasksOnDate(String command) {
        LocalDate date = Parser.parseCheckDate(command);
        ui.showDateMatches(tasks.findTasksByDate(date));
    }

    private void addTask(Task task) throws IOException {
        tasks.add(task);
        storage.save(tasks.asList());
        ui.showTaskAdded(task, tasks.size());
    }

    private void deleteTask(String command) throws IOException {
        int taskNumber = Parser.getTaskNumber(command, 7);
        Task removedTask = tasks.delete(taskNumber);
        storage.save(tasks.asList());
        ui.showTaskDeleted(removedTask, tasks.size());
    }
}
