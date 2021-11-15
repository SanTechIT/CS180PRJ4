import java.io.*;
import java.sql.SQLSyntaxErrorException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Main class
 *
 * @author chang794
 * @version 0.1
 */
public class Main {
    private static boolean USESER = false;

    /**
     * @param args Command Line Arguments
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Reading Data...");
        if (USESER) {
            System.out.println("Using Saved Data");
            try {
                User.USER_LIST = (List<User>) readData("data/UserList");
                Course.COURSE_LIST = (List<Course>) readData("data/CourseList");
                Discussion.DISCUSSION_LIST = (List<Discussion>) readData("data/DiscussionList");
                Post.POST_LIST = (List<Post>) readData("data/PostList");
            } catch (IOException e) {
                System.out.println("An Error while loading data has occurred: " + e.getMessage());
            } catch (ClassNotFoundException e) {
                System.out.println("An Error while loading data has occurred: " + e.getMessage());
            }
        } else {
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

            s.upvotePost(post0);
            s.downvotePost(post1);
            s.downvotePost(post2);

            alice.upvotePost(post0);
            alice.upvotePost(post1);
            alice.downvotePost(post2);
        }
        System.out.println("Data has been read and loaded!");

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

        System.out.println("Saving Data...");
        // ON Exit Save Data
        try {
            writeData(User.USER_LIST, "data/UserList");
            writeData(Course.COURSE_LIST, "data/CourseList");
            writeData(Discussion.DISCUSSION_LIST, "data/DiscussionList");
            writeData(Post.POST_LIST, "data/PostList");
        } catch (IOException e) {
            System.out.println(
                    "An error has occured while trying to save the data: " + e.getMessage());
        }
        System.out.println("Data has been saved!");
    }

    /**
     * With inspiration from https://www.geeksforgeeks.org/serialization-in-java/
     *
     * @param obj
     * @throws FileNotFoundException
     */
    private static void writeData(Object obj, String filename) throws IOException {
        ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(filename, false));
        output.writeObject(obj);
        output.close();
    }

    /**
     * Reads
     * With inspiration from https://www.geeksforgeeks.org/serialization-in-java/
     *
     * @param filename
     * @return
     * @throws IOException
     * @throws ClassNotFoundException
     */
    private static Serializable readData(
            String filename) throws IOException, ClassNotFoundException {
        ObjectInputStream input = new ObjectInputStream(new FileInputStream(filename));
        Serializable obj = (Serializable) input.readObject();
        input.close();
        return obj;
    }
}
