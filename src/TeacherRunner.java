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
     * Each loop method has a corresponding Display.display method that Display.displays output
     * for that menu
     */

    /*
     * Handles all control flow and UI interaction
     * Called by Teacher's loop method, which is called by Main
     */
    public void loop(Scanner reader) {
        while (!exitProgram) {
            Display.displayWelcome(this.teacher);
            String input = reader.nextLine();

            switch(input) {
                case "edit account":
                    loopEditAccount(reader);
                    break;

                // deleteAccount is a User method that teacher inherits
                case "delete account":
                    teacher.deleteAccount(reader);
                    exitProgram = true;
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
                            Display.displayBadInput();
                        } else {
                            loopCourse(reader);
                        }

                    } catch (NumberFormatException e) {
                        Display.displayBadInput();
                    }
                    break;
            }
        }
        Display.displayExit();
    }


    /**
     * Menu for creating new course (accessed from main menu)
     */
    private void menuCreateCourse(Scanner reader) {
        Display.displayCreateCourse();
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

            Display.displayEditAccount(this.teacher);
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

            Display.displayViewStudent();
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
                        int userId = Integer.parseInt(input);

                        // TODO
                        User selectedUser = User.USER_LIST.get(userId);
                        if (selectedUser == null || !(selectedUser instanceof Student)) {
                            Display.displayBadInput();
                        } else {
                            Student currentStudent = (Student) selectedUser;
                            loopIndividualStudent(reader, currentStudent);
                        }

                    } catch (NumberFormatException e) {
                        Display.displayBadInput();
                    }
                    break;
            }
        }
    }

    /**
     * Loop for viewing/editing all posts of 1 student
     * Called by loopViewStudent if valid student ID is inputted
     */
    private void loopIndividualStudent(Scanner reader, Student currentStudent) {
        while (currentStudent != null) {
            Display.displayIndividualStudent(currentStudent);
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
                        Display.displayBadInput();
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

            Display.displayCourse(currentCourse);
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
                    break;
            }
        }
    }

    /**
     * Menu for creating new discussion forum ()
     */
    private void menuCreateDiscussion(Scanner reader) {
        Display.displayCreateDiscussion();
        String input = reader.nextLine();

        if (this.teacher.createDiscussion(input, currentCourse)) {
            System.out.println("Discussion created successfully!");
        } else {
            System.out.println("An error has occurred while creating this discussion");
        }
    }

    /**
     * Loop for 1 discussion form + its posts
     */
    private void loopDiscussion(Scanner reader) {
        while (currentDiscussion != null) {
            Display.displayDiscussionTeacher(currentDiscussion);
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
                        Display.displayBadInput();
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

    /**
     * Menu for posting reply to other post
     *
     * @param targetPost post to reply to
     * @param reader Scanner for getting input
     */
    private boolean menuPostReply(Post targetPost, Scanner reader) {
        Display.displayPostReply(targetPost);

        String input = reader.nextLine();
        Post newPost = this.teacher.makePostReply(targetPost, input, currentDiscussion);

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
        this.teacher.editPost(targetPost, input);

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
            this.teacher.deletePost(targetPost);
        }

        System.out.println("Post " + targetPost.getId() +
            "has been deleted.");
        return true;
    }

    /**
     * Menu for grading a post
     *
     * @param targetPost post to grade
     * @param reader Scanner for getting input
     */
    private boolean menuGradePost(Post targetPost, Scanner reader) {
        Display.displayGradePost(targetPost);

        String input = reader.nextLine();
        int grade = -1;
        try {
            grade = Integer.parseInt(input);
        } catch (NumberFormatException e) {
            return false;
        }

        if (grade < 0 || grade > targetPost.getMaxGrade()) {
            return false;
        }

        this.teacher.gradePost(targetPost, grade);

        System.out.println("Post " + targetPost.getId() +
            "has been assigned the grade: " + grade + "/" +
            targetPost.getMaxGrade());
        return true;
    }
}
