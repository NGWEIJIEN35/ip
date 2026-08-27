import java.util.ArrayList;
import java.io.FileNotFoundException;
import java.io.IOException;

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
                Command parsedCommand = Parser.parse(command);
                parsedCommand.execute(tasks, ui, storage);

                if (parsedCommand.isExit()) {
                    break;
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
}
