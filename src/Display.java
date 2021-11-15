import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator; // for sorting Discussion posts by votes
// in displayViewVoteboard

/* ----- Display methods - for displaying UI messages -----
 * These methods display messages to the user.
 * None of these methods change anything.
 *
 * For Project 5, can be modified to use GUI (handling input will be
 * trickier, though).
 *
 * @author Sara Xiao
 * @version 2021-11-13
 */
public class Display {
    /**
     * Displays output for main login/create account loop
     */
    public static void displayStart() {
        System.out.println(
                "\nWelcome to our program!" + "\nPlease type one of these commands:" + "\nlogin" + "\ncreate account" + "\nexit");

        System.out.print("> ");
    }

    /**
     * Displays output for User main loop (viewing all courses after login)
     */
    public static void displayWelcome(User user) {
        System.out.println(
                "\nWelcome " + user.getName() + "!" + "\nPlease type the number of a course to view:");

        String courseList = Course.getCoursesString();
        if (courseList.isEmpty()) {
            System.out.println("There are no courses.");
        } else {
            System.out.println(courseList);
        }

        if (user instanceof Student) {
            System.out.println(
                    "Or, please type one of these commands: " + "\nedit account" + "\ndelete account" + "\nlogout");
        } else {
            System.out.println(
                    "Or, please type one of these commands: " + "\nedit account" + "\ndelete account" + "\ncreate course" + "\nview student" + "\nlogout");
        }

        System.out.print("> ");
    }

    /**
     * Displays output for invalid input
     */
    public static void displayBadInput() {
        System.out.println(
                "\nInput Error:" + "\nSorry, I couldn't understand what you typed. Please try again!" + "\n-----");
    }

    /**
     * Displays output for program exit
     */
    public static void displayExit() {
        System.out.println("\nExit:" + "\nThank you for using our program. Goodbye!");
    }

    /**
     * Displays output for logging out
     */
    public static void displayLogout() {
        System.out.println("\nLogging out:" + "\nYou have successfully logged out.");
    }

    /**
     * Displays output for creating a course (accessed from main menu)
     */
    public static void displayCreateCourse() {
        System.out.println("\nCreating Course:" + "\nPlease enter the name of the new course:");

        System.out.print("> ");
    }

    /**
     * Displays output for course loop (viewing all discussions in 1 course)
     */
    public static void displayCourse(Course currentCourse, User user) {
        System.out.println(
                "\nWelcome to " + currentCourse.getTopic() + "!" + "\nPlease type the number of a discussion forum to view:");

        String discussionList = currentCourse.getDiscussionsString();
        if (discussionList.isEmpty()) {
            System.out.println("There are no discussion forums.");
        } else {
            System.out.println(discussionList);
        }

        System.out.println("Or, please type one of these commands: ");
        if (user instanceof Student) {
            System.out.println("\nback" + "\nlogout");
        } else {
            System.out.println("\nback" + "\ncreate forum" + "\nedit this course" + "\nlogout");
        }

        System.out.print("> ");
    }

    // Displays output for creating a discussion
    public static void displayCreateDiscussion() {
        System.out.println(
                "\nCreating Discussion:" + "\nPlease enter the name of the new discussion:");

        System.out.print("> ");
    }

    // Displays output for editing a course topic
    public static void displayEditCourse() {
        System.out.println(
                "\nEditing Course Topic:" + "\nPlease enter the new topic of this course:");

        System.out.print("> ");
    }

    /**
     * Displays output for discussion loop (viewing all posts in 1 discussion)
     * Called by displayDiscussionTeacher and displayDiscussionStudent
     */
    public static void displayDiscussion(Discussion currentDiscussion, User user) {
        String commands;
        if (user instanceof Student) {
            commands = "Commands: " + "back, reply to discussion, reply [num], edit [num], delete [num], " + "upvote [num], downvote [num], novote [num], logout";
        } else {
            commands = "Commands: " + "back, reply [num], edit [num], delete [num], " + "grade [num], view voteboard, delete forum, logout";
        }

        System.out.println(
                "\nWelcome to " + currentDiscussion.getTopic() + "!" + "\n" + commands + "\nReplace [num] with the number of the post you " + "want to interact with!");

        String postList = getDiscussionString(currentDiscussion, user);
        if (postList.isEmpty()) {
            System.out.println("There are no posts.");
        } else {
            System.out.println(postList);
        }

        System.out.print("> ");
    }

