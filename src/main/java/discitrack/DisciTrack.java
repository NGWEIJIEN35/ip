package discitrack;

import discitrack.command.Command;
import discitrack.exception.DisciTrackException;
import discitrack.parser.Parser;
import discitrack.storage.Storage;
import discitrack.task.Task;
import discitrack.task.TaskList;
import discitrack.ui.Ui;
import java.util.ArrayList;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Runs the DisciTrack chatbot application.
 */
public class DisciTrack {
    private static final String FILE_PATH = "data/discitrack.txt";

    private final Storage storage;
    private final TaskList tasks;
    private final Ui ui;

    /**
     * Creates a DisciTrack application that stores tasks at the given file path.
     *
     * @param filePath path to the file used for saving and loading tasks.
     */
    public DisciTrack(String filePath) {
        ui = new Ui();
        storage = new Storage(filePath);
        tasks = new TaskList(loadTasks());
    }

    /**
     * Starts the DisciTrack application.
     *
     * @param args command line arguments supplied to the program.
     */
    public static void main(String[] args) {
        new DisciTrack(FILE_PATH).run();
    }

    /**
     * Reads user commands until the user exits the application.
     */
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

    /**
     * Loads the saved tasks, or starts with an empty list if the data file does not exist.
     *
     * @return the tasks loaded from storage.
     */
    private ArrayList<Task> loadTasks() {
        try {
            return new ArrayList<>(storage.load());
        } catch (FileNotFoundException e) {
            return new ArrayList<>();
        }
    }
}
