import java.util.Scanner;

/**
 * Project 4 - User Runner
 *
 * Abstract, inherited by TeacherRunner and StudentRunner
 * Contains methods accessible to both.
 *
 * Runner classes handle control flow and UI interaction.
 *
 * @author briankwon25 (Brian Kwon), saraxiao0 (Sara Xiao)
 * @version 0.1 - 2021-11-14
 */
public abstract class UserRunner {
    private User user; // User who's logged in
    private Course currentCourse; // current course user's looking at
    private Discussion currentDiscussion; // current discussion user's looking at

    private boolean exitProgram = false; // whether to exit the program
    // set to true when user inputs "exit" - then program logs off and stops

    /*
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
     * @return
     */
     public Course getCurrentCourse() {
         return currentCourse;
     }

     /**
     * Sets new value of currentCourse
     * @param
     */
     public void setCurrentCourse(Course currentCourse) {
         this.currentCourse = currentCourse;
     }

     /**
     * Returns value of currentDiscussion
     * @return
     */
     public Discussion getCurrentDiscussion() {
         return currentDiscussion;
     }

     /**
     * Sets new value of currentDiscussion
     * @param
     */
     public void setCurrentDiscussion(Discussion currentDiscussion) {
         this.currentDiscussion = currentDiscussion;
     }

     /**
     * Returns value of exitProgram
     * @return
     */
     public boolean isExitProgram() {
         return exitProgram;
     }

     /**
     * Sets new value of exitProgram
     * @param
     */
     public void setExitProgram(boolean exitProgram) {
         this.exitProgram = exitProgram;
     }

    /* ----- Loop methods - for handling control flow -----
     * All loop methods are called by loop(Scanner reader) directly or indirectly
     * Each loop method represents a particular menu
     *
     * Each loop method has a corresponding Display.display method that Display.displays output
     * for that menu
     */

