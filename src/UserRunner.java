import java.util.Scanner;

/**
 * Project 4 - User Runner
 * <p>
 * Abstract, inherited by TeacherRunner and StudentRunner
 * Contains methods accessible to both.
 * <p>
 * Runner classes handle control flow and UI interaction.
 *
 * @author Sara Xiao, Richard Chang, Brian Kwon, Aarini Panzade
 * @version 2021-11-15
 */
public abstract class UserRunner {
    private User user; // User who's logged  in
    private Course currentCourse; // current course user's looking at
    private Discussion currentDiscussion; // current discussion user's looking at

    private boolean logout = false; // whether to logout the program
    // set to true when user inputs "logout" - then program logs out
    // and returns to "login / create account / etc" menu

    /**
     * Creates new UserRunner
     *
     * @param user User this runner is connected to and operating for
     */
    public UserRunner(User user) {
        this.user = user;

        currentCourse = null;
        currentDiscussion = null;
    }

    /* ----- Getters and Setters -----
     * So derived classes can access private values.
     */

    /**
     * Returns value of currentCourse
     *
     * @return currentCourse
     */
    public Course getCurrentCourse() {
        return currentCourse;
    }

    public void setCurrentCourse(Course c) {
        currentCourse = c;
    }

    /**
     * Returns value of currentDiscussion
     *
     * @return currentDiscussion
     */
    public Discussion getCurrentDiscussion() {
        return currentDiscussion;
    }

    /**
     * Sets new value of currentDiscussion
     *
     * @param currentDiscussion new value
     */
    public void setCurrentDiscussion(Discussion currentDiscussion) {
        this.currentDiscussion = currentDiscussion;
    }

    /**
     * Sets new value of logout
     *
     * @param logout new value
     */
    public void setLogout(boolean logout) {
        this.logout = logout;
    }

    /* ----- Loop methods - for handling control flow -----
     * All loop methods are called by loop(Scanner reader) directly or indirectly
     * Each loop method represents a particular menu
     *
     * Each loop method has a corresponding Display.display method that Display.displays output
     * for that menu
     */

    /**
     * Handles all control flow and UI interaction
     *
     * @param reader Scanner for input
     */
    public void loop(Scanner reader) {
        while (!logout) {
            Display.displayWelcome(this.user);
            String input = reader.nextLine();

            switch (input) {
                case "edit account":
                    loopEditAccount(reader);
                    break;

                // deleteAccount is a User method that user inherits
                case "delete account":
                    user.deleteAccount(reader);
                    logout = true;
                    break;

                case "logout":
                    logout = true;
                    break;

                default:
                    if (!loopMainOverride(reader, input)) {

                        try {
                            int courseId = Integer.parseInt(input);
                            // Checks if course id exists
                            if (courseId < Course.courseList.size() && courseId >= 0) {
                                currentCourse = Course.courseList.get(courseId);
                            }
                            // Checks if course is deleted / exists
                            if (currentCourse == null) {
                                Display.displayBadInput();
                            } else {
                                loopCourse(reader);
                            }

                        } catch (NumberFormatException e) {
                            Display.displayBadInput();
                        }
                    }
                    break;
            }
        }
        Display.displayLogout();
    }

    /**
     * Loop for editing account
     *
     * @param reader Scanner for input
     */
    private void loopEditAccount(Scanner reader) {
        boolean continueThisMenu = true;
        while (continueThisMenu) {

            Display.displayEditAccount(this.user);
            String input = reader.nextLine();

            switch (input) {
                case "back":
                    continueThisMenu = false;
                    break;

                // modifyUsername is a User method that user inherits
                case "change username":
                    user.modifyUsername(reader);
                    break;

                // modifyName is a User method that user inherits
                case "change name":
                    user.modifyName(reader);
                    break;

                // modifyPassword is a User method that user inherits
                case "change password":
                    user.modifyPassword(reader);
                    break;

                case "logout":
                    logout = true;
                    break;
            }
        }
    }

    /**
     * Loop for 1 course + its discussions
     *
     * @param reader Scanner for input
     */
    private void loopCourse(Scanner reader) {
        while (currentCourse != null) { // "back" sets currentCourse to null
            // then program goes back to main loop

            Display.displayCourse(currentCourse, this.user);
            String input = reader.nextLine();

            switch (input) {
                case "back":
                    currentCourse = null;
                    break;

                case "logout":
                    currentCourse = null;
                    logout = true;
                    break;

                default:
                    if (!loopCourseOverride(reader, input)) {
                        try {
                            int discussionId = Integer.parseInt(input);

                            // Checks if index is in bounds
                            if (discussionId < Discussion.discussionList.size() && discussionId >= 0) {
                                currentDiscussion = Discussion.discussionList.get(discussionId);
                            }
                            // Checks if Discussion is part of the current course
                            if (!currentCourse.getDiscussions().contains(discussionId)) {
                                currentDiscussion = null;
                                Display.displayBadInput();
                            } else if (currentDiscussion == null) {
                                Display.displayBadInput();
                            } else {
                                loopDiscussion(
                                    reader); // enter discussion menu with inputted discussion
                            }

                        } catch (NumberFormatException e) {
                            Display.displayBadInput(); // error if discussion ID doesn't convert
                            // to number
                        }
                    }
                    break;
            }
        }
    }

