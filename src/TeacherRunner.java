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
 * @version 0.3 - 2021-11-14
 */
public class TeacherRunner extends UserRunner {
    private Teacher teacher; // Teacher who's logged in

    /*
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

    /*
     * For menu options exclusive to Teacher
     * protected because abstract methods can't be private (next best thing)
     * called in UserRunner's loopDiscussion method (viewing posts in a discussion)
     *
     * @param reader Scanner for getting additional input
     * @param input Existing user input
     */
    @Override
    protected void loopMainOverride(Scanner reader, String input) {
        switch(input) {
            case "create course":
                menuCreateCourse(reader);
                break;

            case "view student":
                loopViewStudent(reader);
                break;

            default:
                break;
        }
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
                    setExitProgram(true);
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
                    setCurrentDiscussion(null);
                    break;

                case "exit":
                    setCurrentDiscussion(null);
                    setCurrentCourse(null);
                    setExitProgram(true);
                    break;

                case "delete forum":
                    this.teacher.deleteDiscussion(getCurrentDiscussion());
                    break;

                default:
                    if (!(super.parse2WordInput(input, reader))) {
                        Display.displayBadInput();
                    }
                    break;
            }
        }
    }

    /*
     * For menu options exclusive to Teacher
     * protected because abstract methods can't be private (next best thing)
     * called in UserRunner's loopCourse method (viewing discussions in a course)
     *
     * @param reader Scanner for getting additional input
     * @param input Existing user input
     */
    @Override
    protected void loopCourseOverride(Scanner reader, String input) {
        switch(input) {
            case "create forum":
                menuCreateDiscussion(reader);
                break;

            default:
                break;
        }
    }

    /**
     * Menu for creating new discussion forum ()
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
     * For menu options exclusive to Teacher
     * overrides abstract method in UserRunner
     * protected because abstract methods can't be private (next best thing)
     * called in UserRunner's loopDiscussion method (viewing posts in a discussion)
     *
     * @param reader Scanner for getting additional input
     * @param input Existing user input
     */
    @Override
    protected void loopDiscussionOverride(Scanner reader, String input) {
        switch(input) {
            case "delete forum":
                this.teacher.deleteDiscussion(getCurrentDiscussion());
                break;

            default:
                break;
        }
    }

    /**
     * For menu options exclusive to Teacher
     * overrides abstract method in UserRunner
     * protected because abstract methods can't be private (next best thing)
     * called in UserRunner's parse2WordInputOverride method (parsing input w/ argument)
     *
     * @param targetPost post affected by command
     * @param reader scanner for getting input
     * @param inputWord1 1st word of user input, determines command
     */
    @Override
    protected boolean parse2WordInputOverride(
        Post targetPost, Scanner reader, String inputWord1) {

        switch (inputWord1) {
            case "grade":
                return menuGradePost(targetPost, reader);

            default:
                return false;
        }
    }

    /**
     * Menu for grading a post
     *
     * @param targetPost post to grade
     * @param reader Scanner for getting input
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
