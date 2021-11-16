import org.junit.Test;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

import static org.junit.Assert.*;

/**
 * Project 4 - Tests Course
 * <p>
 * Contains JUnit tests for testing Main class.
 *
 * @author Richard Chang
 * @version 2021-11-16
 */
public class TestsCourse extends Tests {

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
     * Checks if teacher can create courses
     */
    @Test
    public void testCreateCourseTeacher() {
        String commands = "";
        commands += CREATE_TEACHER;
        commands += LOGIN_TEACHER;
        commands += CREATE_DEFAULT_CLASSES;
        commands += "logout\n";
        commands += "exit\n";

        setIOStreams(commands);

        // Run Program
        Main.main(new String[]{"empty"});

        // Courses should exist since they are created in this order
        String actual = getOut().toString();
        String expected1 = "0 - PHYS110\n";
        String expected2 = "1 - CS180\n";
        String expected3 = "2 - ENGL106\n";
        String expected4 = "0 - PHYS110\n" + "1 - CS180\n" + "2 - ENGL106";
        assertTrue(actual.contains(expected1));
        assertTrue(actual.contains(expected2));
        assertTrue(actual.contains(expected3));
        assertTrue(actual.contains(expected4));

    }

    /**
     * Tests if a student can create courses (should not)
     */
    @Test
    public void testCreateCourseStudent() {
        String commands = "";
        commands += CREATE_STUDENT;
        commands += LOGIN_STUDENT_ALICE;
        commands += CREATE_DEFAULT_CLASSES;
        commands += "logout\n";
        commands += "exit\n";

        setIOStreams(commands);

        // Run Program
        Main.main(new String[]{"empty"});

        String actual = getOut().toString();

        // None of these classes should show, given that they were never created
        String expected7 = "PHYS110\n";
        String expected8 = "CS180\n";
        String expected9 = "ENGL106\n";
        assertFalse(actual.contains(expected7));
        assertFalse(actual.contains(expected8));
        assertFalse(actual.contains(expected9));
    }

    /**
     * Tests if the user can properly see all the courses created
     * Tests if invalid course ID Crashes
     */
    @Test
    public void testAccessCourseStudent() {
        String commands = "";
        commands += CREATE_TEACHER;
        commands += LOGIN_TEACHER;
        commands += CREATE_DEFAULT_CLASSES;
        commands += "logout\n";
        commands += CREATE_STUDENT;
        commands += LOGIN_STUDENT_ALICE;
        commands += "0\n";
        commands += "back\n";
        commands += "1\n";
        commands += "back\n";
        commands += "2\n";
        commands += "back\n";
        commands += "3\n";
        commands += "100\n";
        commands += "-3\n";
        commands += "logout\n";
        commands += "exit\n";

        setIOStreams(commands);

        // Run Program
        Main.main(new String[]{"empty"});

        // All created courses should be visible to student
        // Student should be able to access all courses
        String actual = getOut().toString();
        String expected1 = "0 - PHYS110\n" + "1 - CS180\n" + "2 - ENGL106";
        String expected2 = "Welcome to PHYS110!";
        String expected3 = "Welcome to CS180!";
        String expected4 = "Welcome to ENGL106!";
        String expected5 = "Sorry, I couldn't understand what you typed. Please try again!";
        assertTrue(actual.contains(expected1));
        assertTrue(actual.contains(expected2));
        assertTrue(actual.contains(expected3));
        assertTrue(actual.contains(expected4));
        assertTrue(actual.contains(expected5));
    }

    /**
     * Checks if teacher can delete courses
     */
    @Test
    public void testDeleteCourseTeacher() {
        String commands = "";
        commands += CREATE_TEACHER;
        commands += LOGIN_TEACHER;
        commands += CREATE_SINGLE_CLASS;
        commands += "0\n";
        commands += "delete course\n";
        commands += "yes\n";
        commands += "logout\n";
        commands += "exit\n";

        setIOStreams(commands);

        // Run Program
        Main.main(new String[]{"empty"});

        // Courses should exist since they are created in this order
        String expected1 = "Delete course 0:\n" +
            "Deleted courses can't be recovered. Are you sure?\n" +
            "Type yes to confirm.\n" +
            "> Course deleted successfully!\n" +
            "\n" +
            "Welcome name!\n" +
            "Please type the number of a course to view:\n" +
            "There are no courses.";
        checkOutputContainsExpected(expected1);

    }
}
