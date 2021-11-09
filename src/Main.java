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
        System.out.println("Hello World!");
        Scanner scanner = new Scanner(System.in);
        // Load Course List if exits
        Course.COURSE_LIST = new ArrayList<>();
        // make new Course object, set static vars
        // Load User List if exits
        User.USER_LIST = new ArrayList<>();
        // make new User object, set static vars
        String input = scanner.nextLine();
        do {
            switch (input) {
                case "login":
                    // Login
                    // User.connect
                    break;
                case "create account":
                    // Create Account
                    // User.createAccount
                    break;
                default:
                    // Invalid input
                    break;
            }
            input = scanner.nextLine();
        } while (!input.equals("exit")); // Not Exit
    }
}
