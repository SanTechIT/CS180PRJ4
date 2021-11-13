/* ----- Display methods - for displaying UI messages -----
 * These methods display messages to the user.
 * None of these methods change anything.
 *
 * For Project 5, can be modified to use GUI (handling input will be
 * trickier, though).
 */
public class Display {
    /**
     * Displays output for main loop (viewing all courses after login)
     */
    public void displayWelcome() {
        System.out.println("Welcome " + this.getName() + "!");

        System.out.println(Course.getCoursesString());

        System.out.println("Or please type an option: " +
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
    public void displayBadInput() {
        System.out.println("Input Error:" +
            "\nSorry, I couldn't understand what you typed. Please try again!" +
            "\n-----");
    }

    /**
     * Displays output for program exit
     */
    public void displayExit() {
        System.out.println("Exit:" +
            "Logging out..." +
            "Thank you for using our program. Goodbye!");
    }

    /**
     * Displays output for creating a course (accessed from main menu)
     */
    public void displayCreateCourse() {
        System.out.println("Creating Course:" +
            "\nPlease enter the name of the new course:");

        System.out.print("> ");
    }

    /**
     * Displays output for course loop (viewing all discussions in 1 course)
     */
    public void displayCourse() {
        System.out.println("Welcome to " + currentCourse.getTopic() + "!");

        System.out.println(currentCourse.getDiscussionsString());

        System.out.println("Or, please type an option: " +
            "\nback" +
            "\ncreate forum" +
            "\ndelete forum" +
            "\nexit");

        System.out.print("> ");
    }

    public void displayCreateDiscussion() {
        System.out.println("Creating Discussion:" +
            "\nPlease enter the name of the new discussion:");

        System.out.print("> ");
    }

    /**
     * Displays output for discussion loop (viewing all posts in 1 discussion)
     */
    public void displayDiscussion() {
        System.out.println("Welcome to " + currentDiscussion.getTopic() + "!" +
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
    public void displayPostReply(Post targetPost) {
        System.out.println("Reply to post " + targetPost.getId() + ":" +
            "\nWhat should be the content in the new reply post?");

        System.out.print("> ");
    }

    /**
     * Displays menu for editing post
     */
    public void displayEditPost(Post targetPost) {
        int id = targetPost.getId();
        System.out.println("Edit post " + id + ":" +
            "\nWhat should be the new content in post " + id +
            "?");

        System.out.print("> ");
    }

    /**
     * Displays menu for deleting post
     */
    public void displayDeletePost(Post targetPost) {
        System.out.println("Delete post " + targetPost.getId() + ":" +
            "\nDeleted posts can't be recovered." +
            "Are you sure you want to do this?" +
            "Type yes to confirm.");

        System.out.print("> ");
    }

    /**
     * Displays menu for grading post
     */
    public void displayGradePost(Post targetPost) {
        int id = targetPost.getId();
        System.out.println("Grade post " + id + ":" +
            "\nThe minimum grade is 0," +
            "and the maximum grade is " + targetPost.getMaxGrade() + "." +
            "\nEnter the grade to assign to post " + id + ":");

        System.out.print("> ");
    }

    /**
     * Displays output for edit account loop (an option from the main loop)
     */
    public void displayEditAccount() {
        System.out.println("Editing Your Account - " + teacher.getUsername() +
            "\nPlease type an option: " +
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
    public void displayViewStudent() {
        System.out.println("View Student:" +
            "\nThis shows all of a student's posts and lets you grade them." +
            "\nEnter the name or ID of the student to view: " +
            "\nOr, please type an option: " +
            "\nback" +
            "\nexit");
    }
}
