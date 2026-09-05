package discitrack;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import discitrack.command.Command;
import discitrack.exception.DisciTrackException;
import discitrack.parser.Parser;
import discitrack.storage.Storage;
import discitrack.task.Task;
import discitrack.task.TaskList;
import discitrack.ui.Ui;
/**
 * Runs the DisciTrack chatbot application.
 */
public class DisciTrack {
    private static final String FILE_PATH = "data/discitrack.txt";

    private final Storage storage;
    private final TaskList tasks;
    private final Ui ui;
    private boolean shouldExit;

    /**
     * Creates a DisciTrack application for use by the graphical user interface.
     */
    public DisciTrack() {
        this(FILE_PATH, false);
    }

    /**
     * Creates a DisciTrack application that stores tasks at the given file path.
     *
     * @param filePath path to the file used for saving and loading tasks.
     */
    public DisciTrack(String filePath) {
        this(filePath, true);
    }

    private DisciTrack(String filePath, boolean shouldPrintResponses) {
        ui = new Ui(shouldPrintResponses);
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
     * Returns the greeting shown when the graphical user interface opens.
     *
     * @return the DisciTrack greeting.
     */
    public String getGreeting() {
        return ui.getGreeting();
    }

    /**
     * Processes one user command and returns the response for the graphical interface.
     *
     * @param input the command entered by the user.
     * @return the response produced after processing the command.
     */
    public String getResponse(String input) {
        shouldExit = false;
        try {
            Command parsedCommand = Parser.parse(input);
            parsedCommand.execute(tasks, ui, storage);
            shouldExit = parsedCommand.isExit();
        } catch (DisciTrackException e) {
            ui.showError(e.getMessage());
        } catch (IOException e) {
            ui.showSaveError();
        }

        return ui.getLastResponse();
    }

    /**
     * Returns whether the latest graphical-interface command should close the application.
     *
     * @return true if the latest command was an exit command.
     */
    public boolean shouldExit() {
        return shouldExit;
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
