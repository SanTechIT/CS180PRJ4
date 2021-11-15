import org.junit.Test;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

import java.io.*;

import static org.junit.Assert.*;

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
     * Borrowed from RunLocal
     * @param args
     */
    public static void main(String[] args){
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
        String expected = "Reading Data...\n" +
                "Using test files...\n" +
                "Using Initial Dataset\n" +
                "Data has been read and loaded!\n" +
                "\n" +
                "Welcome to our program!\n" +
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
                "exit\n" +
                "> \n" +
                "Exit:\n" +
                "Thank you for using our program. Goodbye!\n" +
                "Saving Data...\n" +
                "Data has been saved!";
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
        String expected = "Reading Data...\n" +
                "Using test files...\n" +
                "Using Initial Dataset\n" +
                "Data has been read and loaded!\n" +
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
        assertTrue(actual.contains(expected));
    }
}