    /**
     * Gets the posts of direct child in discussion and return as string
     *
     * @param discussion
     * @param user
     * @return
     */
    private static String getDiscussionString(Discussion discussion, User user) {
        String postString = "";
        for (Integer postId : discussion.getPosts()) {
            Post post = Post.POST_LIST.get(postId);
            postString += getPostStrings(post, 0, user);
        }
        return postString;
    }

    /**
     * Gets the posts and nested posts and returns a string representation
     *
     * @param postin
     * @param indent
     * @param user
     * @return
     */
    private static String getPostStrings(Post postin, int indent, User user) {
        String postString = "";
        if(postin == null){
            return "";
        }
        postString += getPostString(postin, indent, user);
        for (int postId : postin.getPosts()) {
            Post post = Post.POST_LIST.get(postId);
            postString += getPostStrings(post, indent + 1, user);
        }
        return postString;
    }

    /**
     * Returns the string representation of a post
     *
     * @param postin
     * @param indent
     * @param user
     * @return
     */
    private static String getPostString(Post postin, int indent, User user) {
        if (postin == null) {
            return "";
        }
        String postString = "";
        String indentStr = "|  ";
        for(int i = 0; i < indent; i++){
            indentStr += " ".repeat(4) + "|  ";
        }

        postString += "\n" + indentStr + "--------------------\n";

        postString += indentStr + "Post ID " + postin.getId();
        if (postin.getParent() != -1) {
            postString += " (reply to " + postin.getParent() + ")";
        }
        postString += "\n";
        User puser = User.USER_LIST.get(postin.getCreatorId());
        postString += indentStr + puser.getUsername() + " | " + puser.getName() + " " + "(ID " + puser.getId() + ") posted";
        postString += " at time " + postin.getTimestamp().toString() + "\n";

        postString += indentStr + "(votes: +" + postin.getUpvotes() + " | -" + postin.getDownvotes() + ")\n";
        if (postin.getCreatorId() == user.getId() || user.canGrade()) {
            postString += indentStr + "(grade: " + postin.getGrade() + "/" + postin.getMaxGrade() + ")\n";
        }

        postString += indentStr + postin.getContent() + "\n";
        postString += indentStr + "--------------------";
        return postString;
    }

    /**
     * Displays menu for replying directly to discussion (Student only)
     */
    public static void displayDiscussionReply(Discussion currentDiscussion) {
        System.out.println(
                "\nReply to discussion - " + currentDiscussion.getTopic() + ": " + "\nYou are replying directly to this discussion." + "\nWhat should be the content in your new post?");

        System.out.print("> ");
    }

    /**
     * Displays menu for replying to post
     */
    public static void displayPostReply(Post targetPost) {
        System.out.println(
                "\nReply to post " + targetPost.getId() + ":" + "\nYou are replying to an existing post in the discussion." + "\nWhat should be the content in your new reply post?");

        System.out.print("> ");
    }

    /**
     * Displays menu for editing post
     */
    public static void displayEditPost(Post targetPost) {
        int id = targetPost.getId();
        System.out.println(
                "\nEdit post " + id + ":" + "\nWhat should be the new content in post " + id + "?");

        System.out.print("> ");
    }

    /**
     * Displays menu for deleting post
     */
    public static void displayDeletePost(Post targetPost) {
        System.out.println(
                "\nDelete post " + targetPost.getId() + ":" + "\nDeleted posts can't be recovered. Are you sure?" + "\nType yes to confirm.");

        System.out.print("> ");
    }

    /**
     * Displays menu for grading post
     */
    public static void displayGradePost(Post targetPost) {
        int id = targetPost.getId();
        System.out.println(
                "\nGrade post " + id + ":" + "\nThe minimum grade is 0," + "and the maximum grade is " + targetPost.getMaxGrade() + "." + "\nEnter the grade to assign to post " + id + ":");

        System.out.print("> ");
    }

