import java.io.Serializable;
import java.sql.SQLOutput;
import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * User Class
 * <p>
 * Represents a user of the program.
 *
 * @author aarinipanzade, chang794, saraxiao0
 * @version 0.1
 */
public abstract class User implements Serializable {
    private List<Integer> posts; // ID of every post the User has made

    // As per https://stackoverflow.com/
    // questions/10378855/java-io-invalidclassexception-local-class-incompatible
    private static final long serialVersionUID = 01L;

    public static List<User> USER_LIST;
    private int id;
    private String username;
    private String password;
    private String name;



    // this is the User constructor
    public User(String username, String password, String name) {
        this.username = username;
        this.password = password;
        this.name = name;
        // TODO: NOT CONCURRENT SAFE : )
        id = USER_LIST.size();
        USER_LIST.add(this);

        posts = new ArrayList<>();
    }

    // getter and setter methods
    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public String getPassword() {
        return password;
    }

    public List<Integer> getPosts() {
        return posts;
    }

    // this connect method is for the user to log in with their credentials
    public static void connect(Scanner in) {
        System.out.println("Enter your username: ");
        String username = in.nextLine();

        System.out.println("Enter your password: ");
        String password = in.nextLine();

        User user = getUser(username);

        if (user == null) {
            System.out.println("Wrong username or password");
            return;
        } else if (!password.equals(user.getPassword())) {
            System.out.println("Wrong username or password");
            return;
        } else {
            System.out.println("Successfully Logged In!");
        }

        user.loop(in);
    }

    // this method creates the account for the certain user
    public static void createAccount(Scanner in) {
        System.out.println("Enter your username: ");
        String username = in.nextLine();

        System.out.println("Enter your name: ");
        String name = in.nextLine();

        System.out.println("Create your password: ");
        String password = in.nextLine();

        System.out.println("Select if you are a [T]eacher or [S]tudent: ");
        String userType = in.nextLine();

        if (userType.equalsIgnoreCase("T")) {
            new Teacher(username, password, name);
        } else if (userType.equalsIgnoreCase("S")) {
            new Student(username, password, name);
        }
    }


    // get user method which finds the user in the user list
    private static User getUser(String username) {
        for (User user : USER_LIST) {
            if (username.equals(user.getUsername())) {
                return user;
            }
        }
        return null;
    }

    // this method changes the username if the user wants to edit it to a new one
    public void modifyUsername(Scanner in) {
        Display.displayModifyUsername(this);

        String newUsername = in.nextLine();

        User newUser = User.getUser(newUsername);

        if (newUser == null) {
            username = newUsername;
            System.out.println("Congratulations! You have changed your username.");
        } else {
            System.out.println("Sorry! You can't use that username.");
        }
    }

    // this method changes the name if the user wants to edit it to a new one
    public void modifyName(Scanner in) {
        Display.displayModifyName(this);

        String newName = in.nextLine();

        name = newName;

        System.out.println("Congratulations! You have changed your name.");
    }

    // this method changes the password if the user wants to edit it to a new one
    public void modifyPassword(Scanner in) {
        Display.displayModifyPassword(this);

        String newPassword = in.nextLine();

        password = newPassword;

        System.out.println("Congratulations! You have changed your password.");
    }

    // this method deletes the account if the user wants to delete their account
    public void deleteAccount(Scanner in) {
        Display.displayDeleteAccount(this);

        String deleteConfirm = in.nextLine();

        if (deleteConfirm.equalsIgnoreCase("yes")) {
            USER_LIST.remove(this);
            System.out.println(
                    "Your account has been deleted. Welcome to the Learning Management Discussion Board!");
        }
    }


    /**
     * Reply to a reply to a discussion forum
     *
     * @param parentPost       parent post the new post is replying to
     * @param newContent       content of new post
     * @param parentDiscussion discussion forum that contains both posts
     */
    public Post makePostReply(Post parentPost, String newContent, Discussion parentDiscussion) {
        Post p = Post.createPost(newContent, parentDiscussion, parentPost, this);

        int postId = p.getId();
        if (!posts.contains(postId)) {
            posts.add(p.getId());
        }

        return p;
    }

    /**
     * Edit a post
     *
     * @param targetPost post to be edited
     * @param newContent new content of post
     */
    public boolean editPost(Post targetPost, String newContent) {
        return targetPost.editPost(newContent, this);
    }

    /**
     * Delete a post
     *
     * @param targetPost post to be deleted
     */
    public boolean deletePost(Post targetPost) {
        return (targetPost.deletePost(this) != null);
    }

    /**
     * Get Vote Count
     *
     * @return voteCount which is the total number of upvotes and downvotes together
     */
    public int getVoteCount() {
        int voteCount = 0;
        for (int post : getPosts()) {
            Post p = Post.POST_LIST.get(post);
            voteCount += p.getUpvotes();
            voteCount -= p.getDownvotes();
        }
        return voteCount;
    }

    /**
     * User Action Loop
     *
     * @param in Scanner input
     */
    public abstract void loop(Scanner in);

    public abstract boolean canVote();

    public abstract boolean canGrade();

    public abstract boolean canPost();

    public abstract boolean canCreateCourse();

    public abstract boolean canModifyCourse();

    public abstract boolean canCreateDiscussion();

    public abstract boolean canModifyDiscussion();

    public abstract boolean canModifyPost();
}
