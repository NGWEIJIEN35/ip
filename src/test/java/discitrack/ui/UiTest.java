package discitrack.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import discitrack.task.Todo;

public class UiTest {
    @Test
    public void showTaskMarked_multipleResponseLines_joinsLines() {
        Ui ui = new Ui(false);
        Todo task = new Todo("read chapter");
        task.markAsDone();

        ui.showTaskMarked(task);

        String expectedResponse = String.join(System.lineSeparator(),
                "Well done! You completed:",
                "",
                "[T] [X] read chapter",
                "",
                "Keep the momentum going! You can do it!");
        assertEquals(expectedResponse, ui.getLastResponse());
    }
}
