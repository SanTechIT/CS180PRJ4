import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
// for sorting Discussion posts by votes
// in displayViewVoteboard

/**
 * Project 4 - Display
 * <p>
 * Utility class for displaying output/UI messages.
 * These methods display messages to the user.
 * None of these methods change anything.
 * <p>
 * For Project 5, can be modified to use GUI (handling input will be
 * trickier, though).
 *
 * @author Sara Xiao, Richard Chang, Aarini Panzade
 * @version 2021-11-15
 */
public class Display {
    /**
     * Displays output for main login/create account loop
     */
    public static void displayStart() {
        System.out.println(
            "\nWelcome to our program!" + "\nPlease type one of these commands:" + "\nlogin" + "\ncreate account" +
                "\nexit");

        System.out.print("> ");
    }

    /**
     * Displays output for User main loop (viewing all courses after login)
     *
     * @param user User viewing output
     */
    public static void displayWelcome(User user) {
        System.out.println(
            "\nWelcome " + user.getName() + "!" + "\nPlease type the number of a course to " +
                "view:");

        String courseList = Course.getCoursesString();
        if (courseList.isEmpty()) {
            System.out.println("There are no courses.");
        } else {
            System.out.println(courseList);
        }

        if (user instanceof Student) {
            System.out.println(
                "Or, please type one of these commands: " + "\nedit account" + "\ndelete " +
                    "account" + "\nlogout");
        } else {
            System.out.println(
                "Or, please type one of these commands: " + "\nedit account" + "\ndelete " +
                    "account" + "\ncreate course" + "\nview student" + "\nlogout");
        }

        System.out.print("> ");
    }

    /**
     * Displays output for invalid input
     */
    public static void displayBadInput() {
        System.out.println(
            "\nInput Error:" + "\nSorry, I couldn't understand what you typed. Please try " +
                "again!" + "\n-----");
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
     *
     * @param currentCourse course being viewed
     * @param user          user viewing the course
     */
    public static void displayCourse(Course currentCourse, User user) {
        System.out.println(
            "\nWelcome to " + currentCourse.getTopic() + "!" + "\nPlease type the number of a" +
                " discussion forum to view:");

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
            System.out.println(
                "\nback" + "\ncreate forum" + "\nedit this course" + "\ndelete course" +
                    "\nlogout");
        }

        System.out.print("> ");
    }

    /**
     * Displays output for creating a new discussion
     */
    public static void displayCreateDiscussion() {
        System.out.println(
            "\nCreating Discussion:" + "\nPlease enter the name of the new discussion:");

        System.out.print("> ");
    }

    /**
     * Displays output for editing a discussion topic
     */
    public static void displayEditDiscussion() {
        System.out.println(
            "\nEditing Discussion Topic:" + "\nPlease enter the new topic of this discussion:");

        System.out.print("> ");
    }

    /**
     * Displays output for discussion loop (viewing all posts in 1 discussion)
     *
     * @param currentDiscussion discussion being viewed
     * @param user              user viewing discussion
     */
    public static void displayDiscussion(Discussion currentDiscussion, User user) {
        String commands;
        if (user instanceof Student) {
            commands = "Commands: " + "back, reply to discussion, reply [num], edit [num]" +
                "upvote [num], downvote [num], novote [num], view grades, logout";
        } else {
            commands = "Commands: " + "back, reply [num], edit [num], delete [num], " + "grade " +
                "[num], view voteboard, edit this forum, delete forum, logout";
        }

        System.out.println(
            "\nWelcome to " + currentDiscussion.getTopic() + "!" + "\n" + commands +
                "\nReplace [num] with the number of the post you " + "want to interact " +
                "with!");

        String postList = getDiscussionString(currentDiscussion, user);
        if (postList.isEmpty()) {
            System.out.println("There are no posts.");
        } else {
            System.out.println(postList);
        }

        System.out.print("> ");
    }