    /**
     * Loop for 1 discussion form + its posts
     *
     * @param reader Scanner for input
     */
    private void loopDiscussion(Scanner reader) {
        while (currentDiscussion != null) {
            Display.displayDiscussion(currentDiscussion, this.user);

            String input = reader.nextLine();

            // Input loop is different because input can be a static command or one that takes an
            // argument
            // Outer switch checks static commands, inner switch checks arguments
            switch (input) {
                case "back":
                    currentDiscussion = null;
                    break;

                case "logout":
                    currentDiscussion = null;
                    currentCourse = null;
                    logout = true;
                    break;

                default:
                    if (!loopDiscussionOverride(reader, input)) {
                        if (!parse2WordInput(input, reader)) {
                            Display.displayBadInput();
                        }
                    }
                    break;
            }
        }
    }

    /**
     * Checks whether 2-word input for loopDiscussion
     * has valid length + post number
     * <p>
     * If it is, checks which command is in input, then executes command
     * <p>
     * Protected because it's called in TeacherRunner's
     * loopIndividualStudent method
     *
     * @param input  Existing user input
     * @param reader Scanner for getting additional input
     */
    protected boolean parse2WordInput(String input, Scanner reader) {
        if (input.split(" ").length != 2) {
            return false;
        }

        // check if post number is valid
        int postId;
        try {
            postId = Integer.parseInt(input.split(" ")[1]);
        } catch (NumberFormatException e) {
            return false;
        }

        // check if post number corresponds to existing post
        Post targetPost = null;
        if (postId < Post.postList.size() && postId >= 0) {
            targetPost = Post.postList.get(postId);
        }
        if (targetPost == null) {
            // Post is deleted / does not exist
            return false;
        } else if (!currentDiscussion.getPosts().contains(
            postId) && targetPost.getDiscussion() != (currentDiscussion.getId())) {
            // Post is not part of current discussion
            // check if post upstream is part of discussion
            return false;
        }

        // check command
        String inputWord1 = input.split(" ")[0];

        boolean operationSuccess = false; // whether operation succeeds
        // ONLY for if input makes sense - false is for network errors etc
        // On the other hand, if the command itself makes no sense, the
        // entire function returns false

        // As of now, some menu functions are incapable of returning false
        // Since they will always work, since there will always be an Internet connection
        switch (inputWord1) {
            case "reply":
                operationSuccess = menuPostReply(targetPost, reader);
                break;

            case "edit":
                operationSuccess = menuEditPost(targetPost, reader);
                break;

            default:
                return parse2WordInputOverride(targetPost, reader, inputWord1);
        }

        if (!operationSuccess) {
            System.out.println("The operation didn't go through.");
        }
        return true;
    }

    /**
     * Menu for posting reply to other post
     *
     * @param targetPost post to reply to
     * @param reader     Scanner for getting input
     */
    private boolean menuPostReply(Post targetPost, Scanner reader) {
        Display.displayPostReply(targetPost);

        String input = reader.nextLine();
        Post newPost = this.user.makePostReply(targetPost, input, currentDiscussion);

        System.out.println(
            "New post " + newPost.getId() + " (reply to " + targetPost.getId() + ")" + " has " +
                "been created!");

        return true;
    }

    /**
     * Menu for editing a post
     *
     * @param targetPost post to edit
     * @param reader     Scanner for getting input
     */
    protected boolean menuEditPost(Post targetPost, Scanner reader) {
        Display.displayEditPost(targetPost);

        String input = reader.nextLine();
        boolean success = this.user.editPost(targetPost, input);

        if (success) {
            System.out.println("Post " + targetPost.getId() + "has been edited!");
        } else {
            System.out.println("Sorry, you can't edit that post.");
        }

        return success;
    }

    /* ----- Protected "loopXOverride" methods -----
     * Overriden by derived classes
     * For commands exclusive to Teacher or Student (eg. "create forum")
     * Protected so they can be overridden
     *
     * @return a boolean:
     * This boolean represents whether an exclusive command was executed
     * True if it was called, false if it wasn't and input couldn't be parsed
     * always false because User has no exclusive commands
     */

    /**
     * For commands exclusive to Teacher or Student
     * called in UserRunner's loop method (viewing all courses)
     *
     * @param reader Scanner for getting additional input
     * @param input  Existing user input
     * @return if an exclusive command was successfully executed
     * always false because User has no exclusive commands
     */
    protected boolean loopMainOverride(Scanner reader, String input) {
        return false;
    }

    /**
     * For commands exclusive to Teacher or Student
     * called in UserRunner's loopCourse method (viewing discussions in a course)
     *
     * @param reader Scanner for getting additional input
     * @param input  Existing user input
     * @return if an exclusive command was successfully executed (eg. create forum)
     * always false because User has no exclusive commands
     */
    protected boolean loopCourseOverride(Scanner reader, String input) {
        return false;
    }

    /**
     * For commands exclusive to Teacher or Student
     * called in UserRunner's loopDiscussion method (viewing posts in a discussion)
     *
     * @param reader Scanner for getting additional input
     * @param input  Existing user input
     * @return if an exclusive command was successfully executed (eg. create forum)
     * always false because User has no exclusive commands
     */
    protected boolean loopDiscussionOverride(Scanner reader, String input) {
        return false;
    }

    /**
     * For commands exclusive to Teacher or Student
     * <p>
     * called in UserRunner's parse2WordInput method
     * which is called in its loopDiscussion method
     * for parsing commands with an argument
     *
     * @param targetPost post affected by command
     * @param reader     scanner for getting input
     * @param inputWord1 1st word of user input, determines command
     * @return if an exclusive command was successfully executed (eg. create forum)
     * always false because User has no exclusive commands
     */
    protected boolean parse2WordInputOverride(Post targetPost, Scanner reader, String inputWord1) {
        return false;
    }

}
