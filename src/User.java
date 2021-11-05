import java.util.List;
import java.util.Scanner;

/**
 * User Class
 *
 * @author [author], chang794
 * @version 0.1
 */
public abstract class User {
    public static List<User> USER_LIST;

    public static void connect(Scanner in) {
        // Get User information
        // Find User in USER_LIST
        // if null
        // Call User's loop method.
    }

    // login method

    // get user method
    private static User getUser(Scanner in) {
        // tries to find user in USER_LIST with username/password
        // if cant returns null
        return null;
    }

    /**
     * User Action Loop
     *
     * @param in Scanner input
     */
    public abstract void loop(Scanner in);
}