    /*
     * Handles all control flow and UI interaction
     */
    public void loop(Scanner reader) {
        while (!exitProgram) {
            Display.displayWelcome(this.user);
            String input = reader.nextLine();

            switch(input) {
                case "edit account":
                    loopEditAccount(reader);
                    break;

                // deleteAccount is a User method that user inherits
                case "delete account":
                    user.deleteAccount(reader);
                    exitProgram = true;
                    break;

                case "exit":
                    exitProgram = true;
                    break;

                default:
                    if (!loopMainOverride(reader, input)) {

                        try {
                            int courseId = Integer.parseInt(input);

                            currentCourse = Course.COURSE_LIST.get(courseId);
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
        Display.displayExit();
    }

    /**
     * Loop for editing account
     */
    private void loopEditAccount(Scanner reader) {
        boolean continueThisMenu = true;
        while (continueThisMenu) {

            Display.displayEditAccount(this.user);
            String input = reader.nextLine();

            switch(input) {
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

                case "exit":
                    exitProgram = true;
                    break;
            }
        }
    }

    /**
     * Loop for 1 course + its discussions
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

                case "exit":
                    currentCourse = null;
                    exitProgram = true;
                    break;

                default:
                    if (!loopCourseOverride(reader, input)) {

                        try {
                            int discussionId = Integer.parseInt(input);

                            currentDiscussion = Discussion.DISCUSSION_LIST.get(discussionId);

                            if(!currentCourse.getDiscussions().contains(currentDiscussion)){
                                currentDiscussion = null;
                                Display.displayBadInput();
                            } else if (currentDiscussion == null) {
                                Display.displayBadInput();
                            } else {
                                loopDiscussion(reader); // enter discussion menu with inputted discussion
                            }

                        } catch (NumberFormatException e) {
                            Display.displayBadInput(); // error if discussion ID doesn't convert to number
                        }
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
            Display.displayDiscussion(currentDiscussion, this.user);

            String input = reader.nextLine();

            // Input loop is different because input can be a static command or one that takes an argument
            // Outer switch checks static commands, inner switch checks arguments
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
     *
     * If it is, checks which command is in input, then executes command
     *
     * Protected because it's called in TeacherRunner's
     * loopIndividualStudent method
     *
     * @param input Existing user input
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

            // post id can't be negative
            if (postId < 0) {
                return false;
            }
        } catch (NumberFormatException e) {
            return false;
        }

        // check if post number corresponds to existing post
        Post targetPost = Post.searchPostsById(postId);
        if (targetPost == null) {
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

            case "delete":
                operationSuccess = menuDeletePost(targetPost, reader);
                break;

            default:
                return parse2WordInputOverride(targetPost, reader, inputWord1);
        }

        if (!operationSuccess) {
            System.out.println("Sorry, there was an error in performing the command.");
        }
        return true;
    }

    /**
     * Menu for posting reply to other post
     *
     * @param targetPost post to reply to
     * @param reader Scanner for getting input
     */
    private boolean menuPostReply(Post targetPost, Scanner reader) {
        Display.displayPostReply(targetPost);

        String input = reader.nextLine();
        Post newPost = this.user.makePostReply(targetPost, input, currentDiscussion);

        System.out.println("New post " + newPost.getId() +
            " (reply to " + targetPost.getId() + ")" +
            "has been created!");
        return true;
    }

    /**
     * Menu for editing a post
     *
     * @param targetPost post to edit
     * @param reader Scanner for getting input
     */
    private boolean menuEditPost(Post targetPost, Scanner reader) {
        Display.displayEditPost(targetPost);

        String input = reader.nextLine();
        this.user.editPost(targetPost, input);

        System.out.println("Post " + targetPost.getId() +
            "has been edited!");
        return true;
    }

    /**
     * Menu for deleting a post
     *
     * @param targetPost post to delete
     * @param reader Scanner for getting input
     */
    private boolean menuDeletePost(Post targetPost, Scanner reader) {
        Display.displayDeletePost(targetPost);

        String input = reader.nextLine();
        if (input.toLowerCase().equals("yes")) {
            this.user.deletePost(targetPost);
        }

        System.out.println("Post " + targetPost.getId() +
            "has been deleted.");
        return true;
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

    /*
     * For commands exclusive to Teacher or Student
     * called in UserRunner's loop method (viewing all courses)
     *
     * @param reader Scanner for getting additional input
     * @param input Existing user input
     *
     * @return if an exclusive command was successfully executed
     * always false because User has no exclusive commands
     */
    protected boolean loopMainOverride(Scanner reader, String input) {
        return false;
    }

    /*
     * For commands exclusive to Teacher or Student
     * called in UserRunner's loopCourse method (viewing discussions in a course)
     *
     * @param reader Scanner for getting additional input
     * @param input Existing user input
     *
     * @return if an exclusive command was successfully executed (eg. create forum)
     * always false because User has no exclusive commands
     */
    protected boolean loopCourseOverride(Scanner reader, String input) {
        return false;
    }

    /*
     * For commands exclusive to Teacher or Student
     * called in UserRunner's loopDiscussion method (viewing posts in a discussion)
     *
     * @param reader Scanner for getting additional input
     * @param input Existing user input
     *
     * @return if an exclusive command was successfully executed (eg. create forum)
     * always false because User has no exclusive commands
     */
    protected boolean loopDiscussionOverride(Scanner reader, String input){
        return false;
    }

    /*
     * For commands exclusive to Teacher or Student
     *
     * called in UserRunner's parse2WordInput method
     * which is called in its loopDiscussion method
     * for parsing commands with an argument
     *
     * @param targetPost post affected by command
     * @param reader scanner for getting input
     * @param inputWord1 1st word of user input, determines command
     *
     * @return if an exclusive command was successfully executed (eg. create forum)
     * always false because User has no exclusive commands
     */
    protected boolean parse2WordInputOverride(
        Post targetPost, Scanner reader, String inputWord1) {
        return false;
    }

}
