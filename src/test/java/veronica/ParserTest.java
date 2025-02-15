package veronica;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import veronica.task.TaskManager;
import veronica.main.VeronicaException;
import veronica.ui.Parser;

import static org.junit.jupiter.api.Assertions.*;

class ParserTest {
    private Parser parser;
    private TaskManager taskManager;

    @BeforeEach
    void setUp() {
        parser = new Parser();
    }

    @Test
    void testProcessUserCommands_byeCommand_setsInactive() {
        parser.processUserCommands("bye");
        assertFalse(parser.getIsActive(), "Parser should be inactive after 'bye' command.");
    }

    @Test
    void testProcessUserCommands_addDeadline_valid() {
        assertDoesNotThrow(() -> parser.processUserCommands("deadline finish project /by 16/12/2025 1800"));
    }
}
