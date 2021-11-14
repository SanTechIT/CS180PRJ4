import java.util.Scanner;

/**
 * Project 4 - Student Runner
 * <p>
 * Runs the loop() for the Student class
 *
 * @author briankwon25 (Brian Kwon), saraxiao0 (Sara Xiao)
 *
 * @version 0.3 - 2021-11-14
 */
public class StudentRunner extends UserRunner {
    private Student student; // Student who's logged in

    /*
     * Creates new StudentRunner
     *
     * @param student Student this runner is connected to and operating for
     */
    public StudentRunner(Student student) {
        super(student);
        this.student = student;
    }

    /* ----- Loop methods - for handling control flow -----
     * All loop methods are called by loop(Scanner reader) directly or indirectly
     * Each loop method represents a particular menu
     *
     * Each loop method has a corresponding Display.display method that Display.displays output
     * for that menu
     */

    @Override
    protected void loopMainOverride(Scanner reader, String input){}

    @Override
    protected void loopCourseOverride(Scanner reader, String input){}

    @Override
    protected void loopDiscussionOverride(Scanner reader, String input) {
        switch(input) {
            case "reply to discussion":
                menuDiscussionReply(reader);
                break;

            default:
                break;
        }
    }

    private boolean menuDiscussionReply(Scanner reader) {
        Display.displayDiscussionReply(getCurrentDiscussion());

        String input = reader.nextLine();
        Post newPost = this.student.makeDiscussionReply(input, getCurrentDiscussion());

        System.out.println("New post " + newPost.getId() + " " +
            "has been created!");
        return true;
    }

    @Override
    protected boolean parse2WordInputOverride(Post targetPost, Scanner reader, String input){return false;}

}
