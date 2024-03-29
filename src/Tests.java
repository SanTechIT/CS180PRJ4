import org.junit.After;

import java.io.*;

import static org.hamcrest.CoreMatchers.containsString;

import static org.junit.Assert.*;

/**
 * Project 4 - Tests
 * <p>
 * Utility class for test cases.
 * <p>
 * Contains functionality for setting streams,
 * getting file output, etc.
 *
 * @author chang794 (Richard Chang), saraxiao0 (Sara Xiao)
 * @version 2021-11-16
 */
public class Tests {
    // terminal output PrintStream for printing to console
    private static final PrintStream OUT_STREAM = System.out;
    // for setting output stream to program output
    private ByteArrayOutputStream out = new ByteArrayOutputStream();

    // Strings for easily executing certain commands
    // that must be executed to test other features
    public static final String CREATE_TEACHER = "create account\n" + "teacher\n" + "name\n" +
        "password\n" + "t\n";
    public static final String CREATE_STUDENT = "create account\n" + "student\n" + "name\n" +
        "password\n" + "s\n";
    public static final String LOGIN_TEACHER = "login\n" + "teacher\n" + "password\n";
    public static final String LOGIN_STUDENT_ALICE = "login\n" + "student\n" + "password\n";
    public static final String LOGIN_STUDENT_S = "login\n" + "s\n" + "s\n";
    public static final String CREATE_DEFAULT_CLASSES = "create course\n" + "PHYS110\n" + "create " +
        "course\n" + "CS180\n" + "create " +
        "course\n" + "ENGL106\n";
    public static final String CREATE_DISCUSSION = "create discussion\n" + "Lesson 1\n" + "create " +
        "discussion\n" + "Lesson 2\n" + "create discussion\n" + "Lesson 3\n";
    public static final String CREATE_SINGLE_CLASS = "create course\n" + "PHYS110\n";
    public static final String CREATE_DEFAULT_DISCUSSIONS = "create forum\n" + "Lesson 1\n" +
        "create forum\n" + "Lesson 2\n" + "create forum\n" + "Lesson 3\n";
    public static final String NAVIGATE_TO_DISCUSSION = "0\n" + "0\n";
    public static final String EXIT_STRING = "logout\n" + "exit\n";

    /**
     * Get the out stream
     *
     * @return ByteArrayOutputStream representing current output
     */
    public ByteArrayOutputStream getOut() {
        return out;
    }

    /**
     * Set out stream
     *
     * @param out
     */
    public void setOut(ByteArrayOutputStream out) {
        this.out = out;
    }

    /**
     * Set up in/output
     * Methods-to-test will get "console" input from a passed in string
     * <p>
     * Courtesy of
     * https://stackoverflow.com/questions/6415728/junit-testing-with-simulated-user-input
     * As well as RunLocalTest.java (Various)
     *
     * @param commands commands that will be executed on test method
     */
    public void setIOStreams(String commands) {
        // Setup IN/OUTPUT
        ByteArrayInputStream in = new ByteArrayInputStream(commands.getBytes());
        setOut(new ByteArrayOutputStream());
        System.setOut(new PrintStream(getOut()));
        System.setIn(in);
    }

    /**
     * Check that program output contains an expected string
     * Used by derived classes
     *
     * @param expected string expected to be in output
     */
    public void checkOutputContainsExpected(String expected) {
        String actual = getOut().toString();
        assertThat(actual, containsString(expected));
    }

    /**
     * After each test is finished, set system output to console/terminal
     * So program output can be seen in test cases
     */
    @After
    public void setIOStreamsAfter() {
        // Restore output, print output
        System.setOut(OUT_STREAM);
        System.out.println(getOut());
    }
}
