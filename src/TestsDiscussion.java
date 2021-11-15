import org.junit.Test;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Test cases for course creation and access
 *
 * @author chang799
 * @version 0.1
 */
public class TestsDiscussion extends Tests {
    private static String CREATE_TEACHER = "create account\n" + "teacher\n" + "name\n" +
            "password\n" + "t\n";
    private static String CREATE_STUDENT = "create account\n" + "student\n" + "name\n" +
            "password\n" + "s\n";
    private static String LOGIN_TEACHER = "login\n" + "teacher\n" + "password\n";
    private static String LOGIN_STUDENT = "login\n" + "student\n" + "password\n";
    private static String CREATE_DEFAULT_CLASSES = "create course\n" + "PHYS110\n" + "create " +
            "course\n" + "CS180\n" + "create " +
            "course\n" + "ENGL106\n";
    private static String CREATE_SINGLE_CLASS = "create course\n" + "PHYS110\n";
    private static String CREATE_DEFAULT_DISCUSSIONS = "create forum\n" + "Lesson 1\n" +
            "create forum\n" + "Lesson 2\n" + "create forum\n" + "Lesson 3\n";

    /**
     * Borrowed from RunLocal
     *
     * @param args
     */
    public static void main(String[] args) {
        Result result = JUnitCore.runClasses(TestsCourse.class);
        if (result.wasSuccessful()) {
            System.out.println("Excellent - Test ran successfully");
        } else {
            for (Failure failure : result.getFailures()) {
                System.out.println(failure.toString());
            }
        }
    }

    /**
     * Check teachers can create discussions
     */
    @Test
    public void testCreateDiscussionTeacher() {
        String commands = "";
        commands += CREATE_TEACHER;
        commands += LOGIN_TEACHER;
        commands += CREATE_DEFAULT_CLASSES;
        commands += "0\n";
        commands += CREATE_DEFAULT_DISCUSSIONS;
        commands += "back\n";
        commands += "1\n";
        commands += "back\n";
        commands += "logout\n";
        commands += "exit\n";

        // Setup input
        setIOStreams(commands);

        // Run Program
        Main.main(new String[]{"empty"});

        // Courses should exist since they are created in this order
        String actual = getOut().toString();
        String expected1 = "Welcome to CS180!\n" +
                "Please type the number of a discussion forum to view:\n" +
                "There are no discussion forums.";
        String expected2 = "Welcome to PHYS110!\n" +
                "Please type the number of a discussion forum to view:\n" +
                "0 - Lesson 1\n" +
                "1 - Lesson 2\n" +
                "2 - Lesson 3";
        assertTrue(actual.contains(expected1));
        assertTrue(actual.contains(expected2));
    }

    /**
     * Check students cant create discussions
     */
    @Test
    public void testCreateDiscussionStudent() {
        String commands = "";
        commands += CREATE_STUDENT;
        commands += LOGIN_STUDENT;
        commands += CREATE_DEFAULT_CLASSES;
        commands += "0\n";
        commands += CREATE_DEFAULT_DISCUSSIONS;
        commands += "back\n";
        commands += "1\n";
        commands += "back\n";
        commands += "logout\n";
        commands += "exit\n";

        // Setup input
        setIOStreams(commands);

        // Run Program
        Main.main(new String[]{"empty"});

        // Courses should exist since they are created in this order
        String actual = getOut().toString();
        String expected1 = "Welcome to CS180!\n" +
                "Please type the number of a discussion forum to view:\n" +
                "There are no discussion forums.";
        String expected2 = "Welcome to PHYS110!\n" +
                "Please type the number of a discussion forum to view:\n" +
                "0 - Lesson 1\n" +
                "1 - Lesson 2\n" +
                "2 - Lesson 3";
        assertFalse(actual.contains(expected1));
        assertFalse(actual.contains(expected2));
    }
}
