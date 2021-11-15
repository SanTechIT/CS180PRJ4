import java.util.Scanner;

import java.util.List;
import java.util.ArrayList;

/**
 * Project 4 - Teacher Runner
 * <p>
 * Runs the loop() for the Teacher class
 * because I don't want to put the control flow
 * in the Teacher class.
 *
 * @author Sara Xiao, Richard Chang, Aarini Panzade
 * @version 2021-11-15
 */
public class TeacherRunner extends UserRunner {
    private Teacher teacher; // Teacher who's logged in

    /**
     * Creates new TeacherRunner
     *
     * @param teacher Teacher this runner is connected to and operating for
     */
    public TeacherRunner(Teacher teacher) {
        super(teacher);
        this.teacher = teacher;
    }

    /* ----- Loop methods - for handling control flow -----
     * All loop methods are called by loop(Scanner reader) directly or indirectly
     * Each loop method represents a particular menu
     *
     * Each loop method has a corresponding Display.display method that Display.displays output
     * for that menu
     */

    /**
     * For menu options exclusive to Teacher
     * called in UserRunner's loopDiscussion method (viewing posts in a discussion)
     *
     * @param reader Scanner for getting additional input
     * @param input Existing user input
     *
     * @return if an exclusive command was successfully executed (eg. create forum)
     * if returns false, no exclusive commands could be detected/executed
     */
    @Override
    protected boolean loopMainOverride(Scanner reader, String input) {
        switch (input) {
            case "create course":
                menuCreateCourse(reader);
                break;

            case "view student":
                loopViewStudent(reader);
                break;

            default:
                return false;
        }
        return true;
    }

    /**
     * Menu for creating new course (accessed from main menu)
     *
     * @param reader Scanner for getting input
     */
    private void menuCreateCourse(Scanner reader) {
        Display.displayCreateCourse();
        String input = reader.nextLine();

        teacher.createCourse(input);

        System.out.println("Course created successfully!");
    }

