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
    /**
     * @param args Command Line Arguments
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Load User List if exits
        // TODO DELETE ME
//        User.USER_LIST = new ArrayList<>();
//        User.USER_LIST.add(new Teacher("teacher", "teacher", "John"));
//        User.USER_LIST.add(new Student("student", "student", "Alice"));
        System.out.println("Reading Data...");
        try {
            User.USER_LIST = (List<User>) readData("data/UserList");
            Course.COURSE_LIST = (List<Course>) readData("data/CourseList");
        } catch (IOException e){
            // TODO
        } catch (ClassNotFoundException e){
            // TODO
        }
        System.out.println("Data has been read and loaded!");

//         Load Course List if exits
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

        System.out.println("Saving Data...");
        // ON Exit Save Data
        try {
            writeData(User.USER_LIST, "data/UserList");
            writeData(Course.COURSE_LIST, "data/CourseList");
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
