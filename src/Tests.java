import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Tests {
    private final InputStream originalInput = System.in;
    private final PrintStream originalOutput = System.out;
    private ByteArrayInputStream in;
    private ByteArrayOutputStream out;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Add default users to USER_LIST
        User.USER_LIST = new ArrayList<>();
        Teacher john = new Teacher("teacher", "teacher", "John");
        Student alice = new Student("student", "student", "Alice"); // ID 2 and ID 3
        Student s = new Student("s", "s", "s"); // ID 4 and ID 5
        Teacher t = new Teacher("t", "t", "t");

        // Add default courses to COURSE_LIST
        Course.COURSE_LIST = new ArrayList<>();
        Course.createCourse("MA165", User.USER_LIST.get(0));
        Course.createCourse("CS180", User.USER_LIST.get(0));
        Course.createCourse("EAPS106", User.USER_LIST.get(0));

        // make new User object, set static vars
        Discussion.DISCUSSION_LIST = new ArrayList<>();
        Discussion.createDiscussion(Course.COURSE_LIST.get(0), "default discussion",
                User.USER_LIST.get(0));

        Post.POST_LIST = new ArrayList<>();
        Post post0 = s.makeDiscussionReply("test post 0", Discussion.DISCUSSION_LIST.get(0));
        Post post1 = s.makePostReply(Post.POST_LIST.get(0), "test post 1",
                Discussion.DISCUSSION_LIST.get(0));
        Post post2 = s.makePostReply(post0, "test post 2", Discussion.DISCUSSION_LIST.get(0));
        s.makePostReply(post1, "test post 3", Discussion.DISCUSSION_LIST.get(0));
        s.makePostReply(post2, "test post 4", Discussion.DISCUSSION_LIST.get(0));
        s.makeDiscussionReply("test post 5", Discussion.DISCUSSION_LIST.get(0));
        String input;
        do {
            Display.displayStart();

            input = scanner.nextLine();
            switch (input) {
                case "login":
                    User.connect(scanner);
                    break;

                case "create account":
                    User.createAccount(scanner);
                    break;

                case "exit":
                    Display.displayExit();
                    break;

                default:
                    Display.displayBadInput();
                    break;
            }
        } while (!input.equals("exit")); // Not Exit
        // return array of objects
    }

    @BeforeEach
    public void setup() {
        out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));
    }

    @AfterEach
    public void end() {
        System.setOut(originalOutput);
        System.out.println(out);
    }

    /**
     * Login Test
     */
    @Test
    public void goodLogin() {
        // Courtesy of
        // https://stackoverflow.com/questions/6415728/junit-testing-with-simulated-user-input
        // As well as RunLocalTest.java (Various)
        String commands = "";
        commands += "login\n";
        commands += "student\n";
        commands += "student\n";
        commands += "logout\n";
        commands += "exit\n";

        // Setup IN/OUTPUT
        in = new ByteArrayInputStream(commands.getBytes());
        System.setIn(in);

        // Run Program
        main(new String[0]);

        // Restore output, print output
        assertTrue(out.toString().contains("Successfully Logged In!\n\nWelcome Alice!"));
    }

    @Test
    public void badLoginOne() {
        String commands = "";
        commands += "login\n";
        commands += "invaliduser\n";
        commands += "invaliduser\n";
        commands += "logout\n";
        commands += "exit\n";

        // Setup IN/OUTPUT
        in = new ByteArrayInputStream(commands.getBytes());
        System.setIn(in);

        // Run Program
        main(new String[0]);

        // Restore output, print output
        assertTrue(out.toString().contains("Wrong username or password"));
    }

    @Test
    public void badLoginTwo() {
        String commands = "";
        commands += "login\n";
        commands += "student\n";
        commands += "Student\n";
        commands += "logout\n";
        commands += "exit\n";

        // Setup IN/OUTPUT
        in = new ByteArrayInputStream(commands.getBytes());
        System.setIn(in);

        // Run Program
        main(new String[0]);

        // Restore output, print output
        assertTrue(out.toString().contains("Wrong username or password"));
    }

    @Test
    public void createUserAndLogInTeacher() {
        // Courtesy of
        // https://stackoverflow.com/questions/6415728/junit-testing-with-simulated-user-input
        // As well as RunLocalTest.java (Various)
        String commands = "";
        commands += "create account\n";
        commands += "user\n";
        commands += "hello world\n";
        commands += "pass\n";
        commands += "t\n";
        commands += "login\n";
        commands += "user\n";
        commands += "pass\n";
        commands += "logout\n";
        commands += "exit\n";

        // Setup IN/OUTPUT
        in = new ByteArrayInputStream(commands.getBytes());
        System.setIn(in);

        // Run Program
        main(new String[0]);

        // Restore output, print output
        assertTrue(out.toString().contains("Welcome hello world!"));
        assertTrue(out.toString().contains(
                "edit account\n" + "delete account\n" + "create course\n" + "view student"));
    }

    @Test
    public void createUserAndLogInStudent() {
        // Courtesy of
        // https://stackoverflow.com/questions/6415728/junit-testing-with-simulated-user-input
        // As well as RunLocalTest.java (Various)
        String commands = "";
        commands += "create account\n";
        commands += "user\n";
        commands += "hello world\n";
        commands += "pass\n";
        commands += "s\n";
        commands += "login\n";
        commands += "user\n";
        commands += "pass\n";
        commands += "logout\n";
        commands += "exit\n";

        // Setup IN/OUTPUT
        in = new ByteArrayInputStream(commands.getBytes());
        System.setIn(in);

        // Run Program
        main(new String[0]);

        // Restore output, print output
        assertTrue(out.toString().contains("Welcome hello world!"));
        assertTrue(out.toString().contains(
                "Or, please type one of these commands: \nedit account\ndelete account\n"));
    }

    @Test
    public void createCourse() {
        String commands = "";
        commands += "login\nt\nt\n";
        commands += "create course\n";
        commands += "ENGL106\n";
        commands += "create course\n";
        commands += "ENGR103\n";
        commands += "logout\n";
        commands += "exit\n";
        in = new ByteArrayInputStream(commands.getBytes());
        System.setIn(in);

        // Run Program
        main(new String[0]);

        // Restore output, print output
        assertTrue(out.toString().contains("3 - ENGL106"));
        assertTrue(out.toString().contains("4 - ENGR103"));
    }

    @Test
    public void createCourseStudent() {
        String commands = "";
        commands += "login\ns\ns\n";
        commands += "create course\n";
        commands += "ENGL106\n";
        commands += "create course\n";
        commands += "ENGR103\n";
        commands += "logout\n";
        commands += "exit\n";
        in = new ByteArrayInputStream(commands.getBytes());
        System.setIn(in);

        // Run Program
        main(new String[0]);

        // Restore output, print output
        assertFalse(out.toString().contains("3 - ENGL106"));
        assertFalse(out.toString().contains("4 - ENGR103"));
    }

    @Test
    public void createDiscussion() {
        String commands = "";
        commands += "login\nt\nt\n";
        commands += "create course\n";
        commands += "ENGL106\n";
        commands += "create course\n";
        commands += "ENGR103\n";
        commands += "3\n";
        commands += "create forum\n";
        commands += "Lesson 1\n";
        commands += "create forum\n";
        commands += "Lesson 2\n";
        commands += "create forum\n";
        commands += "Lesson 3\n";
        commands += "logout\n";
        commands += "exit\n";
        in = new ByteArrayInputStream(commands.getBytes());
        System.setIn(in);

        // Run Program
        main(new String[0]);

        // Restore output, print output
        assertTrue(out.toString().contains("3 - ENGL106"));
        assertTrue(out.toString().contains("4 - ENGR103"));
        assertTrue(out.toString().contains("Welcome to ENGL106!\n" + "Please type the number of a" +
                " discussion forum to view:\n" + "1 - Lesson 1\n" + "2 - Lesson 2\n" + "3 - Lesson 3"));
    }
}
