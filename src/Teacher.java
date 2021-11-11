import java.util.Scanner;

/**
 * Project 4 - Teacher
 *
 * Represents a teacher using the program.
 *
 * @author saraxiao0 (Sara Xiao), chang794
 * @version 0.2 - 2021-11-11
 */
public class Teacher extends User {

    public Teacher(String username, String password, String name) {
        super(username, password, name); // calls User's constructor
    }

    /**
     * Teacher Action Loop
     * Called by Main class, handles control flow
     *
     * @param in Scanner input
     */
    @Override
    public void loop(Scanner in) {

    }

    /* ----- canDoX methods - for permissions -----
     * All these methods return booleans showing whether the class
     * has permission to do something or not.
     *
     * All inherited from User.
     */

    /**
     * Whether user can vote
     *
     * @return false since Teachers can't vote
     */
    @Override
    public boolean canVote() {
        return false;
    }

    /**
     * Whether user can grade posts
     *
     * @return true since Teachers can grade posts
     */
    @Override
    public boolean canGrade() {
        return true;
    }

    /**
     * Whether user can make posts, reply to posts, edit own posts
     *
     * @return true since Teachers can reply to student responses
     */
    @Override
    public boolean canPost() {
        return true;
    }

    /**
     * Whether user can create courses
     *
     * @return true since Teachers can create courses
     */
    @Override
    public boolean canCreateCourse() {
        return true;
    }

    /**
     * Whether user can modify courses
     *
     * @return true since Teachers can modify courses
     */
    @Override
    public boolean canModifyCourse() {
        return true;
    }

    /**
     * Whether user can modify/delete dicussions
     *
     * @return true since Teachers can reply to student responses
     */
    @Override
    public boolean canModifyDiscussion() {
        return true;
    }

    /**
     * Whether user can edit/delete OTHER users' posts
     *
     * @return true since Teachers can (see optional features in handout)
     */
    @Override
    public boolean canModifyPost() {
        return true;
    }

    /* ----- Methods that Modify Things -----
     * Will be called as part of Teacher's loop method.
     * For now, most return a boolean representing operation success/failure.
     *
     * WIP.
     */

    /**
     * Create course
     */
    private boolean createCourse() {
    }

    /**
     * Create discussion forum
     */
    private boolean createDiscussion() {
    }

    /**
     * Edit discussion forum
     */
    private boolean editDiscussion() {
    }

    /**
     * Delete discussion forum
     */
    private boolean deleteDiscussion() {
    }

    /**
     * Reply to a student reponse to a discussion forum
     */
    private boolean makePostReply() {
    }

    /**
     * See dashboard that lists most popular forum replies by vote
     * "Data will appear with the student's name and vote count."
     * "Teachers can choose to sort the dashboard."
     * Part of Voting Selection in handout
     */
    private void seeForumVoteDashboard() {
    }

    /**
     * "Teachers can view replies for a specific student on one page
     * and assign a point value to their work."
     */
    private void gradeStudent() {
    }
}
