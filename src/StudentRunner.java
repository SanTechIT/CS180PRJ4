import java.util.Scanner;

/**
 * Project 4 - Student Runner
 *
 * Runs the loop() for the Teacher class
 *
 *
 * @author briankwon25 (Brian Kwon)
 *
 * @version 0.2 - 2021-11-12
 */

public class StudentRunner {
    private Student student;
    private Course currentCourse;
    private Discussion currentDiscussion;

    private boolean exitProgram = false;


    public StudentRunner(Student student) {
        this.student = student;

        currentCourse = null;
        currentDiscussion = null;
    }

    public void loop(Scanner reader) {
        while (!exitProgram) {
            displayWelcome();
            String input = reader.nextLine();

            switch(input) {
                case "edit account":
                    loopEditAccount(reader);
                    break;

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

    private void menuCreateCourse(Scanner reader) {
        displayCreateCourse();
        String input = reader.nextLine();

        student.createCourse(input);

        System.out.println("Course created successfully!");
    }

    private void loopEditAccount(Scanner reader) {
        boolean continueThisMenu = true;
        while (continueThisMenu) {

            displayEditAccount();
            String input = reader.nextLine();

            switch(input) {
                case "back":
                    continueThisMenu = false;
                    break;

                case "change username":
                    teacher.modifyUsername(reader);
                    break;

                case "change name":
                    teacher.modifyName(reader);
                    break;

                case "change password":
                    teacher.modifyPassword(reader);
                    break;

                case "exit":
                    exitProgram = true;
                    break;
            }
        }
    }

    private void loopViewStudent(Scanner reader) {
        boolean continueThisMenu = true;
        while (continueThisMenu) {

            displayViewStudent();
            String input = reader.nextLine();

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


                    } catch (NumberFormatException e) {
                        displayBadInput();
                    }
                    break;
            }
        }
    }

    private void loopCourse(Scanner reader) {
        while (currentCourse != null) {

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
                            loopDiscussion(reader);
                        }

                    } catch (NumberFormatException e) {
                        displayBadInput();
                    }
                    break;
            }
        }
    }

    private void menuCreateDiscussion(Scanner reader) {
        displayCreateDiscussion();
        String input = reader.nextLine();

        this.teacher.createDiscussion(input, currentCourse);

        System.out.println("Discussion created successfully!");
    }

    private void loopDiscussion(Scanner reader) {
        while (currentDiscussion != null) {
            displayDiscussion();
            String input = reader.nextLine();

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

    private boolean parse2WordInput(String input, Scanner reader) {
        if (input.split(" ").length != 2) {
            return false;
        }

        int postId;
        try {
            postId = Integer.parseInt(input.split(" ")[1]);

            if (postId < 0) {
                return false;
            }
        } catch (NumberFormatException e) {
            return false;
        }

//        Post targetPost = currentDiscussion.searchPosts(postId);
        Post targetPost = Post.POST_LIST.get(postId);
        if (targetPost == null) {
            return false;
        }

        String inputWord1 = input.split(" ")[0];

        boolean operationSuccess = false;


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

    private void displayBadInput() {
        System.out.println("Input Error:" +
                "\nSorry, I couldn't understand what you typed. Please try again!" +
                "\n-----");
    }

    private void displayExit() {
        System.out.println("Exit:" +
                "Logging out..." +
                "Thank you for using our program. Goodbye!");
    }

    private void displayCreateCourse() {
        System.out.println("Creating Course:" +
                "\nPlease enter the name of the new course:");

        System.out.print("> ");
    }

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

    private void displayDiscussion() {
        System.out.println("Welcome to " + currentDiscussion.getTopic() + "!" +
                "\nCommands: back, reply [num], edit [num], delete [num], " +
                "grade [num], exit" +
                "\nReplace [num] with the number of the post you" +
                "want to interact with!");

        System.out.println(currentDiscussion.getPostsString());

        System.out.print("> ");
    }

    private void displayPostReply(targetPost) {
        System.out.println("Reply to post " + targetPost.getId() + ":" +
                "\nWhat should be the content in the new reply post?");

        System.out.print("> ");
    }

    private void displayEditPost(targetPost) {
        int id = targetPost.getId();
        System.out.println("Edit post " + id + ":" +
                "\nWhat should be the new content in post " + id +
                "?");

        System.out.print("> ");
    }

    private void displayDeletePost(targetPost) {
        System.out.println("Delete post " + targetPost.getId() + ":" +
                "\nDeleted posts can't be recovered." +
                "Are you sure you want to do this?" +
                "Type yes to confirm.");

        System.out.print("> ");
    }

    private void displayGradePost(targetPost) {
        int id = targetPost.getId();
        System.out.println("Grade post " + id + ":" +
                "\nThe minimum grade is 0," +
                "and the maximum grade is " + targetPost.getMaxGrade() + "." +
                "\nEnter the grade to assign to post " + id + ":");

        System.out.print("> ");
    }

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

    private void displayViewStudent() {
        System.out.println("View Student:" +
                "\nThis shows all of a student's posts and lets you grade them." +
                "\nEnter the name or ID of the student to view: " +
                "\nOr, please type an option: " +
                "\nback" +
                "\nexit");
    }
}
