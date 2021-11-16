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
        Main.main(new String[]{"empty"});

        // actual contains expected
        String fileName = "testTRBasicNav";
        String actual = getOut().toString();
        String expected = " ";

        assertTrue(actual.contains(expected));
    }
}
