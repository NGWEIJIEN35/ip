package discitrack.ui;

import java.util.List;
import java.util.Scanner;

import discitrack.task.Task;

/**
 * Handles all interactions between DisciTrack and the user.
 */
public class Ui {
    private static final String LINE = "____________________________________________________________";
    private static final String GREETING = "Hi! I'm DisciTrack, your discipline coach.\n"
            + "What are we getting done today?\n"
            + "Type help or click Commands if you need examples.";
    private static final String HELP_MESSAGE = "Here are the commands you can use:\n\n"
            + "todo DESCRIPTION\n  Example: todo exercise\n\n"
            + "deadline DESCRIPTION /by yyyy-MM-dd\n  Example: deadline pay bills /by 2026-09-10\n\n"
            + "event DESCRIPTION /from yyyy-MM-dd /to yyyy-MM-dd\n"
            + "  Example: event holiday /from 2026-09-15 /to 2026-09-18\n\n"
            + "list\n  Shows all tasks.\n\n"
            + "mark NUMBER\n  Example: mark 1\n\n"
            + "unmark NUMBER\n  Example: unmark 1\n\n"
            + "delete NUMBER\n  Example: delete 1\n\n"
            + "find KEYWORD\n  Example: find exercise\n\n"
            + "checkdate yyyy-MM-dd\n  Shows deadlines and events occurring on that date.\n"
            + "  Example: checkdate 2026-09-10\n\n"
            + "help\n  Shows this command guide.\n\n"
            + "bye\n  Ends the conversation.";

    private final Scanner scanner;
    private final boolean shouldPrintResponses;
    private String lastResponse = "";

    /**
     * Creates a UI that reads commands from standard input.
     */
    public Ui() {
        this(true);
    }

    /**
     * Creates a UI that can optionally print responses to standard output.
     *
     * @param shouldPrintResponses whether responses should be printed to the terminal.
     */
    public Ui(boolean shouldPrintResponses) {
        this.scanner = new Scanner(System.in);
        this.shouldPrintResponses = shouldPrintResponses;
    }

    /**
     * Displays the welcome message.
     */
    public void showGreeting() {
        String greeting = LINE + "\n"
                + " ____    _             _   _____                 _       \n"
                + "|  _ \\  (_) ___   ___ (_) |_   _| _ __   __ _  ___ | | __ \n"
                + "| | | | | |/ __| / __|| |   | |  | '__| / _` |/ __|| |/ / \n"
                + "| |_| | | |\\__ \\| (__ | |   | |  | |   | (_| | (__ |   <  \n"
                + "|____/  |_||___/ \\___||_|   |_|  |_|    \\__,_|\\___||_|\\_\\ \n"
                + LINE + "\n"
                + GREETING + "\n"
                + LINE;

        lastResponse = GREETING;
        if (shouldPrintResponses) {
            System.out.println(greeting);
        }
    }

    /**
     * Reads the next command entered by the user.
     *
     * @return the command entered by the user.
     */
    public String readCommand() {
        return scanner.nextLine();
    }

    /**
     * Displays the farewell message.
     */
    public void showBye() {
        showResponse("Bye bye! Well done today, keep it up! Hope to see you again soon!");
    }

    /**
     * Displays the supported commands and examples.
     */
    public void showHelp() {
        showResponse(HELP_MESSAGE);
    }

    /**
     * Displays all tasks in the task list.
     *
     * @param tasks the tasks to display.
     */
    public void showTaskList(List<Task> tasks) {
        if (tasks.isEmpty()) {
            showResponse("Congratulations! You have no tasks currently!");
        } else {
            StringBuilder response = new StringBuilder();
            for (int i = 0; i < tasks.size(); i++) {
                response.append(i + 1).append(". ").append(tasks.get(i));
                if (i < tasks.size() - 1) {
                    response.append(System.lineSeparator());
                }
            }
            showResponse(response.toString());
        }
    }

    /**
     * Displays a message for a task that has been marked as done.
     *
     * @param task the task that was marked.
     */
    public void showTaskMarked(Task task) {
        showResponse(
                "Well done! You completed:",
                "",
                task.toString(),
                "",
                "Keep the momentum going! You can do it!");
    }

    /**
     * Displays a message for a task that has been marked as not done.
     *
     * @param task the task that was unmarked.
     */
    public void showTaskUnmarked(Task task) {
        showResponse(
                "I have marked this task as not done yet, try to finish soon!",
                task.toString());
    }

    /**
     * Displays tasks that match a searched date.
     *
     * @param tasksMatchedDate tasks that match the searched date.
     */
    public void showDateMatches(List<Task> tasksMatchedDate) {
        if (tasksMatchedDate.isEmpty()) {
            showResponse("There are no deadlines or events on this date.");
        } else {
            StringBuilder response = new StringBuilder("Here are your tasks for this date:\n");
            for (Task task : tasksMatchedDate) {
                response.append(task).append(System.lineSeparator());
            }
            showResponse(response.toString().stripTrailing());
        }
    }

    /**
     * Displays tasks that match a searched keyword.
     *
     * @param matchingTasks tasks that match the searched keyword.
     */
    public void showFoundTasks(List<Task> matchingTasks) {
        if (matchingTasks.isEmpty()) {
            showResponse("There are no matching tasks in your list.");
        } else {
            StringBuilder response = new StringBuilder("Here are the matching tasks in your list:\n");
            for (int i = 0; i < matchingTasks.size(); i++) {
                response.append(i + 1).append(".").append(matchingTasks.get(i));
                if (i < matchingTasks.size() - 1) {
                    response.append(System.lineSeparator());
                }
            }
            showResponse(response.toString());
        }
    }

    /**
     * Displays a message for a newly added task.
     *
     * @param task the task that was added.
     * @param taskCount the number of tasks after adding the task.
     */
    public void showTaskAdded(Task task, int taskCount) {
        String taskWord = taskCount == 1 ? "task" : "tasks";
        showResponse(
                "Alright! I've added this task:",
                "",
                task.toString(),
                String.format("You now have %d %s.", taskCount, taskWord),
                "",
                "Lock in! Try to finish as soon as possible!");
    }

    /**
     * Displays a message for a deleted task.
     *
     * @param removedTask the task that was deleted.
     * @param taskCount the number of tasks after deleting the task.
     */
    public void showTaskDeleted(Task removedTask, int taskCount) {
        showResponse(
                "Alright! I have deleted this task.",
                removedTask.toString(),
                String.format("Now you have %d tasks in the list.", taskCount));
    }

    /**
     * Displays an error message.
     *
     * @param message the error message to display.
     */
    public void showError(String message) {
        showResponse(message);
    }

    /**
     * Displays an error message for a failed save operation.
     */
    public void showSaveError() {
        showError("UHOH! I could not save your tasks.");
    }

    /**
     * Returns the latest response produced by this UI.
     *
     * @return the latest response text.
     */
    public String getLastResponse() {
        return lastResponse;
    }

    /**
     * Returns the welcome message used by both interfaces.
     *
     * @return the DisciTrack welcome message.
     */
    public String getGreeting() {
        return GREETING;
    }

    private void showResponse(String... responseLines) {
        String response = String.join(System.lineSeparator(), responseLines);
        lastResponse = response;
        if (shouldPrintResponses) {
            showLine();
            System.out.println(response);
            showLine();
        }
    }

    private void showLine() {
        System.out.println(LINE);
    }
}
