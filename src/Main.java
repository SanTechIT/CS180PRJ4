import java.util.Scanner;

/**
 * Main class
 */
public class Main {
    /**
     * @param args Command Line Arguments
     */
    public static void main(String[] args) {
        System.out.println("Hello World!");
        Scanner scanner = new Scanner(System.in);
        // Load Course List if exits
        // make new Course object, set static vars
        // Load User List if exits
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
