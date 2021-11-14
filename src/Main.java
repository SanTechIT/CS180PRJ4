import java.util.ArrayList;
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
        User.USER_LIST = new ArrayList<>();
        User.USER_LIST.add(new Teacher("teacher", "teacher", "John"));
        User.USER_LIST.add(new Student("student", "student", "Alice"));

        // Load Course List if exits
        Course.COURSE_LIST = new ArrayList<>();
        Course.createCourse("MA165", User.USER_LIST.get(0));
        Course.createCourse("CS180", User.USER_LIST.get(0));
        Course.createCourse("EAPS106", User.USER_LIST.get(0));

        // make new Course object, set static vars

        // make new User object, set static vars
        Discussion.DISCUSSION_LIST = new ArrayList<>();

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
}
