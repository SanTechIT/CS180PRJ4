import org.junit.Test;
import org.junit.After;

import java.lang.reflect.Field;

import org.junit.Assert;
import org.junit.Before;

import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.containsString;
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

    /**
     * Tests basic menu navigation to a discussion forum
     */
    @Test
    public void testNavigation() {
        String commands =
            LOGIN_TEACHER +
                "0\n" +
                "0\n" +
                EXIT_STRING;

        setIOStreams(commands);
        Main.main(new String[]{"test"});

        String expected = "Welcome John!\n" +
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
            "Welcome to MA165!\n" +
            "Please type the number of a discussion forum to view:\n" +
            "0 - default discussion\n" +
            "\n" +
            "Or, please type one of these commands: \n" +
            "\n" +
            "back\n" +
            "create forum\n" +
            "edit this course\n" +
            "delete course\n" +
            "logout\n" +
            "> \n" +
            "Welcome to default discussion!\n" +
            "Commands: back, reply [num], edit [num], delete [num], grade [num], view voteboard, edit this forum, " +
            "delete forum, logout\n" +
            "Replace [num] with the number of the post you want to interact with!\n";
        checkOutputContainsExpected(expected);
    }

    /**
     * Tests view student feature
     */
    @Test
    public void testViewStudent() {
        String commands =
            LOGIN_TEACHER +
                "view student\n" +
                "2\n" + "back\n" +
                EXIT_STRING;

        setIOStreams(commands);
        Main.main(new String[]{"test"});

        String expected1 = "View Student:\n" +
            "This shows all of a student's posts and lets you grade them.\n" +
            "Enter the ID of the student to view. \n" +
            "Or, please type one of these commands: \n" +
            "back\n" +
            "logout\n" +
            "> \n" +
            "s's Posts:\n" +
            "\n" +
            "Commands: back, edit [num], delete [num], grade [num]\n" +
            "Replace [num] with the number of the post you want to interact with!\n" +
            "\n" +
            "\n" +
            "|  --------------------\n" +
            "|  Post ID 0";
        String expected2 = "|  (votes: +0 | -0)\n" +
            "|  (grade: 0/100)\n" +
            "|  test post 5\n" +
            "|  --------------------\n" +
            "> \n" +
            "View Student:\n" +
            "This shows all of a student's posts and lets you grade them.\n" +
            "Enter the ID of the student to view. \n" +
            "Or, please type one of these commands: \n" +
            "back\n" +
            "logout\n" +
            ">";
        String actual = getOut().toString();
        assertThat(actual, allOf(containsString(expected1), containsString(expected2)));
    }

    /**
     * Tests delete post feature
     */
    @Test
    public void testDeletePost() {
        String commands =
            LOGIN_TEACHER +
                NAVIGATE_TO_DISCUSSION +
                "delete 0\n" +
                "yes\n" +
                EXIT_STRING;

        setIOStreams(commands);
        Main.main(new String[]{"test"});

        String expected1 = "Delete post 0:\n" +
            "Deleted posts can't be recovered. Are you sure?\n" +
            "Type yes to confirm.\n" +
            "> Post 0 has been deleted.\n" +
            "\n" +
            "Welcome to default discussion!\n" +
            "Commands: back, reply [num], edit [num], delete [num], grade [num], view voteboard, edit this forum, " +
            "delete forum, logout\n" +
            "Replace [num] with the number of the post you want to interact with!\n" +
            "\n" +
            "|  --------------------\n" +
            "|  Post ID 5\n" +
            "|  s | s (ID 2) posted at time";
        String expected2 = "|  (votes: +0 | -0)\n" +
            "|  (grade: 0/100)\n" +
            "|  test post 5\n" +
            "|  --------------------\n" +
            "> \n" +
            "Logging out:\n" +
            "You have successfully logged out.\n" +
            "\n" +
            "Welcome to our program!\n" +
            "Please type one of these commands:\n" +
            "login\n" +
            "create account\n" +
            "exit\n" +
            ">";
        String actual = getOut().toString();
        assertThat(actual, allOf(containsString(expected1), containsString(expected2)));
    }
}