package discitrack.parser;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import discitrack.command.AddCommand;
import discitrack.command.ByeCommand;
import discitrack.command.CheckDateCommand;
import discitrack.command.Command;
import discitrack.command.DeleteCommand;
import discitrack.command.FindCommand;
import discitrack.command.HelpCommand;
import discitrack.command.ListCommand;
import discitrack.command.MarkCommand;
import discitrack.command.UnmarkCommand;
import discitrack.exception.DisciTrackException;
import discitrack.task.Deadline;
import discitrack.task.Event;
import discitrack.task.Todo;

/**
 * Parses user input into executable commands.
 */
public class Parser {
    /**
     * Parses the full user command into the corresponding command object.
     *
     * @param fullCommand the full command entered by the user.
     * @return the command represented by the user input.
     * @throws DisciTrackException if the command is unknown or has an invalid format.
     */
    public static Command parse(String fullCommand) throws DisciTrackException {
        assert fullCommand != null
                : "A command must be provided before parsing.";

        String command = fullCommand.trim();
        String[] commandParts = command.split(" ", 2);
        String commandWord = commandParts[0];
        String arguments = commandParts.length > 1 ? commandParts[1].trim() : "";

        if (commandWord.equals("bye")) {
            return new ByeCommand();
        } else if (commandWord.equals("help")) {
            return new HelpCommand();
        } else if (commandWord.equals("list")) {
            return new ListCommand();
        } else if (commandWord.equals("mark")) {
            return new MarkCommand(parseTaskNumber(arguments));
        } else if (commandWord.equals("unmark")) {
            return new UnmarkCommand(parseTaskNumber(arguments));
        } else if (commandWord.equals("checkdate")) {
            return new CheckDateCommand(parseCheckDate(arguments));
        } else if (commandWord.equals("find")) {
            return new FindCommand(parseFindKeyword(arguments));
        } else if (commandWord.equals("todo")) {
            return new AddCommand(parseTodo(arguments));
        } else if (commandWord.equals("deadline")) {
            return new AddCommand(parseDeadline(arguments));
        } else if (commandWord.equals("event")) {
            return new AddCommand(parseEvent(arguments));
        } else if (commandWord.equals("delete")) {
            return new DeleteCommand(parseTaskNumber(arguments));
        } else {
            throw new DisciTrackException("UHOH, I didn't know what you mean.");
        }
    }

    /**
     * Parses the task number after a command word.
     *
     * @param taskNumberString the task number entered by the user.
     * @return the one-based task number entered by the user.
     * @throws DisciTrackException if the task number is missing or invalid.
     */
    private static int parseTaskNumber(String taskNumberString) throws DisciTrackException {
        if (taskNumberString.isEmpty()) {
            throw new DisciTrackException("UHOH! Please enter a task number!");
        }

        try {
            return Integer.parseInt(taskNumberString);
        } catch (NumberFormatException e) {
            throw new DisciTrackException("UHOH! Please enter a valid task number!");
        }
    }

    /**
     * Parses the date supplied to a checkdate command.
     *
     * @param date the date entered by the user.
     * @return the date to check.
     * @throws DisciTrackException if the date is missing or not in yyyy-MM-dd format.
     */
    private static LocalDate parseCheckDate(String date) throws DisciTrackException {
        if (date.isEmpty()) {
            throw new DisciTrackException("UHOH! Please enter a date to check!");
        }

        try {
            return LocalDate.parse(date);
        } catch (DateTimeParseException e) {
            throw new DisciTrackException("UHOH! Please enter the check date in yyyy-MM-dd format!");
        }
    }

    /**
     * Parses the keyword supplied to a find command.
     *
     * @param keyword the keyword entered by the user.
     * @return the keyword to search for.
     * @throws DisciTrackException if the keyword is empty.
     */
    private static String parseFindKeyword(String keyword) throws DisciTrackException {
        if (keyword.isEmpty()) {
            throw new DisciTrackException("UHOH! Please enter a keyword to find!");
        }

        return keyword;
    }

    /**
     * Parses a todo command into a todo task.
     *
     * @param activity the todo activity entered by the user.
     * @return the todo task described by the command.
     * @throws DisciTrackException if the todo activity is empty.
     */
    private static Todo parseTodo(String activity) throws DisciTrackException {
        if (activity.isEmpty()) {
            throw new DisciTrackException("UHOH! The activity of a todo cannot be empty!");
        }

        return new Todo(activity);
    }

    /**
     * Parses a deadline command into a deadline task.
     *
     * @param input the deadline details entered by the user.
     * @return the deadline task described by the command.
     * @throws DisciTrackException if the activity, /by keyword, or date is invalid.
     */
    private static Deadline parseDeadline(String input) throws DisciTrackException {
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
            return new Deadline(activity, LocalDate.parse(time));
        } catch (DateTimeParseException e) {
            throw new DisciTrackException("UHOH! Please enter the deadline date in yyyy-MM-dd format!");
        }
    }

    /**
     * Parses an event command into an event task.
     *
     * @param input the event details entered by the user.
     * @return the event task described by the command.
     * @throws DisciTrackException if the activity, /from keyword, /to keyword, or dates are invalid.
     */
    private static Event parseEvent(String input) throws DisciTrackException {
        if (input.isEmpty()) {
            throw new DisciTrackException("UHOH! The activity of an event cannot be empty!");
        }

        String[] eventAndDates = splitEventDetails(input, "/from");
        String[] startAndEndDates = splitEventDetails(eventAndDates[1], "/to");
        String activity = eventAndDates[0].trim();
        String startDate = startAndEndDates[0].trim();
        String endDate = startAndEndDates[1].trim();

        validateEventDetails(activity, startDate, endDate);
        return createEvent(activity, startDate, endDate);
    }

    private static String[] splitEventDetails(String input, String separator) throws DisciTrackException {
        String[] parts = input.split("\\s+" + separator + "\\s+", 2);
        if (parts.length < 2) {
            throw new DisciTrackException("UHOH! An event needs both /from and /to!");
        }
        return parts;
    }

    private static void validateEventDetails(String activity, String startDate, String endDate)
            throws DisciTrackException {
        if (activity.isEmpty()) {
            throw new DisciTrackException("UHOH! The activity of an event cannot be empty!");
        }

        if (startDate.isEmpty()) {
            throw new DisciTrackException("UHOH! The start time of an event cannot be empty!");
        }

        if (endDate.isEmpty()) {
            throw new DisciTrackException("UHOH! The end time of an event cannot be empty!");
        }
    }

    private static Event createEvent(String activity, String startDate, String endDate)
            throws DisciTrackException {
        try {
            return new Event(activity, LocalDate.parse(startDate), LocalDate.parse(endDate));
        } catch (DateTimeParseException e) {
            throw new DisciTrackException("UHOH! Please enter event dates in yyyy-MM-dd format!");
        }
    }
}
