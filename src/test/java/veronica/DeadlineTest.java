package veronica;

import org.junit.jupiter.api.Test;
import veronica.task.Deadline;

import static org.junit.jupiter.api.Assertions.*;

class DeadlineTest {

    @Test
    void testDeadline_invalidDate_setsNull() {
        Deadline deadline = new Deadline("Submit report", "invalid-date");
        assertNull(deadline.getDeadline(), "Invalid date should result in a null deadline.");
    }

    @Test
    void testToFileString_includesByField() {
        Deadline deadline = new Deadline("Submit report", "2025-12-16T18:00");
        assertTrue(deadline.toFileString().contains("by:"), "toFileString() should include the 'by' field.");
    }
}
