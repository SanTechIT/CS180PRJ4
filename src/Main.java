import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Project 4 - Main
 * <p>
 * Main class that runs the program.
 *
 * @author Richard Chang, Sara Xiao, Brian Kwon, Aarini Panzade
 * @version 2021-11-15
 */
public class Main {
    // whether serialization is used
    private static boolean useSer = true;

    // path separator (OS-dependent)
    private static final String pathSep = File.separator;

    /**
     * @param args Command Line Arguments
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Reading Data...");

        // Data storage path
        // Handles any args passed for testing
        String path = "data" + pathSep;
        boolean blank = false;

        // if "test" passed as command line argument,
        // serialized objects will not be loaded from data folder
        // starts program with default course/post/user info
        // contained in test folder (used for testing)
        if (args.length > 0 && args[0].equals("test")) {
            System.out.println("Using test files...");
            useSer = false;
            // save test data to different folder, so it doesn't ruin real data
            path = "test" + pathSep;

            // if "empty" passed as command line argument,
            // serialized objects will not be loaded from data folder
            // starts program with no default courses/users
        } else if (args.length > 0 && args[0].equals("empty")) {
            System.out.println("Using blank state...");
            useSer = false;
            blank = true;
            // save blank data to different folder, so it doesn't ruin real data
            path = "test" + pathSep;
        }

        // if working directory is src, add .. to path
        if (System.getProperty("user.dir").contains("CS180PRJ4" + pathSep + "src")) {
            path = ".." + pathSep + path;
        }

        // Checks if file exists before using
        boolean filesExist = new File(path + "UserList").exists() && new File(
            path + "CourseList").exists() && new File(
            path + "DiscussionList").exists() && new File(path + "PostList").exists();

        if (useSer && filesExist && !blank) {
            System.out.println("Using Saved Data");
            try {
                User.userList = (List<User>) readData(path + "UserList");
                Course.courseList = (List<Course>) readData(path + "CourseList");
                Discussion.discussionList = (List<Discussion>) readData(path + "DiscussionList");
                Post.postList = (List<Post>) readData(path + "PostList");
            } catch (IOException | ClassNotFoundException e) {
                System.out.println("An Error while loading data has occurred: " + e.getMessage());
            }
        } else if (blank) {
            User.userList = new ArrayList<>();
            Course.courseList = new ArrayList<>();
            Discussion.discussionList = new ArrayList<>();
            Post.postList = new ArrayList<>();

        } else if (path.contains("test")) { // test
            // If running tests, delete internal database and replace with default database
            // Internal Database Deleted
            System.out.println("Creating Test Database");
            User.userList = new ArrayList<>();
            Course.courseList = new ArrayList<>();
            Discussion.discussionList = new ArrayList<>();
            Post.postList = new ArrayList<>();

            System.out.println("Using Test Dataset");
            User.userList = new ArrayList<>();
            Teacher john = new Teacher("teacher", "password", "John");
            Student alice = new Student("student", "password", "Alice");
            Student s = new Student("s", "s", "s");
            Teacher t = new Teacher("t", "t", "t");

            // Add default courses to courseList
            Course.courseList = new ArrayList<>();
            Course.createCourse("MA165", User.userList.get(0));
            Course.createCourse("CS180", User.userList.get(0));
            Course.createCourse("EAPS106", User.userList.get(0));

            // make new User object, set static vars
            Discussion.discussionList = new ArrayList<>();
            Discussion.createDiscussion(Course.courseList.get(0), "default discussion",
                User.userList.get(0));

            Post.postList = new ArrayList<>();
            Post post0 = s.makeDiscussionReply("test post 0", Discussion
                .discussionList.get(0));
            Post post1 = s.makePostReply(Post.postList.get(0), "test post 1",
                Discussion.discussionList.get(0));
            Post post2 = s.makePostReply(post0, "test post 2", Discussion
                .discussionList.get(0));
            s.makePostReply(post1, "test post 3", Discussion.discussionList.get(0));
            s.makePostReply(post2, "test post 4", Discussion.discussionList.get(0));
            s.makeDiscussionReply("test post 5", Discussion.discussionList.get(0));

            s.upvotePost(post0);
            s.downvotePost(post1);
            s.downvotePost(post2);

            alice.upvotePost(post0);
            alice.upvotePost(post1);
            alice.downvotePost(post2);

            john.gradePost(post2, 33);
        } else {
            // use serialization, but no initial files, so must start from nothing

            System.out.println("Creating new database");
            User.userList = new ArrayList<>();
            Course.courseList = new ArrayList<>();
            Discussion.discussionList = new ArrayList<>();
            Post.postList = new ArrayList<>();
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
            writeData(User.userList, path + "UserList");
            writeData(Course.courseList, path + "CourseList");
            writeData(Discussion.discussionList, path + "DiscussionList");
            writeData(Post.postList, path + "PostList");
            System.out.println("Data has been saved!");
        } catch (IOException e) {
            System.out.println(
                "An error has occured while trying to save the data: " + e.getMessage());
        }
    }

    /**
     * With inspiration from https://www.geeksforgeeks.org/serialization-in-java/
     *
     * @param obj object to write to file
     * @throws FileNotFoundException if write fails
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
     * @param filename name of file to read from
     * @return object read from file
     * @throws IOException            if read fails
     * @throws ClassNotFoundException if read fails
     */
    private static Serializable readData(
        String filename) throws IOException, ClassNotFoundException {
        ObjectInputStream input = new ObjectInputStream(new FileInputStream(filename));
        Serializable obj = (Serializable) input.readObject();
        input.close();
        return obj;
    }
}
