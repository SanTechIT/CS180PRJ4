import java.util.Scanner;

/**
 * Project 4 - Teacher Runner
 *
 * Runs the loop() for the Teacher class
 * because I don't want to put the control flow
 * in the Teacher class.
 *
 * Feel free to copy this format for other classes.
 * Or copy-paste this into another class to use as a guide.
 *
 * @author saraxiao0 (Sara Xiao)
 * @version 0.1 - 2021-11-11
 */
public class TeacherRunner {
    private Teacher teacher; // Teacher who's logged in
    private Course currentCourse; // current course user's looking at
    private Discussion currentDiscussion; // current discussion user's looking at

    private boolean exitProgram = false;

    /*
     * Creates new TeacherRunner
     */
    public TeacherRunner(Teacher teacher) {
        this.teacher = teacher;

        currentCourse = null;
        currentDiscussion = null;
    }

    /* ----- Loop methods - for handling control flow -----
     * All loop methods are called by loop(Scanner reader) directly or indirectly
     * Each loop method represents a particular menu
     *
     * Each loop method has a corresponding display method that displays output
     * for that menu
     */

    /*
     * Handles all control flow and UI interaction
     * Called by Teacher's loop method, which is called by Main
     */
    public void loop(Scanner reader) {
        while (!exitProgram) {
            displayWelcome();
            String input = reader.nextLine();

            switch(input) {
                case "edit account":
                    break;

                case "delete account":
                    break;

                case "create course":
                    break;

                case "view student":
                    break;

                case "exit":
                    exitProgram = true;
                    break;

                default:
                    try {
                        int courseId = Integer.parseInt(input);

                        currentCourse = Course.searchCourses(courseId);
                        if (currentCourse == null) {
                            displayBadInput();
                        } else {
                            loopCourse(reader);
                        }

                    } catch (NumberFormatException) {
                        displayBadInput();
                    }
                    break;
            }
        }
        displayExit();
    }

    /**
     * Loop for 1 course + its discussions
     */
    private void loopCourse(Scanner reader) {
        while (currentCourse != null) { // "back" sets currentCourse to null
        // then program goes back to main loop

            displayCourse();
            String input = reader.nextLine();

            switch (input) {
                case "back":
                    currentCourse = null;

                case "create forum":
                    break;

                case "delete forum":
                    break;

                case "exit":
                    currentCourse = null;
                    exitProgram = true;

                default:
                    try {
                        int discussionId = Integer.parseInt(input);

                        currentDiscussion = Course.searchDiscussions(discussionId);
                        if (currentDiscussion == null) {
                            displayBadInput();
                        } else {
                            loopDiscussion(reader);
                        }

                    } catch (NumberFormatException) {
                        displayBadInput();
                    }
                    break;
            }
        }
    }

    /**
     * Loop for 1 discussion form + its posts
     */
    private void loopDiscussion(Scanner reader) {
        while (currentDiscussion != null) {
            displayDiscussion();
            String input = reader.nextLine();

            // Input loop is different because input can be 1 or 2 words
            // Outer switch checks 1-word input, inner switch checks 2-word input
            switch(input) {
                case "back":
                    currentDiscussion = null;
                    break;

                case "exit":
                    currentDiscussion = null;
                    currentCourse = null;
                    exitProgram = true;
                    break;

                default:
                    if (!(parse2WordInput(input))) {
                        displayBadInput();
                    }
                    break;
            }
        }
    }

    /**
     * Checks whether 2-word input for loopDiscussion
     * has valid length + post number
     *
     * If it is, checks which command is in input, then executes command
     */
    private boolean parse2WordInput(String input) {
        if (input.split(" ").length != 2) {
            return false;
        }

        // check if post number is valid
        int postId;
        try {
            postId = Integer.parseInt(inputArray[1]);

            // post id can't be negative
            if (postId < 0) {
                return false;
            }
        } catch (NumberFormatException) {
            return false;
        }

        // check if post number corresponds to existing post
        Post targetPost = currentDiscussion.searchPosts(postId);
        if (targetPost == null) {
            return false;
        }

        // check command
        inputWord1 = input.split(" ")[0];
        switch (inputWord1) {
            case "reply":
                String newContent = "filler";
                this.teacher.makePostReply(targetPost, newContent);
                break;

            case "edit":
                String newContent = "filler 2";
                this.teacher.editPost(targetPost, newContent);
                break;

            case "delete":
                this.teacher.deletePost(targetPost);
                break;

            case "grade":
                int grade = 70;
                this.teacher.gradePost(targetPost, grade);
                break;

            default:
                return false;
        }
    }

    /* ----- Display methods - for displaying UI messages -----
     * These methods display messages to the user.
     * None of these methods change anything.
     *
     * For Project 5, can be modified to use GUI (handling input will be
     * trickier, though).
     */

    private void displayWelcome() {
        System.out.println("Welcome " + this.getName() + "!");

        System.out.println(Course.getCoursesString());

        System.out.println("Or please type an option: " +
            "\nedit account" +
            "\ndelete account" +
            "\ncreate course" +
            "\nview student" +
            "\nexit");

        System.out.println("> ");
    }

    private void displayBadInput() {
        System.out.println("Input Error:" +
            "Sorry, I couldn't understand what you typed. Please try again!");
    }

    private void displayExit() {
        System.out.println("Exit:" +
            "Logging out..." +
            "Thank you for using our program. Goodbye!");
    }

    private void displayCourse() {
        System.out.println("Welcome to " + currentCourse.getTopic() + "!");

        System.out.println(currentCourse.getDiscussionsString());

        System.out.println("Or, please type an option: " +
            "\nback" +
            "\ncreate forum" +
            "\ndelete forum" +
            "\nexit");

        System.out.println("> ");
    }

    private void displayDiscussion() {
        System.out.println("Welcome to " + currentDiscussion.getTopic() + "!" +
            "\nCommands: back, reply [num], edit [num], delete [num], " +
                "grade [num], exit" +
            "\nReplace [num] with the number of the post you" +
                "want to interact with!");

        System.out.println(currentDiscussion.getPostsString());
    }
}
