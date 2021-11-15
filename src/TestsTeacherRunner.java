import org.junit.Test;
import org.junit.After;

import java.lang.reflect.Field;

import org.junit.Assert;
import org.junit.Before;

import static org.junit.Assert.*;

/**
 * Project 4 - Tests Teacher Runner
 * <p>
 * Contains JUnit tests for TeacherRunner (Teacher loop)
 *
 * @author Sara Xiao
 * @version 2021-11-15
 */
public class TestsTeacherRunner extends Tests {

    private static String loginLogoutAsTeacher(String commands) {
        commands = "login\n" +
                "t\n" +
                "t\n" +
                commands;

        commands += "\nlogout\nexit";
        return commands;
    }

    /**
     * Tests basic menu navigation and forum editing:
     * <p>
     * Navigation to a discussion forum
     * Reply to post
     * Delete post
     * Delete forum
     * Create forum
     * Navigate to created forum
     * Back
     */
    @Test
    public void testTRBasicNav() {
        String commands = loginLogoutAsTeacher(
                "0\n" +
                        "0\n" +
                        "0\n" +
                        "reply 1\n" +
                        "test post that is reply to 1\n" +
                        "delete 6\n" +
                        "yes\n" +
                        "delete forum\n" +
                        "create forum\n" +
                        "1\n" +
                        "back\n" +
                        "back"
        );

        setIOStreams(commands);
        Main.main(new String[]{"blank"});

        // actual contains expected
        String fileName = "testTRBasicNav";
        String actual = getOut().toString();
        String expected = "Reading Data...\n" +
                "Creating new Db\n" +
                "Data has been read and loaded!\n" +
                "\n" +
                "Welcome to our program!\n" +
                "Please type one of these commands:\n" +
                "login\n" +
                "create account\n" +
                "exit\n" +
                "> Enter your username: \n" +
                "Enter your password: \n" +
                "Wrong username or password\n" +
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
}
