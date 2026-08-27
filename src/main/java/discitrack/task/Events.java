package discitrack.task;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Events extends Task {
    private final LocalDate from;
    private final LocalDate to;

    public Events(String activity, LocalDate from, LocalDate to) {
        super(activity);
        this.from = from;
        this.to = to;
    }

    public LocalDate getFrom() {
        return this.from;
    }

    public LocalDate getTo() {
        return this.to;
    }
    @Override
    public String toString() {
        String stringFrom = DateTimeFormatter.ofPattern("MMM dd yyyy").format(from);
        String stringTo = DateTimeFormatter.ofPattern("MMM dd yyyy").format(to);
        return String.format("[E]%s (from: %s to: %s)", super.toString(), stringFrom, stringTo);
    }
}
