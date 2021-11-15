import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Project 4 - Tests Main
 *
 * Contains JUnit tests for Main class and login loop.
 *
 * @author Richard Chang, Sara Xiao
 * @version 2021-11-15
 */
public class TestsMain extends Tests {

    @Test
    public void testOne() {
        String commands = "";
        commands += "login\n";
        commands += "student\n";
        commands += "student\n";
        commands += "logout\n";
        commands += "exit\n";

        setIOStreams(commands);

        // Run Program
        Main.main(new String[0]);
    }

    @Test
    public void testMainInvalidInput() {
        String commands = "";
        commands += "asdlfj092dalkfjlkdasjflksd\n";
        commands += "exit";

        setIOStreams(commands);
        Main.main(new String[0]);

        assertTrue(getOut().toString().contains(
            getOutputFromFile("ExpectedOutputs/testMainInvalidInput")));
    }

    @Test
    public void testMainCreateAccountLogin() {
        String commands = "create account\n" +
                "username\n" +
                "name\n" +
                "password\n" +
                "T\n" +
                "login\n" +
                "username\n" +
                "password\n" +
                "logout\n" +
                "exit";

        setIOStreams(commands);
        Main.main(new String[0]);

        assertTrue(getOut().toString().contains(
                getOutputFromFile("ExpectedOutputs/testMainCreateAccountLogin")));
    }
}
