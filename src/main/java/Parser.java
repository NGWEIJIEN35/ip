import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class Parser {
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
        } else if (commandWord.equals("checkdate")) {
            return CommandType.CHECKDATE;
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

    public static void validateCommand(String command, CommandType commandType) throws DisciTrackException {
        if (commandType == CommandType.BYE || commandType == CommandType.LIST) {
            return;
        } else if (commandType == CommandType.MARK
                || commandType == CommandType.UNMARK
                || commandType == CommandType.DELETE) {
            return;
        } else if (commandType == CommandType.CHECKDATE) {
            validateCheckDate(command);
        } else if (commandType == CommandType.TODO) {
            validateTodo(command);
        } else if (commandType == CommandType.DEADLINE) {
            validateDeadline(command);
        } else if (commandType == CommandType.EVENT) {
            validateEvent(command);
        } else {
            throw new DisciTrackException("UHOH, I didn't know what you mean.");
        }
    }

    public static int getTaskNumber(String command, int commandWordLength) {
        String taskNumberString = command.substring(commandWordLength);
        return Integer.parseInt(taskNumberString);
    }

    public static LocalDate parseCheckDate(String command) {
        String stringDate = command.substring(9).trim();
        return LocalDate.parse(stringDate);
    }

    public static ToDos parseTodo(String command) {
        return new ToDos(command.substring(5));
    }

    public static Deadlines parseDeadline(String command) {
        String input = command.substring(9);
        String[] parts = input.split(" /by ", 2);

        String activity = parts[0];
        LocalDate time = LocalDate.parse(parts[1].trim());
        return new Deadlines(activity, time);
    }

    public static Events parseEvent(String command) {
        String input = command.substring(6);
        String[] fromSplit = input.split(" /from ", 2);
        String activity = fromSplit[0];

        String[] toSplit = fromSplit[1].split(" /to ", 2);
        LocalDate from = LocalDate.parse(toSplit[0].trim());
        LocalDate to = LocalDate.parse(toSplit[1].trim());
        return new Events(activity, from, to);
    }

    private static void validateCheckDate(String command) throws DisciTrackException {
        String date = command.substring(9).trim();

        if (date.isEmpty()) {
            throw new DisciTrackException("UHOH! Please enter a date to check!");
        }

        try {
            LocalDate.parse(date);
        } catch (DateTimeParseException e) {
            throw new DisciTrackException("UHOH! Please enter the check date in yyyy-MM-dd format!");
        }
    }

    private static void validateTodo(String command) throws DisciTrackException {
        String activity = command.substring(4).trim();

        if (activity.isEmpty()) {
            throw new DisciTrackException("UHOH! The activity of a todo cannot be empty!");
        }
    }

    private static void validateDeadline(String command) throws DisciTrackException {
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

        try {
            LocalDate.parse(time);
        } catch (DateTimeParseException e) {
            throw new DisciTrackException("UHOH! Please enter the deadline date in yyyy-MM-dd format!");
        }
    }

    private static void validateEvent(String command) throws DisciTrackException {
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
    }
}
