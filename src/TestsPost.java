import org.junit.Test;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Test cases for Posting and access
 *
 * @author chang799
 * @version 0.1
 */
public class TestsPost extends Tests{
    private static String CREATE_TEACHER = "create account\n" + "teacher\n" + "name\n" +
            "password\n" + "t\n";
    private static String CREATE_STUDENT = "create account\n" + "student\n" + "name\n" +
            "password\n" + "s\n";
    private static String LOGIN_TEACHER = "login\n" + "teacher\n" + "password\n";
    private static String LOGIN_STUDENT = "login\n" + "student\n" + "password\n";
    private static String CREATE_DEFAULT_CLASSES = "create course\n" + "PHYS110\n" + "create " +
            "course\n" + "CS180\n" + "create " +
            "course\n" + "ENGL106\n";
    private static String CREATE_DISCUSSION = "create discussion\n" + "Lesson 1\n" + "create " +
            "discussion\n" + "Lesson 2\n" + "create discussion\n" + "Lesson 3\n";
    private static String CREATE_SINGLE_CLASS = "create course\n" + "PHYS110\n";
    private static String CREATE_DEFAULT_DISCUSSIONS = "create forum\n" + "Lesson 1\n" +
            "create forum\n" + "Lesson 2\n" + "create forum\n" + "Lesson 3\n";

    /**
     * Borrowed from RunLocal
     *
     * @param args
     */
    public static void main(String[] args) {
        Result result = JUnitCore.runClasses(TestsPost.class);
        if (result.wasSuccessful()) {
            System.out.println("Excellent - Test ran successfully");
        } else {
            for (Failure failure : result.getFailures()) {
                System.out.println(failure.toString());
            }
        }
    }
    /**
     * Tests whether students can make a reply
     * Tests whether invalid ids crash the program
     */
    @Test
    public void testReplyTeacherStudent(){
        String commands = "";
        commands += CREATE_TEACHER;
        commands += LOGIN_TEACHER;
        commands += CREATE_DEFAULT_CLASSES;
        commands += "0\n";
        commands += CREATE_DEFAULT_DISCUSSIONS;
        commands += "logout\n";
        commands += CREATE_STUDENT;
        commands += LOGIN_STUDENT;
        commands += "0\n"; // course zero
        commands += "1\n"; // discussion 1
        commands += "reply to discussion\n" + "Hello World to Discussion 1\n";
        commands += "reply to discussion\n" + "Hello World to Discussion 2\n";
        commands += "reply 0\n" + "Hello to 0\n";
        commands += "reply 1\n" + "Hello to 1\n";
        commands += "reply 2\n" + "Hello to 2\n";
        commands += "reply 20\n" + "reply -30\n";
        commands += "logout\n";
        commands += LOGIN_TEACHER;
        commands += "0\n" + "1\n";
        commands += "reply to discussion\n"; // Should not show up, checked by id of next reply
        commands += "reply 1\n" + "Hello Teacher to 1\n";
        commands += "logout\n";
        commands += "exit\n";

        // Setup Input
        setIOStreams(commands);

        // Run Program
        Main.main(new String[]{"empty"});

        // Courses should exist since they are created in this order
        String actual = getOut().toString();
        String expected1 = "Post ID 0";
        String expected2 = "Post ID 2 (reply to 0)";
        String expected3 = "Post ID 4 (reply to 2)";
        String expected4 = "Post ID 1";
        String expected5 = "Post ID 3 (reply to 1)";
        String expected6 = "Post ID 5 (reply to 1)";
        assertTrue(actual.contains(expected1));
        assertTrue(actual.contains(expected2));
        assertTrue(actual.contains(expected3));
        assertTrue(actual.contains(expected4));
        assertTrue(actual.contains(expected5));
        assertTrue(actual.contains(expected6));
    }

    /**
     * Tests if voting works properly and that each person can only vote once
     */
    @Test
    public void testVote(){
        String commands = "";
        commands += CREATE_TEACHER;
        commands += LOGIN_TEACHER;
        commands += CREATE_DEFAULT_CLASSES;
        commands += "0\n";
        commands += CREATE_DEFAULT_DISCUSSIONS;
        commands += "logout\n";
        commands += CREATE_STUDENT;
        commands += LOGIN_STUDENT;
        commands += "0\n" + "1\n";
        commands += "reply to discussion\n" + "Hello World!\n";
        commands += "upvote 0\n";
        commands += "upvote 0\n";
        commands += "downvote 0\n";
        commands += "logout\n";
        commands += "exit\n";

        // Setup Input
        setIOStreams(commands);

        // Run Program
        Main.main(new String[]{"empty"});
        String actual = getOut().toString();
        String expected1 = "(votes: +0 | -1)";
        assertTrue(actual.contains(expected1));

    }

    /**
     * Tests if teachers can properly grade
     */
    @Test
    public void testGradeTeacher(){
        String commands = "";
        commands += CREATE_TEACHER;
        commands += LOGIN_TEACHER;
        commands += CREATE_DEFAULT_CLASSES;
        commands += "0\n";
        commands += CREATE_DEFAULT_DISCUSSIONS;
        commands += "logout\n";
        commands += CREATE_STUDENT;
        commands += LOGIN_STUDENT;
        commands += "0\n" + "1\n";
        commands += "reply to discussion\n" + "Hello World!\n";
        commands += "logout\n";
        commands += LOGIN_TEACHER;
        commands += "0\n" + "1\n";
        commands += "grade 0\n";
        commands += "50\n";
        commands += "logout\n";
        commands += "exit\n";

        // Setup Input
        setIOStreams(commands);

        // Run Program
        Main.main(new String[]{"empty"});
        String actual = getOut().toString();
        String expected1 = "(grade: 50/100)";
        assertTrue(actual.contains(expected1));
    }

    /**
     * Tests if students cant
     */
    @Test
    public void testGradeStudent(){
        String commands = "";
        commands += CREATE_TEACHER;
        commands += LOGIN_TEACHER;
        commands += CREATE_DEFAULT_CLASSES;
        commands += "0\n";
        commands += CREATE_DEFAULT_DISCUSSIONS;
        commands += "logout\n";
        commands += CREATE_STUDENT;
        commands += LOGIN_STUDENT;
        commands += "0\n" + "1\n";
        commands += "reply to discussion\n" + "Hello World!\n";
        commands += "grade 0\n";
        commands += "50\n";
        commands += "logout\n";
        commands += "exit\n";

        // Setup Input
        setIOStreams(commands);

        // Run Program
        Main.main(new String[]{"empty"});
        String actual = getOut().toString();
        String expected1 = "(grade: 50/100)";
        assertFalse(actual.contains(expected1));
    }
}
