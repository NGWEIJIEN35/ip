package discitrack.task;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

public class TaskListTest {
    @Test
    public void findTasksByDate_matchingDeadlineDate_returnsDeadline() {
        TaskList tasks = new TaskList();
        tasks.add(new ToDos("read book"));
        tasks.add(new Deadlines("submit homework", LocalDate.parse("2026-08-29")));

        List<Task> tasksOnDate = tasks.findTasksByDate(LocalDate.parse("2026-08-29"));

        assertEquals(1, tasksOnDate.size());
        assertInstanceOf(Deadlines.class, tasksOnDate.get(0));
        assertEquals("submit homework", tasksOnDate.get(0).getActivity());
    }

    @Test
    public void findTasksByDate_matchingEventEndDate_returnsEvent() {
        TaskList tasks = new TaskList();
        tasks.add(new Events("camp", LocalDate.parse("2026-09-01"), LocalDate.parse("2026-09-03")));
        tasks.add(new Deadlines("submit homework", LocalDate.parse("2026-08-29")));

        List<Task> tasksOnDate = tasks.findTasksByDate(LocalDate.parse("2026-09-03"));

        assertEquals(1, tasksOnDate.size());
        assertInstanceOf(Events.class, tasksOnDate.get(0));
        assertEquals("camp", tasksOnDate.get(0).getActivity());
    }

    @Test
    public void findTasksByDate_noMatchingDate_returnsEmptyList() {
        TaskList tasks = new TaskList();
        tasks.add(new ToDos("read book"));
        tasks.add(new Deadlines("submit homework", LocalDate.parse("2026-08-29")));
        tasks.add(new Events("camp", LocalDate.parse("2026-09-01"), LocalDate.parse("2026-09-03")));

        List<Task> tasksOnDate = tasks.findTasksByDate(LocalDate.parse("2026-10-01"));

        assertEquals(0, tasksOnDate.size());
    }
}
