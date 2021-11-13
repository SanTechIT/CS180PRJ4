import java.util.ArrayList;
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
        posts = new ArrayList<>();
    }

    /**
     * User Action Loop
     *
     * @param in Scanner input
     */
    @Override
    public void loop(Scanner in) {
        StudentRunner sr = new StudentRunner(this);
        sr.loop(in);
    }

    public boolean canVote() {
        return true;
    }

    public boolean canGrade() {
        return false;
    }

    public boolean canPost() {
        return true;
    }

    public boolean canCreateCourse() {
        return false;
    }

    public boolean canModifyCourse() {
        return false;
    }

    public boolean canModifyDiscussion() {
        return false;
    }

    public boolean canModifyPost() {
        return false;
    }

    public boolean isAdmins() {
        return true;
    }
}
