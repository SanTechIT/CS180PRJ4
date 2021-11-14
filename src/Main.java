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
        // Load Course List if exits
        Course.COURSE_LIST = new ArrayList<>();
        // make new Course object, set static vars
        // Load User List if exits
        User.USER_LIST = new ArrayList<>();
        // TODO DELETE ME
        User.USER_LIST.add(new Teacher("abc", "123", "John"));
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
