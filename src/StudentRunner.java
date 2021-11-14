import java.util.Scanner;

/**
 * Project 4 - Student Runner
 *
 * Runs the loop() for the Teacher class
 *
 * @author briankwon25 (Brian Kwon)
 *
 * @version 0.2 - 2021-11-12
 */
public class StudentRunner {
    private Student student; // Teacher who's logged in
    private Course currentCourse; // current course user's looking at
    private Discussion currentDiscussion; // current discussion user's looking at

    private boolean exitProgram = false; // whether to exit the program
    // set to true when user inputs "exit" - then program logs off and stops


    public StudentRunner(Student student) {
        this.student = student;

        currentCourse = null;
        currentDiscussion = null;
    }

    public void loop(Scanner reader) {
        while (!exitProgram) {
            Display.displayWelcome(this.student);
            String input = reader.nextLine();

            switch(input) {
                case "edit account":
                    loopEditAccount(reader);
                    break;

                // deleteAccount is a User method that teacher inherits
                case "delete account":
                    student.deleteAccount(reader);
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
     * Loop for editing account
     */
    private void loopEditAccount(Scanner reader) {
        boolean continueThisMenu = true;
        while (continueThisMenu) {

            Display.displayEditAccount(this.student);
            String input = reader.nextLine();

            switch(input) {
                case "back":
                    continueThisMenu = false;
                    break;

                // modifyUsername is a User method that teacher inherits
                case "change username":
                    student.modifyUsername(reader);
                    break;

                // modifyName is a User method that teacher inherits
                case "change name":
                    student.modifyName(reader);
                    break;

                // modifyPassword is a User method that teacher inherits
                case "change password":
                    student.modifyPassword(reader);
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

            Display.displayCourse(currentCourse);
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
                    try {
                        int discussionId = Integer.parseInt(input);

                        currentDiscussion = Discussion.DISCUSSION_LIST.get(discussionId);
                        if (currentDiscussion == null) {
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
     * Loop for 1 discussion form + its posts
     */
    private void loopDiscussion(Scanner reader) {
        while (currentDiscussion != null) {
            Display.displayDiscussion(currentDiscussion);
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


            default:
                return false;
        }

        if (!operationSuccess) {
            System.out.println("Sorry, there was an error in performing the command.");
        }
        return true;
    }

    private boolean menuPostReply(Post targetPost, Scanner reader) {
        Display.displayPostReply(targetPost);

        String input = reader.nextLine();
        Post newPost = this.student.makePostReply(targetPost, input);

        System.out.println("New post " + newPost.getId() +
                " (reply to " + targetPost.getId() + ")" +
                "has been created!");
        return true;
    }

    private boolean menuEditPost(Post targetPost, Scanner reader) {
        Display.displayEditPost(targetPost);

        String input = reader.nextLine();
        this.student.editPost(targetPost, input);

        System.out.println("Post " + targetPost.getId() +
                "has been edited!");
        return true;
    }

    private boolean menuDeletePost(Post targetPost, Scanner reader) {
        Display.displayDeletePost(targetPost);

        String input = reader.nextLine();
        if (input.toLowerCase().equals("yes")) {
            this.student.deletePost(targetPost);
        }

        System.out.println("Post " + targetPost.getId() +
                "has been deleted.");
        return true;
    }

}
