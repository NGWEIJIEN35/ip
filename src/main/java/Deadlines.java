public class Deadlines extends Task {
    private String time;

    public Deadlines(String activity, String time) {
        super(activity);
        this.time = time;
    }

    public String getTime() {
        return this.time;
    }

    @Override
    public String toString() {
        return String.format("[D]%s (by: %s)", super.toString(), time);
    }
}
