import java.util.List;
import java.util.Scanner;

/**
 * Student class
 *
 * @author [author], chang794
 * @version 0.1
 */
public class Student extends User {
    // Track posts?
    private List<Integer> posts;

    /**
     * Student Constructor
     *
     * @param username
     * @param password
     * @param name
     */
    public Student(String username, String password, String name) {
        super(username, password, name);
    }

    /**
     * User Action Loop
     *
     * @param in Scanner input
     */
    @Override
    public void loop(Scanner in) {

    }
}
