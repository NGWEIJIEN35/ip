package discitrack;

import java.util.List;
import java.util.Scanner;

public class Ui {
    private static final String LINE = "____________________________________________________________";

    private final Scanner scanner;

    public Ui() {
        this.scanner = new Scanner(System.in);
    }

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

    public String readCommand() {
        return scanner.nextLine();
    }

    public void showBye() {
        showLine();
        System.out.println("Bye bye! Well done today, keep it up! Hope to see you again soon!");
        showLine();
    }

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

    public void showTaskMarked(Task task) {
        showLine();
        System.out.println("Well done, I have marked this task as done!");
        System.out.println(task);
        showLine();
    }

    public void showTaskUnmarked(Task task) {
        showLine();
        System.out.println("I have marked this task as not done yet, try to finish soon!");
        System.out.println(task);
        showLine();
    }

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

    public void showTaskAdded(Task task, int taskCount) {
        showLine();
        System.out.println("Alright! I have added this task:");
        System.out.println(task);
        System.out.println(String.format("Now you have %d tasks in the list.", taskCount));
        showLine();
    }

    public void showTaskDeleted(Task removedTask, int taskCount) {
        showLine();
        System.out.println("Alright! I have deleted this task.");
        System.out.println(removedTask);
        System.out.println(String.format("Now you have %d tasks in the list.", taskCount));
    }

    public void showError(String message) {
        showLine();
        System.out.println(message);
        showLine();
    }

    public void showSaveError() {
        showError("UHOH! I could not save your tasks.");
    }

    private void showLine() {
        System.out.println(LINE);
    }
}