    /**
     * Displays output for edit account loop (an option from the main loop)
     */
    public static void displayEditAccount(User user) {
        System.out.println(
                "\nEditing Your Account - " + user.getUsername() + "\nPlease type one of these commands: " + "\nback" + "\nchange username" + "\nchange name" + "\nchange password" + "\nlogout");

        System.out.print("> ");
    }

    /**
     * Displays output for modifying the username
     */
    public static void displayModifyUsername(User user) {
        System.out.println("Editing Your Account - " + user.getUsername() + ":");

        System.out.println("Current username: " + user.getUsername());

        System.out.println(
                "What would you like your new username to be? It can't be an already existing username");
    }

    /**
     * Displays output for modifying the name
     */
    public static void displayModifyName(User user) {
        System.out.println("Editing Your Account - " + user.getUsername() + ":");

        System.out.println("Current name: " + user.getName());

        System.out.println("What would you like your new name to be?");
    }

    /**
     * Displays output for modifying the password
     */

    public static void displayModifyPassword(User user) {
        System.out.println("Editing Your Account - " + user.getUsername() + ":");

        System.out.println("What would you like your new password to be?");
    }

    /**
     * Displays output for deleting an account
     */
    public static void displayDeleteAccount(User user) {
        System.out.println(
                "Delete Account - " + user.getUsername() + ":" + "\nDeleted accounts can't be recovered. Are you sure you want to do this?" + " Type yes to confirm.");
    }

    /**
     * Displays output for view student loop (option from the main loop)
     */
    public static void displayViewStudent() {
        System.out.println(
                "\nView Student:" + "\nThis shows all of a student's posts and lets you grade them." + "\nEnter the ID of the student to view. " + "\nOr, please type one of these commands: " + "\nback" + "\nlogout");

        System.out.print("> ");
    }

    /**
     * Displays output for an individual student
     */
    public static void displayIndividualStudent(Student currentStudent) {
        System.out.println("\n" + currentStudent.getName() + "'s Posts:");

        System.out.println(
                "\nCommands: " + "back, edit [num], delete [num], grade [num]" + "\nReplace [num] with the number of the post you " + "want to interact with!");

        String postList = currentStudent.getPostsString();
        if (postList.isEmpty()) {
            System.out.println("There are no posts.");
        } else {
            System.out.println(postList);
        }

        System.out.print("> ");
    }

    private static void displayPostsVoteboard(List<Integer> posts, User user) {
        String str = "\n";

        for (Integer pid : posts) {
            Post p = Post.POST_LIST.get(pid);
            str += getPostString(p, 0, user);
        }

        System.out.println(str);
    }

    public static void displayViewVoteboard(Discussion currentDiscussion, String currentSort, User user) {
        System.out.println(
                "Voteboard: discussion - " + currentDiscussion.getTopic() + "\nCommands: back, sort best, sort worst, sort controversial" + "\nThe voteboard displays posts in a forum by vote count." + "\nCurrent sort: " + currentSort);

        List<Integer> posts = currentDiscussion.getAllPosts();
        if (posts.size() == 0) {
            System.out.println("There are no posts.");
        } else {

            switch (currentSort) {
                case "best":
                    Collections.sort(posts, new Comparator<Integer>() {
                        public int compare(Integer p1id, Integer p2id) {
                            Post p1 = Post.POST_LIST.get(p1id);
                            Post p2 = Post.POST_LIST.get(p2id);
                            return p2.getVotes() - p1.getVotes();
                        }
                    });
                    break;

                case "worst":
                    Collections.sort(posts, new Comparator<Integer>() {
                        public int compare(Integer p1id, Integer p2id) {
                            Post p1 = Post.POST_LIST.get(p1id);
                            Post p2 = Post.POST_LIST.get(p2id);
                            return -(p2.getVotes() - p1.getVotes());
                        }
                    });
                    break;

                case "controversial":
                    Collections.sort(posts, new Comparator<Integer>() {
                        public int compare(Integer p1id, Integer p2id) {
                            Post p1 = Post.POST_LIST.get(p1id);
                            Post p2 = Post.POST_LIST.get(p2id);
                            return - (p2.getControversy() - p1.getControversy());
                        }
                    });
                    break;
            }
            displayPostsVoteboard(posts, user);
        }

        System.out.print("> ");
    }
}
