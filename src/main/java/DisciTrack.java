import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;

public class DisciTrack {
    public static void main(String[] args) {
        String line = "____________________________________________________________";
        String greeting = line + "\n"
                + " ____    _             _   _____                 _       \n"
                + "|  _ \\  (_) ___   ___ (_) |_   _| _ __   __ _  ___ | | __ \n"
                + "| | | | | |/ __| / __|| |   | |  | '__| / _` |/ __|| |/ / \n"
                + "| |_| | | |\\__ \\| (__ | |   | |  | |   | (_| | (__ |   <  \n"
                + "|____/  |_||___/ \\___||_|   |_|  |_|    \\__,_|\\___||_|\\_\\ \n"
                + line + "\n"
                + "Hello! I am DisciTrack.\n"
                + "My job is to keep your discipline on track.\n"
                + "How can I help you?\n"
                + line;

        System.out.println(greeting);

        Scanner scanner = new Scanner(System.in); //to receive users input

        List<Task> listOfTasks = new ArrayList<>();

        while (true) {
            String command = scanner.nextLine();
            if (command.equals("bye")) {
                System.out.println(line);
                System.out.println("Bye bye! Well done today, keep it up! Hope to see you again soon!");
                System.out.println(line);
                break;
            } else if (command.equals("list")) {
                System.out.println(line);

                for (int i = 0; i < listOfTasks.size(); i++) {
                    System.out.println((i + 1) + ". " + listOfTasks.get(i));
                }

                System.out.println(line);
            } else if (command.startsWith("mark ")) {  //assume users follow command's format, could modify in future
                String taskNumberString = command.substring(5);  //get the task user chose
                int taskNumber = Integer.parseInt(taskNumberString);
                Task task = listOfTasks.get(taskNumber - 1);
                task.markAsDone();
                System.out.println(line);
                System.out.println("Well done, I have marked this task as done!");
                System.out.println(task);
                System.out.println(line);
            } else if (command.startsWith("unmark ")) {
                String taskNumberString = command.substring(7);
                int taskNumber = Integer.parseInt(taskNumberString);
                Task task = listOfTasks.get(taskNumber - 1);
                task.markAsUndone();
                System.out.println(line);
                System.out.println("I have marked this task as not done yet, try to finish soon!");
                System.out.println(task);
                System.out.println(line);
            } else {
                listOfTasks.add(new Task(command));
                System.out.println(line);
                System.out.println("I have added: " + command);
                System.out.println(line);
            }
        }
    }
}
