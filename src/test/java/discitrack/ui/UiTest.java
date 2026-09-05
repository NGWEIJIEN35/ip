package discitrack.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import discitrack.task.ToDos;

public class UiTest {
    @Test
    public void showTaskMarked_multipleResponseLines_joinsLines() {
        Ui ui = new Ui(false);
        ToDos task = new ToDos("read chapter");
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
