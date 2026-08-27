package discitrack.task;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Deadlines extends Task {
    private LocalDate time;

    public Deadlines(String activity, LocalDate time) {
        super(activity);
        this.time = time;
    }

    public LocalDate getTime() {
        return this.time;
    }

    @Override
    public String toString() {
        String date = DateTimeFormatter.ofPattern("MMM dd yyyy").format(time);
        return String.format("[D]%s (by: %s)", super.toString(), date);
    }
}
