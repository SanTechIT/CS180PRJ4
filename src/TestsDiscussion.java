import org.junit.Test;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Project 4 - Tests Discussion
 * <p>
 * Contains JUnit tests for testing Discussion class.
 *
 * @author Richard Chang
 * @version 2021-11-15
 */
public class TestsDiscussion extends Tests {

    /**
     * Borrowed from RunLocal
     *
     * @param args
     */
    public static void main(String[] args) {
        Result result = JUnitCore.runClasses(TestsDiscussion.class);
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
     * Check students can't create discussions
     */
    @Test
    public void testCreateDiscussionStudent() {
        String commands = "";
        commands += CREATE_STUDENT;
        commands += LOGIN_STUDENT_ALICE;
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

    /**
     * Tests whether the discussions were properly created and the student can see them
     * Tests whether invalid ids crash program
     */
    @Test
    public void testStudentCanSeeDiscussions() {
        String commands = "";
        commands += CREATE_TEACHER;
        commands += LOGIN_TEACHER;
        commands += CREATE_DEFAULT_CLASSES;
        commands += "0\n";
        commands += CREATE_DEFAULT_DISCUSSIONS;
        commands += "logout\n";
        commands += CREATE_STUDENT;
        commands += LOGIN_STUDENT_ALICE;
        commands += "1\n";
        commands += "back\n";
        commands += "100\n";
        commands += "-15\n";
        commands += "0\n";
        commands += "2\n";
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
        String expected3 = "Welcome to Lesson 3!\n" +
                "Commands: back, reply to discussion, reply [num], edit [num]upvote [num], " +
                "downvote [num], novote [num], view grades, logout\n" +
                "Replace [num] with the number of the post you want to interact with!\n" +
                "There are no posts.";
        assertTrue(actual.contains(expected1));
        assertTrue(actual.contains(expected2));
        assertTrue(actual.contains(expected3));
    }

    /**
     * Test that teachers can delete discussions
     */
    @Test
    public void testDeleteDiscussionTeacher() {
        String commands = "";
        commands += CREATE_TEACHER;
        commands += LOGIN_TEACHER;
        commands += CREATE_DEFAULT_CLASSES;
        commands += "0\n";
        commands += CREATE_DEFAULT_DISCUSSIONS;
        commands += "1\n";
        commands += "delete forum\n";
        commands += "yes\n";
        commands += "back\n";
        commands += "logout\n";
        commands += "exit\n";

        // Setup input
        setIOStreams(commands);

        // Run Program
        Main.main(new String[]{"empty"});

        // Courses should exist since they are created in this order
        String actual = getOut().toString();
        String expected1 = "Welcome to PHYS110!\n" +
                "Please type the number of a discussion forum to view:\n" +
                "0 - Lesson 1\n" +
                "1 - Lesson 2\n" +
                "2 - Lesson 3";
        String expected2 = "Welcome to PHYS110!\n" +
                "Please type the number of a discussion forum to view:\n" +
                "0 - Lesson 1\n" +
                "2 - Lesson 3";
        assertTrue(actual.contains(expected1));
        assertTrue(actual.contains(expected2));
    }

    /**
     * Check students cant delete discussions
     */
    @Test
    public void testDeleteDiscussionStudent() {
        String commands = "";
        commands += CREATE_TEACHER;
        commands += LOGIN_TEACHER;
        commands += CREATE_DEFAULT_CLASSES;
        commands += "0\n";
        commands += CREATE_DEFAULT_DISCUSSIONS;
        commands += "logout\n";
        commands += CREATE_STUDENT;
        commands += LOGIN_STUDENT_ALICE;
        commands += "0\n";
        commands += "1\n";
        commands += "delete forum\n";
        commands += "yes\n";
        commands += "back\n";
        commands += "logout\n";
        commands += "exit\n";

        // Setup input
        setIOStreams(commands);

        // Run Program
        Main.main(new String[]{"empty"});

        // Courses should exist since they are created in this order
        String actual = getOut().toString();
        String expected1 = "Welcome to PHYS110!\n" +
                "Please type the number of a discussion forum to view:\n" +
                "0 - Lesson 1\n" +
                "1 - Lesson 2\n" +
                "2 - Lesson 3";
        String expected2 = "Welcome to PHYS110!\n" +
                "Please type the number of a discussion forum to view:\n" +
                "0 - Lesson 1\n" +
                "2 - Lesson 3";
        assertTrue(actual.contains(expected1));
        assertFalse(actual.contains(expected2));
    }
}