    /**
     * See dashboard that lists most popular forum replies by vote
     * "Data will appear with the student's name and vote count."
     * "Teachers can choose to sort the dashboard."
     * Part of Voting Selection in handout
     *
     * @param reader Scanner for getting input
     */
    private void loopViewStudent(Scanner reader) {
        boolean continueThisMenu = true;
        while (continueThisMenu) { // back, logout set continueThisMenu = false
            // then program goes back to main loop

            Display.displayViewStudent();
            String input = reader.nextLine(); // user input

            switch (input) {
                case "back":
                    continueThisMenu = false;
                    break;

                case "logout":
                    continueThisMenu = false;
                    setLogout(true);
                    break;

                default:
                    try {
                        int userId = Integer.parseInt(input);

                        // TODO
                        User selectedUser = null;
                        // checks if id is valid / exists in list
                        if (userId < User.USER_LIST.size() && userId >= 0) {
                            selectedUser = User.USER_LIST.get(userId);
                        }
                        // Check if user exists/is deleted/and is a student
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
     *
     * @param reader Scanner for getting input
     * @param currentStudent student using this method
     */
    private void loopIndividualStudent(Scanner reader, Student currentStudent) {
        while (currentStudent != null) {
            Display.displayIndividualStudent(currentStudent);
            String input = reader.nextLine();

            // Input loop is different because input can be a static command or one that takes an argument
            // Outer switch checks static commands, inner switch checks arguments
            switch (input) {
                case "back":
                    currentStudent = null;
                    break;

                default:
                    if (!parse2WordInputStudent(input, reader)) {
                        Display.displayBadInput();
                    }
                    break;
            }
        }
    }

    /**
     * For menu options exclusive to Teacher
     * called in UserRunner's loopCourse method (viewing discussions in a course)
     *
     * @param reader Scanner for getting additional input
     * @param input Existing user input
     *
     * @return if an exclusive command was successfully executed (eg. create forum)
     * if returns false, no exclusive commands could be detected/executed
     */
    @Override
    protected boolean loopCourseOverride(Scanner reader, String input) {
        switch (input) {
            case "create forum":
                menuCreateDiscussion(reader);
                break;

            case "edit this course":
                menuEditCourse(reader);
                break;

            default:
                return false;
        }
        return true;
    }

    /**
     * Menu for creating new discussion forum
     *
     * @param reader Scanner for getting input
     */
    private void menuCreateDiscussion(Scanner reader) {
        Display.displayCreateDiscussion();
        String input = reader.nextLine();

        if (this.teacher.createDiscussion(input, getCurrentCourse())) {
            System.out.println("Discussion created successfully!");
        } else {
            System.out.println("An error has occurred while creating this discussion");
        }
    }

    /**
     * Menu for editing course topic
     *
     * @param reader Scanner for getting input
     */
    private void menuEditCourse(Scanner reader) {
        Display.displayEditCourse();
        String input = reader.nextLine();

        if (this.teacher.editCourse(input, getCurrentCourse())) {
            System.out.println("Course topic edited successfully!");
        } else {
            System.out.println("An error has occurred while editing this course topic!");
        }
    }

    /**
     * For menu options exclusive to Teacher
     * overrides abstract method in UserRunner
     * called in UserRunner's loopDiscussion method (viewing posts in a discussion)
     *
     * @param reader Scanner for getting additional input
     * @param input  Existing user input
     * @return if an exclusive command was successfully executed (eg. create forum)
     * if returns false, no exclusive commands could be detected/executed
     */
    protected boolean loopDiscussionOverride(Scanner reader, String input) {
        switch (input) {
            case "view voteboard":
                loopViewVoteboard(reader);
                break;

            case "delete forum":
                this.teacher.deleteDiscussion(getCurrentDiscussion());
                setCurrentDiscussion(null);
                break;

            default:
                return false;
        }
        return true;
    }

    /**
     * Shows the dashboard by the number of votes
     */
    private void loopViewVoteboard(Scanner reader) {
        boolean continueThisMenu = true;
        String currentSort = "best";

        while (continueThisMenu) {
            Display.displayViewVoteboard(getCurrentDiscussion(), currentSort, this.teacher);
            String input = reader.nextLine();

            switch (input) {
                case "back":
                    continueThisMenu = false;
                    break;

                case "sort best":
                    currentSort = "best";
                    break;

                case "sort worst":
                    currentSort = "worst";
                    break;

                case "sort controversial":
                    currentSort = "controversial";
                    break;

                default:
                    Display.displayBadInput();
                    break;
            }
        }
    }

    /**
     * Modified version of UserRunner's parse2WordInput.
     * However, while UserRunner's method is for the discussion menu,
     * this method is for the viewIndividualStudent menu.
     * <p>
     * So there are slight modifications.
     * <p>
     *
     * @param input  Existing user input
     * @param reader Scanner for getting additional input
     */
    private boolean parse2WordInputStudent(String input, Scanner reader) {
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
        if (postId < Post.POST_LIST.size() && postId >= 0) {
            targetPost = Post.POST_LIST.get(postId);
        }

        if (targetPost == null) {
            // Post is deleted / does not exist
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
            case "edit":
                operationSuccess = super.menuEditPost(targetPost, reader);
                break;

            case "delete":
                operationSuccess = super.menuDeletePost(targetPost, reader);
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
     * For menu options exclusive to Teacher
     * overrides abstract method in UserRunner
     * called in UserRunner's parse2WordInputOverride method (parsing input w/ argument)
     *
     * @param targetPost post affected by command
     * @param reader     scanner for getting input
     * @param inputWord1 1st word of user input, determines command
     * @return if an exclusive command was successfully executed (eg. create forum)
     * if returns false, no exclusive commands could be detected/executed
     */
    @Override
    protected boolean parse2WordInputOverride(Post targetPost, Scanner reader, String inputWord1) {

        switch (inputWord1) {
            case "grade":
                menuGradePost(targetPost, reader);
                break;

            default:
                return false;
        }
        return true;
    }

    /**
     * Menu for grading a post
     *
     * @param targetPost post to grade
     * @param reader     Scanner for getting input
     * @return whether operation succeeded (whether grade was valid or not)
     */
    private boolean menuGradePost(Post targetPost, Scanner reader) {
        Display.displayGradePost(targetPost);

        // get grade from user
        String input = reader.nextLine();
        int grade = -1;
        try {
            grade = Integer.parseInt(input);
        } catch (NumberFormatException e) {
            return false;
        }

        if (grade < 1 || grade > targetPost.getMaxGrade()) {
            return false;
        }

        this.teacher.gradePost(targetPost, grade);

        System.out.println(
                "Post " + targetPost.getId() + "has been assigned the grade: " + grade + "/" + targetPost.getMaxGrade());
        return true;
    }
}