    /**
     * Returns formatted String representing all posts in Discussion
     *
     * @param discussion discussion to return String for
     * @param user       user viewing discussion
     * @return formatted String
     */
    private static String getDiscussionString(Discussion discussion, User user) {
        String discussionString = "";
        for (Integer postId : discussion.getPosts()) {
            Post post = Post.POST_LIST.get(postId);
            discussionString += getPostStrings(post, 0, user);
        }
        return discussionString;
    }

    /**
     * Gets string representation of 1 post + all its replies
     *
     * @param postin parent post
     * @param indent level of indent for reply posts
     * @param user   user getting this info
     * @return string representing posts
     */
    private static String getPostStrings(Post postin, int indent, User user) {
        String postString = "";
        if (postin == null) {
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
     * @param postin post to get representation of
     * @param indent level of indentation
     * @param user   user getting this info
     * @return string representing posts
     */
    private static String getPostString(Post postin, int indent, User user) {
        if (postin == null) {
            return "";
        }
        String postString = "";
        String indentStr = "|  ";
        for (int i = 0; i < indent; i++) {
            indentStr += " ".repeat(4) + "|  ";
        }

        postString += "\n" + indentStr + "--------------------\n";

        postString += indentStr + "Post ID " + postin.getId();
        if (postin.getParent() != -1) {
            postString += " (reply to " + postin.getParent() + ")";
        }
        postString += "\n";
        User puser = User.USER_LIST.get(postin.getCreatorId());
        postString += indentStr + puser.getUsername() + " | " + puser.getName() + " " + "(ID " + puser.getId() + ") " +
            "posted";
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
     * Displays output for deleting a course
     *
     * @param currentCourse course to delete
     */
    public static void displayDeleteCourse(Course currentCourse) {
        System.out.println(
            "\nDelete course " + currentCourse.getId() + ":" + "\nDeleted courses can't be " +
                "recovered. Are you sure?" + "\nType yes to confirm.");

        System.out.print("> ");
    }

    /**
     * Displays output for editing a course topic
     */
    public static void displayEditCourse() {
        System.out.println(
            "\nEditing Course Topic:" + "\nPlease enter the new topic of this course:");

        System.out.print("> ");
    }

    /**
     * Displays menu for replying directly to discussion (Student only)
     *
     * @param currentDiscussion discussion Student is replying to
     */
    public static void displayDiscussionReply(Discussion currentDiscussion) {
        System.out.println(
            "\nReply to discussion - " + currentDiscussion.getTopic() + ": " + "\nYou are " +
                "replying directly to this discussion." + "\nWhat should be the content " +
                "in your new post?");

        System.out.print("> ");
    }

    /**
     * Displays menu for replying to post
     *
     * @param targetPost post User is replying to
     */
    public static void displayPostReply(Post targetPost) {
        System.out.println(
            "\nReply to post " + targetPost.getId() + ":" + "\nYou are replying to an " +
                "existing post in the discussion." + "\nWhat should be the content in " +
                "your new reply post?");

        System.out.print("> ");
    }

    /**
     * Displays menu for editing post
     *
     * @param targetPost Post User is editing
     */
    public static void displayEditPost(Post targetPost) {
        int id = targetPost.getId();
        System.out.println(
            "\nEdit post " + id + ":" + "\nWhat should be the new content in post " + id + "?");

        System.out.print("> ");
    }

    /**
     * Displays menu for deleting post
     *
     * @param targetPost Post user is deleting (only teachers have permission to)
     */
    public static void displayDeletePost(Post targetPost) {
        System.out.println(
            "\nDelete post " + targetPost.getId() + ":" + "\nDeleted posts can't be recovered" +
                ". Are you sure?" + "\nType yes to confirm.");

        System.out.print("> ");
    }

    /**
     * Displays menu for grading post
     *
     * @param targetPost post user is grading
     */
    public static void displayGradePost(Post targetPost) {
        int id = targetPost.getId();
        System.out.println(
            "\nGrade post " + id + ":" + "\nThe minimum grade is 0," + "and the maximum grade" +
                " is " + targetPost.getMaxGrade() + "." + "\nEnter the grade to assign to" +
                " post " + id + ":");

        System.out.print("> ");
    }

    /**
     * Displays output for edit account loop (an option from the main loop)
     *
     * @param user user who is editing account
     */
    public static void displayEditAccount(User user) {
        System.out.println(
            "\nEditing Your Account - " + user.getUsername() + "\nPlease type one of these " +
                "commands: " + "\nback" + "\nchange username" + "\nchange name" +
                "\nchange password" + "\nlogout");

        System.out.print("> ");
    }

    /**
     * Displays output for modifying the username
     *
     * @param user user who's modifying username
     */
    public static void displayModifyUsername(User user) {
        System.out.println("Editing Your Account - " + user.getUsername() + ":");

        System.out.println("Current username: " + user.getUsername());

        System.out.println(
            "What would you like your new username to be? It can't be an already existing " +
                "username");
    }

    /**
     * Displays output for modifying the name
     *
     * @param user user who's modifying name
     */
    public static void displayModifyName(User user) {
        System.out.println("Editing Your Account - " + user.getUsername() + ":");

        System.out.println("Current name: " + user.getName());

        System.out.println("What would you like your new name to be?");
    }

    /**
     * Displays output for modifying the password
     *
     * @param user user who's modifying password
     */

    public static void displayModifyPassword(User user) {
        System.out.println("Editing Your Account - " + user.getUsername() + ":");

        System.out.println("What would you like your new password to be?");
    }

    /**
     * Displays output for deleting an account
     *
     * @param user user who's deleting account
     */
    public static void displayDeleteAccount(User user) {
        System.out.println(
            "Delete Account - " + user.getUsername() + ":" + "\nDeleted accounts can't be " +
                "recovered. Are you sure you want to do this?" + " Type yes to confirm.");
    }

    /**
     * Displays output for view student loop (option from the main loop)
     */
    public static void displayViewStudent() {
        System.out.println(
            "\nView Student:" + "\nThis shows all of a student's posts and lets you grade " +
                "them." + "\nEnter the ID of the student to view. " + "\nOr, please type " +
                "one of these commands: " + "\nback" + "\nlogout");

        System.out.print("> ");
    }

    /**
     * Displays output for an individual student
     *
     * @param currentStudent current student being viewed
     */
    public static void displayIndividualStudent(Student currentStudent, Teacher currentTeacher) {
        System.out.println("\n" + currentStudent.getName() + "'s Posts:");

        System.out.println(
            "\nCommands: " + "back, edit [num], delete [num], grade [num]" + "\nReplace [num]" +
                " with the number of the post you " + "want to interact with!");

        List<Integer> studentPosts = currentStudent.getPosts();
        if (studentPosts.isEmpty()) {
            System.out.println("There are no posts.");
        } else {
            displayPostsDepth0(studentPosts, currentTeacher);
        }

        System.out.print("> ");
    }

    /**
     * Given a list of posts, prints them all in order with 0 depth/indentation
     * (as opposed to "normal" way of printing posts, which indents and puts
     * replies beneath parent posts)
     *
     * @param posts posts to display
     * @param user  user viewing posts (determines whether certain info is seen)
     */
    private static void displayPostsDepth0(List<Integer> posts, User user) {
        String str = "\n";

        for (Integer pid : posts) {
            Post p = Post.POST_LIST.get(pid);
            str += getPostString(p, 0, user);
        }

        System.out.println(str);
    }

    /**
     * Displays voteboard, which shows all posts in a forum ranked by votes
     * with sorting options.
     *
     * @param currentDiscussion discussion forum to show posts of
     * @param currentSort       current sorting methodology - either "best", "worst" or
     *                          "controversial"
     * @param user              user viewing the voteboard
     */
    public static void displayViewVoteboard(Discussion currentDiscussion, String currentSort,
                                            User user) {
        System.out.println(
            "Voteboard: discussion - " + currentDiscussion.getTopic() + "\nCommands: back, " +
                "sort best, sort worst, sort controversial" + "\nThe voteboard displays " +
                "posts in a forum by vote count." + "\nCurrent sort: " + currentSort);

        List<Integer> posts = currentDiscussion.getAllPosts();
        if (posts.size() == 0) {
            System.out.println("There are no posts.");
        } else {

            switch (currentSort) {
                case "best":
                    posts.sort((p1id, p2id) -> {
                        Post p1 = Post.POST_LIST.get(p1id);
                        Post p2 = Post.POST_LIST.get(p2id);
                        return p2.getVotes() - p1.getVotes();
                    });
                    break;

                case "worst":
                    posts.sort((p1id, p2id) -> {
                        Post p1 = Post.POST_LIST.get(p1id);
                        Post p2 = Post.POST_LIST.get(p2id);
                        return -(p2.getVotes() - p1.getVotes());
                    });
                    break;

                case "controversial":
                    posts.sort((p1id, p2id) -> {
                        Post p1 = Post.POST_LIST.get(p1id);
                        Post p2 = Post.POST_LIST.get(p2id);
                        return -(p2.getControversy() - p1.getControversy());
                    });
                    break;
            }
            displayPostsDepth0(posts, user);
        }

        System.out.print("> ");
    }

    /**
     * Displays grades for all of 1 student's posts in 1 forum
     *
     * @param currentDiscussion discussion forum to show posts of
     * @param currentStudent    student who's viewing their own posts
     */
    public static void displayViewGrades(Discussion currentDiscussion, Student currentStudent) {
        System.out.println("\nView Grades - " + currentDiscussion.getTopic() +
            "\nYou are viewing the grades for every post you have made in this forum." +
            "\nMinimum grade is 1. If a post has grade 0, it has not been graded yet.");

        // get posts
        List<Integer> posts = currentDiscussion.getAllPosts();
        // filter out posts not made by student
        List<Integer> posts2 = new ArrayList<>();
        for (int i = 0; i < posts.size(); i++) {
            int postId = posts.get(i);
            int creatorId = Post.POST_LIST.get(posts.get(i)).getCreatorId();
            if (creatorId == currentStudent.getId()) {
                posts2.add(postId);
            }
        }

        // display posts
        if (posts2.size() == 0) {
            System.out.println("There are no posts.");
        } else {
            displayPostsGrades(posts2);
        }

        System.out.print("> ");
    }

    /**
     * Given a list of posts, prints them all in order with 0 depth
     * AND INCLUDES ONLY GRADE INFO
     * for displayViewGrades
     *
     * @param posts posts to display
     */
    private static void displayPostsGrades(List<Integer> posts) {
        String str = "\n";

        for (Integer pid : posts) {
            Post postin = Post.POST_LIST.get(pid);
            String postString = "";
            if (postin != null) {
                postString = toStringGrades(postin);
            }
            str += postString;
        }

        System.out.println(str);
    }

    /**
     * toString for post when viewed with 'view grades'
     * Modified version that omits unimportant information
     * so student can focus on grade info
     *
     * @return post as formatted string
     */
    private static String toStringGrades(Post postin) {
        String postString = "";
        String indentStr = "|  ";

        postString += "\n" + indentStr + "--------------------\n";

        postString += indentStr + "Post ID " + postin.getId();
        if (postin.getParent() != -1) {
            postString += " (reply to " + postin.getParent() + ")";
        }
        postString += "\n";
        postString += indentStr + "(grade: " + postin.getGrade() + "/" + postin.getMaxGrade() + ")\n";

        postString += indentStr + postin.getContent() + "\n";
        postString += indentStr + "--------------------";
        return postString;
    }
}
