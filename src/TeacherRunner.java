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
 * @version 0.2 - 2021-11-12
 */
public class TeacherRunner {
    private Teacher teacher; // Teacher who's logged in
    private Course currentCourse; // current course user's looking at
    private Discussion currentDiscussion; // current discussion user's looking at

    private boolean exitProgram = false; // whether to exit the program
    // set to true when user inputs "exit" - then program logs off and stops

    /*
     * Creates new TeacherRunner
     *
     * @param teacher Teacher this runner is connected to and operating for
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
                    loopEditAccount(reader);
                    break;

                // deleteAccount is a User method that teacher inherits
                case "delete account":
                    teacher.deleteAccount(reader);
                    break;

                case "create course":
                    menuCreateCourse(reader);
                    break;

                case "view student":
                    loopViewStudent(reader);
                    break;

                case "exit":
                    exitProgram = true;
                    break;

                default:
                    try {
                        int courseId = Integer.parseInt(input);

                        currentCourse = Course.COURSE_LIST.get(courseId);
                        if (currentCourse == null) {
                            displayBadInput();
                        } else {
                            loopCourse(reader);
                        }

                    } catch (NumberFormatException e) {
                        displayBadInput();
                    }
                    break;
            }
        }
        displayExit();
    }


    /**
     * Menu for creating new course (accessed from main menu)
     */
    private void menuCreateCourse(Scanner reader) {
        displayCreateCourse();
        String input = reader.nextLine();

        teacher.createCourse(input);

        System.out.println("Course created successfully!");
    }

    /**
     * Loop for editing account
     */
    private void loopEditAccount(Scanner reader) {
        boolean continueThisMenu = true;
        while (continueThisMenu) {

            displayEditAccount();
            String input = reader.nextLine();

            switch(input) {
                case "back":
                    continueThisMenu = false;
                    break;

                // modifyUsername is a User method that teacher inherits
                case "change username":
                    teacher.modifyUsername(reader);
                    break;

                // modifyName is a User method that teacher inherits
                case "change name":
                    teacher.modifyName(reader);
                    break;

                // modifyPassword is a User method that teacher inherits
                case "change password":
                    teacher.modifyPassword(reader);
                    break;

                case "exit":
                    exitProgram = true;
                    break;
            }
        }
    }

