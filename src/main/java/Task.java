public class Task {
    private String activity; //tasks that the user needs to finish
    private boolean status;  //whether it is done or not

    public Task(String activity) {
        this.activity = activity;
        this.status = false;
    }

    public void markAsDone() {
        this.status = true;
    }

    public void markAsUndone() {
        this.status = false;
    }

    public String checkStatus() {   //to get symbols for checklist boxes
        return this.status ? "X" : " ";
    }

    @Override
    public String toString() {
        return "[" + checkStatus() + "] " + this.activity;
    }
}
