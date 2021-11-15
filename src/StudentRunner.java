import java.util.Scanner;

/**
 * Project 4 - Student Runner
 * <p>
 * Runs the loop() for the Student class, handling menus/control flow.
 *
 * @author Sara Xiao, Brian Kwon, Richard Chang
 * @version 2021-11-15
 */
public class StudentRunner extends UserRunner {
    private Student student; // Student who's logged in

    /**
     * Creates new StudentRunner
     *
     * @param student Student this runner is connected to and operating for
     */
    public StudentRunner(Student student) {
        super(student);
        this.student = student;
    }

    /**
     * For menu options exclusive to Student
     * called in UserRunner's loopDiscussion method (viewing posts in a discussion)
     *
     * @param reader Scanner for getting additional input
     * @param input Existing user input
     *
     * @return if an exclusive command was successfully executed (eg. create forum)
     * if returns false, no exclusive commands could be detected/executed
     */
    @Override
    protected boolean loopDiscussionOverride(Scanner reader, String input) {
        switch(input) {
            case "reply to discussion":
                menuDiscussionReply(reader);
                break;

            case "view grades":
                menuViewGrades(reader);
                break;

            default:
                return false;
        }
        return true;
    }

    /**
     * Menu for viewing all grades received for all posts in the forum (Student exclusive)
     *
     * @param reader Scanner for user input
     * @return boolean for whether operation succeeds (always true for now)
     */
    private boolean menuViewGrades(Scanner reader) {
        Display.displayViewGrades(getCurrentDiscussion(), this.student);
        String input = reader.nextLine(); // nothing done with this, just to "pause" program
        return true;
    }

    /**
     * Menu for replying directly to a discussion forum (Student exclusive)
     *
     * @param reader Scanner for user input
     * @return boolean for whether operation succeeds (always true for now)
    */
    private boolean menuDiscussionReply(Scanner reader) {
        Display.displayDiscussionReply(getCurrentDiscussion());

        String input = reader.nextLine();
        Post newPost = this.student.makeDiscussionReply(input, getCurrentDiscussion());

        System.out.println("New post " + newPost.getId() + " " +
            "has been created!");
        return true;
    }

    /**
     * For menu options exclusive to Student
     * overrides abstract method in UserRunner
     * called in UserRunner's parse2WordInputOverride method (parsing input w/ argument)
     *
     * @param targetPost post affected by command
     * @param reader scanner for getting input
     * @param inputWord1 1st word of user input, determines command
     *
     * @return if an exclusive command was successfully executed (eg. create forum)
     * if returns false, no exclusive commands could be detected/executed
     */
    @Override
    protected boolean parse2WordInputOverride(
        Post targetPost, Scanner reader, String inputWord1) {

        switch (inputWord1) {
            case "upvote":
                if (this.student.upvotePost(targetPost)) {
                    System.out.println("Post upvoted!");
                } else {
                    System.out.println("Sorry, you can't upvote that post.");
                }
                break;

            case "downvote":
                if (this.student.downvotePost(targetPost)) {
                    System.out.println("Post downvoted.");
                } else {
                    System.out.println("Sorry, you can't downvote that post.");
                }
                break;

            case "novote":
                if (this.student.noVotePost(targetPost)) {
                    System.out.println("Removed your vote on this post.");
                } else {
                    System.out.println("Sorry, you haven't voted on this post.");
                }
                break;

            default:
                return false;
        }
        return true;
    }
}
