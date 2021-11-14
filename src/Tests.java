/*
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
*/

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Tests {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Load User List if exits
        User.USER_LIST = new ArrayList<>();
        User.USER_LIST.add(new Teacher("teacher", "teacher", "John"));
        User.USER_LIST.add(new Student("student", "student", "Alice"));
        User.USER_LIST.add(new Student("s", "s", "s"));
        User.USER_LIST.add(new Teacher("t", "t", "t"));

        Course.COURSE_LIST = new ArrayList<>();
        Course.createCourse("MA165", User.USER_LIST.get(0));
        Course.createCourse("CS180", User.USER_LIST.get(0));
        Course.createCourse("EAPS106", User.USER_LIST.get(0));

        // make new Course object, set static vars

        // make new User object, set static vars
        Discussion.DISCUSSION_LIST = new ArrayList<>();
        Post.POST_LIST = new ArrayList<>();

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
    }

    // @Test
    public void testOne() {

    }
}
