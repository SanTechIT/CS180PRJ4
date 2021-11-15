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
        Discussion.createDiscussion(Course.COURSE_LIST.get(0), "default discussion", User.USER_LIST.get(0));

        Post.POST_LIST = new ArrayList<>();
        Post post0 = s.makeDiscussionReply("test post 0", Discussion.DISCUSSION_LIST.get(0));
        Post post1 = s.makePostReply(Post.POST_LIST.get(0), "test post 1", Discussion.DISCUSSION_LIST.get(0));
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
    }

    // @Test
    public void testOne() {

    }
}
