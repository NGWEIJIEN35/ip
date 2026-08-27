package discitrack.ui;

import discitrack.task.Task;
import java.util.List;
import java.util.Scanner;

/**
 * Handles all interactions between DisciTrack and the user.
 */
public class Ui {
    private static final String LINE = "____________________________________________________________";

    private final Scanner scanner;

    /**
     * Creates a UI that reads commands from standard input.
     */
    public Ui() {
        this.scanner = new Scanner(System.in);
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
                + "Hello! I am DisciTrack.\n"
                + "My job is to keep your discipline on track.\n"
                + "How can I help you?\n"
                + LINE;

        System.out.println(greeting);
    }

    /**
     * Reads the next command entered by the user.
     *
     * @return the command entered by the user
     */
    public String readCommand() {
        return scanner.nextLine();
    }

    /**
     * Displays the farewell message.
     */
    public void showBye() {
        showLine();
        System.out.println("Bye bye! Well done today, keep it up! Hope to see you again soon!");
        showLine();
    }

    /**
     * Displays all tasks in the task list.
     *
     * @param tasks the tasks to display
     */
    public void showTaskList(List<Task> tasks) {
        showLine();

        if (tasks.isEmpty()) {
            System.out.println("Congratulations! You have no tasks currently!");
        } else {
            for (int i = 0; i < tasks.size(); i++) {
                System.out.println((i + 1) + ". " + tasks.get(i));
            }
        }

        showLine();
    }

    /**
     * Displays a message for a task that has been marked as done.
     *
     * @param task the task that was marked
     */
    public void showTaskMarked(Task task) {
        showLine();
        System.out.println("Well done, I have marked this task as done!");
        System.out.println(task);
        showLine();
    }

    /**
     * Displays a message for a task that has been marked as not done.
     *
     * @param task the task that was unmarked
     */
    public void showTaskUnmarked(Task task) {
        showLine();
        System.out.println("I have marked this task as not done yet, try to finish soon!");
        System.out.println(task);
        showLine();
    }

    /**
     * Displays tasks that match a searched date.
     *
     * @param tasksMatchedDate tasks that match the searched date
     */
    public void showDateMatches(List<Task> tasksMatchedDate) {
        showLine();

        if (tasksMatchedDate.isEmpty()) {
            System.out.println("There is no tasks found on this date!");
        } else {
            for (Task task : tasksMatchedDate) {
                System.out.println(task);
            }
        }

        showLine();
    }

    /**
     * Displays a message for a newly added task.
     *
     * @param task the task that was added
     * @param taskCount the number of tasks after adding the task
     */
    public void showTaskAdded(Task task, int taskCount) {
        showLine();
        System.out.println("Alright! I have added this task:");
        System.out.println(task);
        System.out.println(String.format("Now you have %d tasks in the list.", taskCount));
        showLine();
    }

    /**
     * Displays a message for a deleted task.
     *
     * @param removedTask the task that was deleted
     * @param taskCount the number of tasks after deleting the task
     */
    public void showTaskDeleted(Task removedTask, int taskCount) {
        showLine();
        System.out.println("Alright! I have deleted this task.");
        System.out.println(removedTask);
        System.out.println(String.format("Now you have %d tasks in the list.", taskCount));
    }

    /**
     * Displays an error message.
     *
     * @param message the error message to display
     */
    public void showError(String message) {
        showLine();
        System.out.println(message);
        showLine();
    }

    /**
     * Displays an error message for a failed save operation.
     */
    public void showSaveError() {
        showError("UHOH! I could not save your tasks.");
    }

    private void showLine() {
        System.out.println(LINE);
    }
}
