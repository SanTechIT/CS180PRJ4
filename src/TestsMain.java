import org.junit.Test;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

import static org.hamcrest.CoreMatchers.*;

// for regex (currently unused, requires hamcrest 2)
// import static org.hamcrest.text.MatchesPattern.*;

import java.io.*;

import static org.junit.Assert.*;

/**
 * Project 4 - Tests Main
 * <p>
 * Contains JUnit tests for Main class and login loop.
 *
 * @author Richard Chang, Sara Xiao
 * @version 2021-11-16
 */
public class TestsMain extends Tests {
    /**
     * Borrowed from RunLocal
     * Runs tests
     *
     * @param args
     */
    public static void main(String[] args) {
        Result result = JUnitCore.runClasses(TestsMain.class);
        if (result.wasSuccessful()) {
            System.out.println("Excellent - Test ran successfully");
        } else {
            for (Failure failure : result.getFailures()) {
                System.out.println(failure.toString());
            }
        }
    }

    /**
     * Test running the program + immediately exiting.
     * Runs non-test version of program.
     * Make sure it displays correct output and doesn't crash.
     * <p>
     * Uses 3 expected strings because read/load data messages can vary.
     */
    @Test
    public void testBasic() {
        String commands = "exit\n";
        setIOStreams(commands);

        // Run program
        Main.main(new String[0]);

        String expected = "Data has been read and loaded!\n" +
            "\n" +
            "Welcome to our program!\n" +
            "Please type one of these commands:\n" +
            "login\n" +
            "create account\n" +
            "exit\n" +
            "> \n" +
            "Exit:\n" +
            "Thank you for using our program. Goodbye!\n" +
            "Saving Data...\n" +
            "Data has been saved!\n";
        checkOutputContainsExpected(expected);
    }

    /**
     * Test logging in.
     */
    @Test
    public void testLoginLogout() {
        String commands = "";
        commands += "login\n";
        commands += "student\n";
        commands += "password\n";
        commands += "logout\n";
        commands += "exit\n";
        setIOStreams(commands);

        // Run Program
        Main.main(new String[]{"test"});

        String expected = "Welcome to our program!\n" +
            "Please type one of these commands:\n" +
            "login\n" +
            "create account\n" +
            "exit\n" +
            "> Enter your username: \n" +
            "Enter your password: \n" +
            "Successfully Logged In!\n" +
            "\n" +
            "Welcome Alice!\n" +
            "Please type the number of a course to view:\n" +
            "0 - MA165\n" +
            "1 - CS180\n" +
            "2 - EAPS106\n" +
            "\n" +
            "Or, please type one of these commands: \n" +
            "edit account\n" +
            "delete account\n" +
            "logout\n" +
            "> \n" +
            "Logging out:\n" +
            "You have successfully logged out.\n" +
            "\n" +
            "Welcome to our program!\n" +
            "Please type one of these commands:\n" +
            "login\n" +
            "create account\n" +
            "exit";
        checkOutputContainsExpected(expected);
    }

    /**
     * Test that main loop handles invalid input.
     */
    @Test
    public void testInvalidInput() {
        String commands = "";
        commands += "asdlfj092dalkfjlkdasjflksd\n";
        commands += "exit\n";

        setIOStreams(commands);
        Main.main(new String[]{"test"});

        // actual contains expected
        String expected = "Welcome to our program!\n" +
            "Please type one of these commands:\n" +
            "login\n" +
            "create account\n" +
            "exit\n" +
            "> \n" +
            "Input Error:\n" +
            "Sorry, I couldn't understand what you typed. Please try again!\n" +
            "-----\n" +
            "\n" +
            "Welcome to our program!\n" +
            "Please type one of these commands:\n" +
            "login\n" +
            "create account\n" +
            "exit\n";
        checkOutputContainsExpected(expected);
    }

    /**
     * Test account creation and logging in with new account.
     */
    @Test
    public void testCreateAccountLogin() {
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
        Main.main(new String[]{"test"});

        // actual contains expected
        String expected = "Welcome to our program!\n" +
            "Please type one of these commands:\n" +
            "login\n" +
            "create account\n" +
            "exit\n" +
            "> Enter your username: \n" +
            "Enter your name: \n" +
            "Create your password: \n" +
            "Select if you are a [T]eacher or [S]tudent: \n" +
            "\n" +
            "Welcome to our program!\n" +
            "Please type one of these commands:\n" +
            "login\n" +
            "create account\n" +
            "exit\n" +
            "> Enter your username: \n" +
            "Enter your password: \n" +
            "Successfully Logged In!\n" +
            "\n" +
            "Welcome name!\n" +
            "Please type the number of a course to view:\n" +
            "0 - MA165\n" +
            "1 - CS180\n" +
            "2 - EAPS106\n" +
            "\n" +
            "Or, please type one of these commands: \n" +
            "edit account\n" +
            "delete account\n" +
            "create course\n" +
            "view student\n" +
            "logout\n" +
            "> \n" +
            "Logging out:\n" +
            "You have successfully logged out.\n" +
            "\n" +
            "Welcome to our program!\n" +
            "Please type one of these commands:\n" +
            "login\n" +
            "create account\n" +
            "exit\n" +
            "> \n" +
            "Exit:\n" +
            "Thank you for using our program. Goodbye!\n" +
            "Saving Data...\n" +
            "Data has been saved!";
        checkOutputContainsExpected(expected);
    }

    /**
     * Test account creation with username that's been used.
     */
    @Test
    public void testInvalidUsername() {
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
        Main.main(new String[]{"test"});

        String expected = "" +
            "Please type one of these commands:\n" +
            "login\n" +
            "create account\n" +
            "exit\n" +
            "> Enter your username: \n" +
            "Enter your name: \n" +
            "Create your password: \n" +
            "Select if you are a [T]eacher or [S]tudent: \n" +
            "\n" +
            "Welcome to our program!\n" +
            "Please type one of these commands:\n" +
            "login\n" +
            "create account\n" +
            "exit\n" +
            "> Enter your username: \n" +
            "Enter your name: \n" +
            "Create your password: \n" +
            "Select if you are a [T]eacher or [S]tudent: \n" +
            "Error: A user with this username already exists\n" +
            "\n" +
            "Welcome to our program!\n" +
            "Please type one of these commands:\n" +
            "login\n" +
            "create account\n" +
            "exit\n" +
            "> \n" +
            "Exit:\n" +
            "Thank you for using our program. Goodbye!\n" +
            "Saving Data...\n" +
            "Data has been saved!";
        checkOutputContainsExpected(expected);
    }
}
