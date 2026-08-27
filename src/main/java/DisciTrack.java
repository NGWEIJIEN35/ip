import java.time.format.DateTimeParseException;
import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

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

        try {
            listOfTasks = loadTasksFromData();
        } catch (FileNotFoundException e) {
            listOfTasks = new ArrayList<>();
        }

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
                    saveTasksToData(listOfTasks);
                    System.out.println(line);
                    System.out.println("Well done, I have marked this task as done!");
                    System.out.println(task);
                    System.out.println(line);
                } else if (commandType == CommandType.UNMARK) {
                    String taskNumberString = command.substring(7);
                    int taskNumber = Integer.parseInt(taskNumberString);
                    Task task = listOfTasks.get(taskNumber - 1);
                    task.markAsUndone();
                    saveTasksToData(listOfTasks);
                    System.out.println(line);
                    System.out.println("I have marked this task as not done yet, try to finish soon!");
                    System.out.println(task);
                    System.out.println(line);
                } else if (commandType == CommandType.TODO)  {
                    ToDos todo = new ToDos(command.substring(5));
                    listOfTasks.add(todo);
                    saveTasksToData(listOfTasks);
                    System.out.println(line);
                    System.out.println("Alright! I have added this task:");
                    System.out.println(todo);
                    System.out.println(String.format("Now you have %d tasks in the list.", listOfTasks.size()));
                    System.out.println(line);
                } else if (commandType == CommandType.DEADLINE) {
                    String input = command.substring(9);
                    String[] parts = input.split(" /by ", 2);

                    String activity = parts[0];
                    // convert time in String type to LocalDate safely since checked in exceptions already
                    LocalDate time = LocalDate.parse(parts[1].trim());
                    Deadlines deadline = new Deadlines(activity, time);
                    listOfTasks.add(deadline);
                    saveTasksToData(listOfTasks);
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
                    // convert string type to Local Date type
                    LocalDate from = LocalDate.parse(toSplit[0].trim());
                    LocalDate to = LocalDate.parse(toSplit[1].trim());
                    Events event  = new Events(activity, from, to);
                    listOfTasks.add(event);
                    saveTasksToData(listOfTasks);
                    System.out.println(line);
                    System.out.println("Alright! I have added this task:");
                    System.out.println(event);
                    System.out.println(String.format("Now you have %d tasks in the list.", listOfTasks.size()));
                    System.out.println(line);
                } else if (commandType == CommandType.DELETE)  {
                    String taskNumberString = command.substring(7);
                    int taskNumber = Integer.parseInt(taskNumberString);
                    Task removedTask = listOfTasks.remove(taskNumber - 1);
                    saveTasksToData(listOfTasks);
                    System.out.println(line);
                    System.out.println("Alright! I have deleted this task.");
                    System.out.println(removedTask);
                    System.out.println(String.format("Now you have %d tasks in the list.", listOfTasks.size()));
                }
            } catch (DisciTrackException e) {
                System.out.println(line);
                System.out.println(e.getMessage());
                System.out.println(line);
            } catch (IOException e) {
                System.out.println(line);
                System.out.println("UHOH! I could not save your tasks.");
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

            //to check if date is inserted in correct format
            try {
                LocalDate.parse(time);
            } catch (DateTimeParseException e) {
                throw new DisciTrackException("UHOH! Please enter the deadline date in yyyy-MM-dd format!");
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

            try {
                LocalDate.parse(from);
                LocalDate.parse(to);
            } catch (DateTimeParseException e) {
                throw new DisciTrackException("UHOH! Please enter event dates in yyyy-MM-dd format!");
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

    public static String taskToFileLine (Task task) {
        //define 1 as done and 0 as not done in table
        String status = task.isDone() ? "1" : "0";

        if (task instanceof ToDos) {
            return "T | " + status + " | " + task.getActivity();
        } else if (task instanceof Deadlines) {
            Deadlines deadline = (Deadlines) task;
            return "D | " + status + " | " + deadline.getActivity() + " | " + deadline.getTime();
        } else if (task instanceof Events) {
            Events event = (Events) task;
            return "E | " + status + " | " + event.getActivity() + " | " + event.getFrom() + " | " + event.getTo();
        }

        return "";
    }

    public static void saveTasksToData (List<Task> listOfTasks) throws IOException {
        File dataFolder = new File("data");

        if (!dataFolder.exists()) {
            dataFolder.mkdir();
        }

        FileWriter writer = new FileWriter("data/discitrack.txt");

        for (Task task : listOfTasks) {
            writer.write(taskToFileLine(task) + System.lineSeparator());
        }

        writer.close();
    }

    public static List<Task> loadTasksFromData () throws FileNotFoundException {
        List<Task> listOfTasks = new ArrayList<>();

        File datafile = new File("data/discitrack.txt");

        // Create the data folder on first run before writing the save file.
        if(!datafile.exists()) {
            return listOfTasks;
        }

        Scanner fileScanner = new Scanner(datafile);

        while(fileScanner.hasNextLine()) {
            String line = fileScanner.nextLine();
            String[] parts = line.split(" \\| ");

            String taskType = parts[0];
            String status = parts[1];

            Task task;

            //add particular activity from data into list
            if (taskType.equals("T")) {
                task = new ToDos(parts[2]);
            } else if (taskType.equals("D")) {
                task = new Deadlines(parts[2], LocalDate.parse(parts[3]));
            } else {
                task = new Events(parts[2], LocalDate.parse(parts[3]), LocalDate.parse(parts[4]));
            }

            if (status.equals("1")) {
                task.markAsDone();
            }

            listOfTasks.add(task);
        }

        fileScanner.close();
        return listOfTasks;
    }
}
