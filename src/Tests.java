import org.junit.Test;
import org.junit.After;
import java.lang.reflect.Field;
import org.junit.Assert;
import org.junit.Before;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

import java.io.*;
import static org.hamcrest.CoreMatchers.containsString;

import static org.junit.Assert.*;

/**
 * Project 4 - Tests
 *
 * Utility class for test cases.
 * <p>
 * Contains functionality for setting streams,
 * getting file output, etc.
 *
 * @author chang794 (Richard Chang), saraxiao0 (Sara Xiao)
 * @version 2021-11-15
 */
public class Tests {
    // terminal output PrintStream for printing to console
    private static final PrintStream ts = System.out;
    // for setting output stream to program output
    private ByteArrayOutputStream out = new ByteArrayOutputStream();

    /**
     * Get the out stream
     * @return ByteArrayOutputStream representing current output
     */
    public ByteArrayOutputStream getOut() {
        return out;
    }

    /**
     * Set out stream
     * @param out
     */
    public void setOut(ByteArrayOutputStream out) {
        this.out = out;
    }

    /**
     * Set up in/output
     * Methods-to-test will get "console" input from a passed in string
     *
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
     * After each test is finished, set system output to console/terminal
     * So program output can be seen in test cases
     */
    @After
    public void setIOStreamsAfter() {
        // Restore output, print output
        System.setOut(ts);
        System.out.println(getOut());
    }

    /**
     * Get string containing contents of file (newline separated)
     *
     * @param fileName name of file
     * @return contents of file
     */
    public static String getOutputFromFile(String fileName) {
        try (BufferedReader br = new BufferedReader(
            new FileReader(fileName)
        )) {
            String output = "";
            String line = br.readLine();
            while (line != null) {
                output += line + "\n";
                line = br.readLine();
            }

            // shave off last \n
            return output.substring(0, output.length() - 1);
        }
        catch (IOException e) {
          e.printStackTrace();
          return "FILE ERROR";
        }
    }

    /**
     * Removes all spaces, newlines from string.
     * For comparison testing (JUnit) that ignores whitespace.
     * Currently unused.
     *
     * @param str string to strip whitespace from
     * @return stripped string
     */
    public static String removeWhitespace(String str) {
        return str.replace(" ", "").replace("\n", "");
    }
}
