package discitrack.storage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import discitrack.task.Deadline;
import discitrack.task.Event;
import discitrack.task.Task;
import discitrack.task.Todo;

/**
 * Handles loading tasks from disk and saving tasks back to disk.
 */
public class Storage {
    private final String filePath;

    /**
     * Creates a storage manager for the given file path.
     *
     * @param filePath path to the task data file.
     */
    public Storage(String filePath) {
        this.filePath = filePath;
    }

    /**
     * Saves the given tasks to the configured data file.
     *
     * @param tasks the tasks to save.
     * @throws IOException if the file cannot be written.
     */
    public void save(List<Task> tasks) throws IOException {
        File dataFile = new File(filePath);
        File dataFolder = dataFile.getParentFile();

        if (dataFolder != null && !dataFolder.exists()) {
            dataFolder.mkdir();
        }

        FileWriter writer = new FileWriter(dataFile);

        for (Task task : tasks) {
            writer.write(taskToFileLine(task) + System.lineSeparator());
        }

        writer.close();
    }

    /**
     * Loads tasks from the configured data file.
     *
     * @return the tasks stored in the data file, or an empty list if the file does not exist.
     * @throws FileNotFoundException if the file cannot be opened for reading.
     */
    public List<Task> load() throws FileNotFoundException {
        List<Task> tasks = new ArrayList<>();
        File dataFile = new File(filePath);

        if (!dataFile.exists()) {
            return tasks;
        }

        Scanner fileScanner = new Scanner(dataFile);

        while (fileScanner.hasNextLine()) {
            tasks.add(parseTaskFromFileLine(fileScanner.nextLine()));
        }

        fileScanner.close();
        return tasks;
    }

    private Task parseTaskFromFileLine(String line) {
        String[] parts = line.split(" \\| ");
        String taskType = parts[0];
        String status = parts[1];
        Task task = createTask(taskType, parts);

        if (status.equals("1")) {
            task.markAsDone();
        }

        return task;
    }

    private Task createTask(String taskType, String[] parts) {
        if (taskType.equals("T")) {
            return new Todo(parts[2]);
        } else if (taskType.equals("D")) {
            return new Deadline(parts[2], LocalDate.parse(parts[3]));
        } else if (taskType.equals("E")) {
            return new Event(parts[2], LocalDate.parse(parts[3]),
                    LocalDate.parse(parts[4]));
        } else {
            throw new IllegalArgumentException(
                    "Unsupported task type in data file: " + taskType);
        }
    }

    /**
     * Converts a task into the text format used in the data file.
     *
     * @param task the task to convert.
     * @return the file line representing the task.
     */
    private String taskToFileLine(Task task) {
        String status = task.isDone() ? "1" : "0";

        if (task instanceof Todo) {
            return "T | " + status + " | " + task.getActivity();
        } else if (task instanceof Deadline) {
            Deadline deadline = (Deadline) task;
            return "D | " + status + " | " + deadline.getActivity() + " | " + deadline.getTime();
        } else if (task instanceof Event) {
            Event event = (Event) task;
            return "E | " + status + " | " + event.getActivity()
                    + " | " + event.getFrom() + " | " + event.getTo();
        } else {
            throw new IllegalArgumentException(
                    "Unsupported task type: " + task.getClass().getSimpleName());
        }
    }
}
