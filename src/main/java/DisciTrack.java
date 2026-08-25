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

            try {
                CommandType commandType = getCommandType(command);
                handleCommand(command, commandType);

                if (commandType == CommandType.BYE) {
                    System.out.println(line);
                    System.out.println("Bye bye! Well done today, keep it up! Hope to see you again soon!");
                    System.out.println(line);
                    break;
                } else if (commandType == CommandType.LIST) {
                    System.out.println(line);

                    if(listOfTasks.isEmpty()) {
                        System.out.println("Congratulations! You have no tasks currently!");
                    } else {
                        for (int i = 0; i < listOfTasks.size(); i++) {
                            System.out.println((i + 1) + ". " + listOfTasks.get(i));
                        }
                    }

                    System.out.println(line);
                } else if  (commandType == CommandType.MARK){  //assume users follow command's format, could modify in future
                    String taskNumberString = command.substring(5);  //get the task user chose
                    int taskNumber = Integer.parseInt(taskNumberString);
                    Task task = listOfTasks.get(taskNumber - 1);
                    task.markAsDone();
                    System.out.println(line);
                    System.out.println("Well done, I have marked this task as done!");
                    System.out.println(task);
                    System.out.println(line);
                } else if (commandType == CommandType.UNMARK) {
                    String taskNumberString = command.substring(7);
                    int taskNumber = Integer.parseInt(taskNumberString);
                    Task task = listOfTasks.get(taskNumber - 1);
                    task.markAsUndone();
                    System.out.println(line);
                    System.out.println("I have marked this task as not done yet, try to finish soon!");
                    System.out.println(task);
                    System.out.println(line);
                } else if (commandType == CommandType.TODO)  {
                    ToDos todo = new ToDos(command.substring(5));
                    listOfTasks.add(todo);
                    System.out.println(line);
                    System.out.println("Alright! I have added this task:");
                    System.out.println(todo);
                    System.out.println(String.format("Now you have %d tasks in the list.", listOfTasks.size()));
                    System.out.println(line);
                } else if (commandType == CommandType.DEADLINE) {
                    String input = command.substring(9);
                    String[] parts = input.split(" /by ", 2);

                    String activity = parts[0];
                    String time = parts[1];
                    Deadlines deadline = new Deadlines(activity, time);
                    listOfTasks.add(deadline);
                    System.out.println(line);
                    System.out.println("Alright! I have added this task:");
                    System.out.println(deadline);
                    System.out.println(String.format("Now you have %d tasks in the list.", listOfTasks.size()));
                    System.out.println(line);
                } else if (commandType == CommandType.EVENT) {
                    String input = command.substring(6);
                    String[] fromSplit = input.split(" /from ", 2);
                    String activity = fromSplit[0];

                    String[] toSplit = fromSplit[1].split(" /to ", 2);
                    String from = toSplit[0];
                    String to = toSplit[1];
                    Events event  = new Events(activity, from, to);
                    listOfTasks.add(event);
                    System.out.println(line);
                    System.out.println("Alright! I have added this task:");
                    System.out.println(event);
                    System.out.println(String.format("Now you have %d tasks in the list.", listOfTasks.size()));
                    System.out.println(line);
                } else if (commandType == CommandType.DELETE)  {
                    String taskNumberString = command.substring(7);
                    int taskNumber = Integer.parseInt(taskNumberString);
                    Task removedTask = listOfTasks.remove(taskNumber - 1);
                    System.out.println(line);
                    System.out.println("Alright! I have deleted this task.");
                    System.out.println(removedTask);
                    System.out.println(String.format("Now you have %d tasks in the list.", listOfTasks.size()));
                }
            } catch (DisciTrackException e) {
                System.out.println(line);
                System.out.println(e.getMessage());
                System.out.println(line);
            }
        }
    }

    public static void handleCommand(String command, CommandType commandType) throws DisciTrackException {
        if (commandType == CommandType.BYE || commandType == CommandType.LIST) {
            return;
        } else if (commandType == CommandType.MARK
                || commandType == CommandType.UNMARK
                || commandType == CommandType.DELETE) {
            return;
        } else if (commandType == CommandType.TODO) {   //handle empty cases for different tasks
            String activity = command.substring(4).trim();

            if (activity.isEmpty()) {
                throw new DisciTrackException("UHOH! The activity of a todo cannot be empty!");
            }
        } else if (commandType == CommandType.DEADLINE) {
            String input = command.substring(8).trim();
            if (input.isEmpty()) {
                throw new DisciTrackException("UHOH! The activity of a deadline cannot be empty!");
            }

            if (!input.contains(" /by ")) {
                throw new DisciTrackException("UHOH! A deadline needs a /by time!");
            }

            String[] parts = input.split(" /by ", 2);
            String activity = parts[0].trim();
            String time = parts[1].trim();

            if (activity.isEmpty()) {
                throw new DisciTrackException("UHOH! The activity of a deadline cannot be empty!");
            }

            if (time.isEmpty()) {
                throw new DisciTrackException("UHOH! The time of a deadline cannot be empty!");
            }
        } else if (commandType == CommandType.EVENT) {
            String input = command.substring(5).trim();

            if (input.isEmpty()) {
                throw new DisciTrackException("UHOH! The activity of an event cannot be empty!");
            }

            if (!input.contains(" /from ") || !input.contains(" /to ")) {
                throw new DisciTrackException("UHOH! An event needs both /from and /to!");
            }

            String[] fromSplit = input.split(" /from ", 2);
            String activity = fromSplit[0].trim();

            String[] toSplit = fromSplit[1].split(" /to ", 2);
            String from = toSplit[0].trim();
            String to = toSplit[1].trim();

            if (activity.isEmpty()) {
                throw new DisciTrackException("UHOH! The activity of an event cannot be empty!");
            }

            if (from.isEmpty()) {
                throw new DisciTrackException("UHOH! The start time of an event cannot be empty!");
            }

            if (to.isEmpty()) {
                throw new DisciTrackException("UHOH! The end time of an event cannot be empty!");
            }
        }  else {
            throw new DisciTrackException("UHOH, I didn't know what you mean.");  //handle unknown commands
        }
    }

    public static CommandType getCommandType(String command) {
        String commandWord = command.split(" ", 2)[0];

        if (commandWord.equals("bye")) {
            return CommandType.BYE;
        } else if (commandWord.equals("list")) {
            return CommandType.LIST;
        } else if (commandWord.equals("mark")) {
            return CommandType.MARK;
        } else if (commandWord.equals("unmark")) {
            return CommandType.UNMARK;
        } else if (commandWord.equals("todo")) {
            return CommandType.TODO;
        } else if (commandWord.equals("deadline")) {
            return CommandType.DEADLINE;
        } else if (commandWord.equals("event")) {
            return CommandType.EVENT;
        } else if (commandWord.equals("delete")) {
            return CommandType.DELETE;
        } else {
            return CommandType.UNKNOWN;
        }
    }
}
