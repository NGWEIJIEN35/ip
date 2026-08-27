package discitrack.parser;

import discitrack.command.AddCommand;
import discitrack.command.ByeCommand;
import discitrack.command.CheckDateCommand;
import discitrack.command.Command;
import discitrack.command.DeleteCommand;
import discitrack.command.ListCommand;
import discitrack.command.MarkCommand;
import discitrack.command.UnmarkCommand;
import discitrack.exception.DisciTrackException;
import discitrack.task.Deadlines;
import discitrack.task.Events;
import discitrack.task.ToDos;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class Parser {
    public static Command parse(String fullCommand) throws DisciTrackException {
        String command = fullCommand.trim();
        String commandWord = command.split(" ", 2)[0];

        if (commandWord.equals("bye")) {
            return new ByeCommand();
        } else if (commandWord.equals("list")) {
            return new ListCommand();
        } else if (commandWord.equals("mark")) {
            return new MarkCommand(parseTaskNumber(command, 5));
        } else if (commandWord.equals("unmark")) {
            return new UnmarkCommand(parseTaskNumber(command, 7));
        } else if (commandWord.equals("checkdate")) {
            return new CheckDateCommand(parseCheckDate(command));
        } else if (commandWord.equals("todo")) {
            return new AddCommand(parseTodo(command));
        } else if (commandWord.equals("deadline")) {
            return new AddCommand(parseDeadline(command));
        } else if (commandWord.equals("event")) {
            return new AddCommand(parseEvent(command));
        } else if (commandWord.equals("delete")) {
            return new DeleteCommand(parseTaskNumber(command, 7));
        } else {
            throw new DisciTrackException("UHOH, I didn't know what you mean.");
        }
    }

    private static int parseTaskNumber(String command, int commandWordLength) throws DisciTrackException {
        String taskNumberString = "";

        if (command.length() > commandWordLength) {
            taskNumberString = command.substring(commandWordLength).trim();
        }

        if (taskNumberString.isEmpty()) {
            throw new DisciTrackException("UHOH! Please enter a task number!");
        }

        try {
            return Integer.parseInt(taskNumberString);
        } catch (NumberFormatException e) {
            throw new DisciTrackException("UHOH! Please enter a valid task number!");
        }
    }

    private static LocalDate parseCheckDate(String command) throws DisciTrackException {
        String date = command.substring(9).trim();

        if (date.isEmpty()) {
            throw new DisciTrackException("UHOH! Please enter a date to check!");
        }

        try {
            return LocalDate.parse(date);
        } catch (DateTimeParseException e) {
            throw new DisciTrackException("UHOH! Please enter the check date in yyyy-MM-dd format!");
        }
    }

    private static ToDos parseTodo(String command) throws DisciTrackException {
        String activity = command.substring(4).trim();

        if (activity.isEmpty()) {
            throw new DisciTrackException("UHOH! The activity of a todo cannot be empty!");
        }

        return new ToDos(activity);
    }

    private static Deadlines parseDeadline(String command) throws DisciTrackException {
        String input = command.substring(8).trim();

        if (input.isEmpty()) {
            throw new DisciTrackException("UHOH! The activity of a deadline cannot be empty!");
        }

        String[] parts = input.split("\\s+/by\\s+", 2);

        if (parts.length < 2) {
            throw new DisciTrackException("UHOH! A deadline needs a /by time!");
        }

        String activity = parts[0].trim();
        String time = parts[1].trim();

        if (activity.isEmpty()) {
            throw new DisciTrackException("UHOH! The activity of a deadline cannot be empty!");
        }

        if (time.isEmpty()) {
            throw new DisciTrackException("UHOH! The time of a deadline cannot be empty!");
        }

        try {
            return new Deadlines(activity, LocalDate.parse(time));
        } catch (DateTimeParseException e) {
            throw new DisciTrackException("UHOH! Please enter the deadline date in yyyy-MM-dd format!");
        }
    }

    private static Events parseEvent(String command) throws DisciTrackException {
        String input = command.substring(5).trim();

        if (input.isEmpty()) {
            throw new DisciTrackException("UHOH! The activity of an event cannot be empty!");
        }

        String[] fromSplit = input.split("\\s+/from\\s+", 2);

        if (fromSplit.length < 2) {
            throw new DisciTrackException("UHOH! An event needs both /from and /to!");
        }

        String activity = fromSplit[0].trim();

        String[] toSplit = fromSplit[1].split("\\s+/to\\s+", 2);

        if (toSplit.length < 2) {
            throw new DisciTrackException("UHOH! An event needs both /from and /to!");
        }

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
            return new Events(activity, LocalDate.parse(from), LocalDate.parse(to));
        } catch (DateTimeParseException e) {
            throw new DisciTrackException("UHOH! Please enter event dates in yyyy-MM-dd format!");
        }
    }
}
