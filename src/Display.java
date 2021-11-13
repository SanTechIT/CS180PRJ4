/* ----- Display methods - for displaying UI messages -----
 * These methods display messages to the user.
 * None of these methods change anything.
 *
 * For Project 5, can be modified to use GUI (handling input will be
 * trickier, though).
 */
public class Display {
    /**
     * Displays output for main login/create account loop
     */
    public static void displayStart() {
        System.out.println("\nWelcome to our program!" +
            "\nPlease type one of these commands:" +
            "\nlogin" +
            "\ncreate account" +
            "\nexit");
    }

    /**
     * Displays output for User main loop (viewing all courses after login)
     */
    public static void displayWelcome(User user) {
        System.out.println("\nWelcome " + user.getName() + "!");

        System.out.println(Course.getCoursesString());

        System.out.println("Or, please type one of these commands: " +
            "\nedit account" +
            "\ndelete account" +
            "\ncreate course" +
            "\nview student" +
            "\nexit");

        System.out.print("> ");
    }

    /**
     * Displays output for invalid input
     */
    public static void displayBadInput() {
        System.out.println("\nInput Error:" +
            "\nSorry, I couldn't understand what you typed. Please try again!" +
            "\n-----");
    }

    /**
     * Displays output for program exit
     */
    public static void displayExit() {
        System.out.println("\nExit:" +
            "\nLogging out..." +
            "\nThank you for using our program. Goodbye!");
    }

    /**
     * Displays output for creating a course (accessed from main menu)
     */
    public static void displayCreateCourse() {
        System.out.println("\nCreating Course:" +
            "\nPlease enter the name of the new course:");

        System.out.print("> ");
    }

    /**
     * Displays output for course loop (viewing all discussions in 1 course)
     */
    public static void displayCourse(Course currentCourse) {
        System.out.println("\nWelcome to " + currentCourse.getTopic() + "!");

        System.out.println(currentCourse.getDiscussionsString());

        System.out.println("Or, please type one of these commands: " +
            "\nback" +
            "\ncreate forum" +
            "\ndelete forum" +
            "\nexit");

        System.out.print("> ");
    }

    public static void displayCreateDiscussion() {
        System.out.println("\nCreating Discussion:" +
            "\nPlease enter the name of the new discussion:");

        System.out.print("> ");
    }

    /**
     * Displays output for discussion loop (viewing all posts in 1 discussion)
     */
    public static void displayDiscussion(Discussion currentDiscussion) {
        System.out.println("\nWelcome to " + currentDiscussion.getTopic() + "!" +
            "\nCommands: back, reply [num], edit [num], delete [num], " +
                "grade [num], exit" +
            "\nReplace [num] with the number of the post you" +
                "want to interact with!");

        System.out.println(currentDiscussion.getPostsString());

        System.out.print("> ");
    }

    /**
     * Displays menu for replying to post
     */
    public static void displayPostReply(Post targetPost) {
        System.out.println("\nReply to post " + targetPost.getId() + ":" +
            "\nYou are replying to an existing post in the discussion." +
            "\nWhat should be the content in your new reply post?");

        System.out.print("> ");
    }

    /**
     * Displays menu for editing post
     */
    public static void displayEditPost(Post targetPost) {
        int id = targetPost.getId();
        System.out.println("\nEdit post " + id + ":" +
            "\nWhat should be the new content in post " + id +
            "?");

        System.out.print("> ");
    }

    /**
     * Displays menu for deleting post
     */
    public static void displayDeletePost(Post targetPost) {
        System.out.println("\nDelete post " + targetPost.getId() + ":" +
            "\nDeleted posts can't be recovered." +
            "Are you sure you want to do this?" +
            "Type yes to confirm.");

        System.out.print("> ");
    }

    /**
     * Displays menu for grading post
     */
    public static void displayGradePost(Post targetPost) {
        int id = targetPost.getId();
        System.out.println("\nGrade post " + id + ":" +
            "\nThe minimum grade is 0," +
            "and the maximum grade is " + targetPost.getMaxGrade() + "." +
            "\nEnter the grade to assign to post " + id + ":");

        System.out.print("> ");
    }

    /**
     * Displays output for edit account loop (an option from the main loop)
     */
    public static void displayEditAccount(User user) {
        System.out.println("\nEditing Your Account - " + user.getUsername() +
            "\nPlease type one of these commands: " +
            "\nback" +
            "\nchange username" +
            "\nchange name" +
            "\nchange password" +
            "\nexit");

        System.out.print("> ");
    }

    /**
     * Displays output for view student loop (option from the main loop)
     */
    public static void displayViewStudent() {
        System.out.println("\nView Student:" +
            "\nThis shows all of a student's posts and lets you grade them." +
            "\nEnter the name or ID of the student to view: " +
            "\nOr, please type one of these commands: " +
            "\nback" +
            "\nexit");
    }
}
