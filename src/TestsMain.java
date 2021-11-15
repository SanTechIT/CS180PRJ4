import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Project 4 - Tests Main
 *
 * Contains JUnit tests for Main class and login loop.
 *
 * @author Richard Chang, Sara Xiao
 * @version 2021-11-15
 */
public class TestsMain extends Tests {

    /**
     * Test logging in and logging out.
     */
    @Test
    public void testLoginLogout() {
        String commands = "";
        commands += "login\n";
        commands += "student\n";
        commands += "student\n";
        commands += "logout\n";
        commands += "exit\n";

        setIOStreams(commands);

        // Run Program
        Main.main(new String[]{"useser"});
    }

    /**
     * Test that main loop handles invalid input.
     */
    @Test
    public void testMainInvalidInput() {
        String commands = "";
        commands += "asdlfj092dalkfjlkdasjflksd\n";
        commands += "exit\n";

        setIOStreams(commands);
        Main.main(new String[]{"useser"});

        // actual contains expected
        String fileName = "testMainInvalidInput";
        String actual = getOut().toString();
        String expected = getOutputFromFile("ExpectedOutputs/" + fileName);
        assertTrue(actual.contains(expected));
    }

    /**
     * Test account creation and logging in with new account.
     */
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
                "exit\n";

        setIOStreams(commands);
        Main.main(new String[]{"useser"});

        // actual contains expected
        String fileName = "testMainCreateAccountLogin";
        String actual = getOut().toString();
        String expected = getOutputFromFile("ExpectedOutputs/" + fileName);
        assertTrue(actual.contains(expected));
    }

    /**
     * Test account creation with username that's been used.
     */
    @Test
    public void testMainInvalidUsername() {
        String commands = "create account\n" +
                "username\n" +
                "name\n" +
                "password\n" +
                "T\n" +
                "create account\n" +
                "username\n" +
                "name\n" +
                "password2\n" +
                "T\n" +
                "exit\n";

        setIOStreams(commands);
        Main.main(new String[]{"useser"});

        // actual contains expected
        String fileName = "testMainInvalidUsername";
        String actual = getOut().toString();
        String expected = getOutputFromFile("ExpectedOutputs/" + fileName);
        assertEquals(expected);
    }
}