    /**
     * See dashboard that lists most popular forum replies by vote
     * "Data will appear with the student's name and vote count."
     * "Teachers can choose to sort the dashboard."
     * Part of Voting Selection in handout
     */
    private void loopViewStudent(Scanner reader) {
        boolean continueThisMenu = true;
        while (continueThisMenu) { // back, exit set continueThisMenu = false
        // then program goes back to main loop

            displayViewStudent();
            String input = reader.nextLine(); // user input

            switch (input) {
                case "back":
                    continueThisMenu = false;
                    break;

                case "exit":
                    continueThisMenu = false;
                    exitProgram = true;
                    break;

                default:
                    try {
                        int studentId = Integer.parseInt(input);

                        // TODO

                        /*
                        currentDiscussion = Course.searchDiscussions(discussionId);
                        if (currentDiscussion == null) {
                            displayBadInput();
                        } else {
                            loopDiscussion(reader);
                        } */

                    } catch (NumberFormatException e) {
                        displayBadInput();
                    }
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

            displayCourse();
            String input = reader.nextLine();

            switch (input) {
                case "back":
                    currentCourse = null;
                    break;

                case "create forum":
                    menuCreateDiscussion(reader);
                    break;

                case "exit":
                    currentCourse = null;
                    exitProgram = true;
                    break;

                default:
                    try {
                        int discussionId = Integer.parseInt(input);

                        currentDiscussion = Discussion.DISCUSSIONS_LIST.get(discussionId);
                        if (currentDiscussion == null) {
                            displayBadInput();
                        } else {
                            loopDiscussion(reader); // enter discussion menu with inputted discussion
                        }

                    } catch (NumberFormatException e) {
                        displayBadInput(); // error if discussion ID doesn't convert to number
                    }
                    break;
            }
        }
    }

    /**
     * Menu for creating new discussion forum ()
     */
    private void menuCreateDiscussion(Scanner reader) {
        displayCreateDiscussion();
        String input = reader.nextLine();

        this.teacher.createDiscussion(input, currentCourse);

        System.out.println("Discussion created successfully!");
    }

    /**
     * Loop for 1 discussion form + its posts
     */
    private void loopDiscussion(Scanner reader) {
        while (currentDiscussion != null) {
            displayDiscussion();
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

                case "delete forum":
                    this.teacher.deleteDiscussion(currentDiscussion);
                    break;

                default:
                    if (!(parse2WordInput(input, reader))) {
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
    private boolean parse2WordInput(String input, Scanner reader) {
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
//        Post targetPost = currentDiscussion.searchPosts(postId);
        Post targetPost = Post.POST_LIST.get(postId);
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

            case "grade":
                operationSuccess = menuGradePost(targetPost, reader);
                break;

            default:
                return false;
        }

        if (!operationSuccess) {
            System.out.println("Sorry, there was an error in performing the command.");
        }
        return true;
    }

    private boolean menuPostReply(Post targetPost, Scanner reader) {
        displayPostReply(targetPost);

        String input = reader.nextLine();
        Post newPost = this.teacher.makePostReply(targetPost, input);

        System.out.println("New post " + newPost.getId() +
            " (reply to " + targetPost.getId() + ")" +
            "has been created!");
        return true;
    }

    private void menuEditPost(Post targetPost, Scanner reader) {
        displayEditPost(targetPost);

        String input = reader.nextLine();
        this.teacher.editPost(targetPost, input);

        System.out.println("Post " + targetPost.getId() +
            "has been edited!");
        return true;
    }

    private void menuDeletePost(Post targetPost, Scanner reader) {
        displayDeletePost(targetPost);

        String input = reader.nextLine();
        if (input.toLowerCase().equals("yes")) {
            this.teacher.deletePost(targetPost);
        }

        System.out.println("Post " + targetPost.getId() +
            "has been deleted.");
        return true;
    }

    private boolean menuGradePost(Post targetPost, Scanner reader) {
        displayGradePost(targetPost);

        String input = reader.nextLine();
        int grade = -1;
        try {
            grade = Integer.parseInt(input);
        } catch (NumberFormatException e) {
            return false;
        }

        if (grade < 0 || grade > ) {
            return false;
        }

        this.teacher.gradePost(targetPost, grade);

        System.out.println("Post " + targetPost.getId() +
            "has been assigned the grade: " + grade + "/" +
            targetPost.getMaxGrade());
        return true;
    }

    /* ----- Display methods - for displaying UI messages -----
     * These methods display messages to the user.
     * None of these methods change anything.
     *
     * For Project 5, can be modified to use GUI (handling input will be
     * trickier, though).
     */

    /**
     * Displays output for main loop (viewing all courses after login)
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

        System.out.print("> ");
    }

    /**
     * Displays output for invalid input
     */
    private void displayBadInput() {
        System.out.println("Input Error:" +
            "\nSorry, I couldn't understand what you typed. Please try again!" +
            "\n-----");
    }

    /**
     * Displays output for program exit
     */
    private void displayExit() {
        System.out.println("Exit:" +
            "Logging out..." +
            "Thank you for using our program. Goodbye!");
    }

    /**
     * Displays output for creating a course (accessed from main menu)
     */
    private void displayCreateCourse() {
        System.out.println("Creating Course:" +
            "\nPlease enter the name of the new course:");

        System.out.print("> ");
    }

    /**
     * Displays output for course loop (viewing all discussions in 1 course)
     */
    private void displayCourse() {
        System.out.println("Welcome to " + currentCourse.getTopic() + "!");

        System.out.println(currentCourse.getDiscussionsString());

        System.out.println("Or, please type an option: " +
            "\nback" +
            "\ncreate forum" +
            "\ndelete forum" +
            "\nexit");

        System.out.print("> ");
    }

    private void displayCreateDiscussion() {
        System.out.println("Creating Discussion:" +
            "\nPlease enter the name of the new discussion:");

        System.out.print("> ");
    }

    /**
     * Displays output for discussion loop (viewing all posts in 1 discussion)
     */
    private void displayDiscussion() {
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
    private void displayPostReply(targetPost) {
        System.out.println("Reply to post " + targetPost.getId() + ":" +
            "\nWhat should be the content in the new reply post?");

        System.out.print("> ");
    }

    /**
     * Displays menu for editing post
     */
    private void displayEditPost(targetPost) {
        int id = targetPost.getId();
        System.out.println("Edit post " + id + ":" +
            "\nWhat should be the new content in post " + id +
            "?");

        System.out.print("> ");
    }

    /**
     * Displays menu for deleting post
     */
    private void displayDeletePost(targetPost) {
        System.out.println("Delete post " + targetPost.getId() + ":" +
            "\nDeleted posts can't be recovered." +
            "Are you sure you want to do this?" +
            "Type yes to confirm.");

        System.out.print("> ");
    }

    /**
     * Displays menu for grading post
     */
    private void displayGradePost(targetPost) {
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
    private void displayEditAccount() {
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
    private void displayViewStudent() {
        System.out.println("View Student:" +
            "\nThis shows all of a student's posts and lets you grade them." +
            "\nEnter the name or ID of the student to view: " +
            "\nOr, please type an option: " +
            "\nback" +
            "\nexit");
    }
}
