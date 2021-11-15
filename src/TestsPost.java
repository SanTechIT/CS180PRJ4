import org.junit.Test;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

/**
 * Test cases for Posting and access
 *
 * @author chang799
 * @version 0.1
 */
public class TestsPost {
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

    @Test
    public void testPostStudent(){
        String commands = "";
        commands += CREATE_TEACHER;
        commands += LOGIN_TEACHER;
        commands += CREATE_DEFAULT_CLASSES;
        commands += "logout\n";
        commands += CREATE_STUDENT;
        commands += LOGIN_STUDENT;
        commands += "1";

        String expected1 = "Welcome to CS180!";

    }
}
