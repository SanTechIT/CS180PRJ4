import java.util.Scanner;

/**
 * Project 4 - Teacher
 * <p>
 * Represents a teacher.
 *
 * @author saraxiao0 (Sara Xiao), chang794
 * @version 0.3 - 2021-11-12
 */
public class Teacher extends User {

    public Teacher(String username, String password, String name) {
        super(username, password, name); // calls User's constructor
    }

    /**
     * Teacher Action Loop
     * Called by Main class, handles control flow
     * Important: I put this & other control flow methods in a separate
     * class for better organization.
     *
     * @param in Scanner input
     */
    @Override
    public void loop(Scanner in) {
        TeacherRunner tr = new TeacherRunner(this);
        tr.loop(in);
    }

    /* ----- canDoX methods - for permissions -----
     * All these methods return booleans showing whether the class
     * has permission to do something or not.
     *
     * All inherited from User (User has abstract methods for these)
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
     * Whether user can create discussions
     *
     * @return true since Teachers can create discussions
     */
    public boolean canCreateDiscussion() {
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

    //    @Override
    //    public boolean isAdmin() {
    //        return false;
    //    }

    /* ----- Methods that Modify Things -----
     * Will be called as part of TeacherRunner's loop method.
     * For now, most return a boolean representing operation success/failure.
     *
     * These are pseudo-private methods that will only be called by TeacherRunner
     * They're not really public.
     * They should be private, but Teacher and TeacherRunner are 2 separate classes..
     *
     * I'm not sure if these are actually necessary. You may be able to
     * just call Post methods from TeacherRunner directly.
     *
     * WIP.
     */

    /**
     * Create course
     */
    public boolean createCourse(String topic) {
        //        Course c = new Course(topic, this);
        // Does permissions check for networking so people cant make random courses;
        return (Course.createCourse(topic, this) != null);
    }

    // TODO: EDIT COURSES

    /**
     * Create discussion forum
     */
    public boolean createDiscussion(String topic, Course course) {
        //        Discussion d = new Discussion(topic, course, this);
        // Does permissions check for networking so people cant make random discussions;
        return (Discussion.createDiscussion(course, topic, this) != null);
    }

    /**
     * Edit discussion forum
     */
    public boolean editDiscussion(Discussion discussion, String newTopic) {
        return discussion.setTopic(newTopic, this);
    }

    /**
     * Delete discussion forum
     */
    public boolean deleteDiscussion(Discussion discussion) {
        return (Discussion.deleteDiscussion(discussion.getId(), this) != null);
    }

    /**
     * Reply to a student reponse to a discussion forum
     *
     * @param parentPost parent post the new post is replying to
     * @param newContent content of new post
     */
    public Post makePostReply(Post parentPost, String newContent) {
        //        return new Post(parentPost, this, newContent);
        return Post.createPost(newContent, parentPost, this);
    }

    /**
     * Reply to a student reponse to a discussion forum
     *
     * @param targetPost
     * @param newContent
     */
    public boolean editPost(Post targetPost, String newContent) {
        return targetPost.editPost(newContent, this);
    }

    /**
     * Delete a post
     *
     * @param targetPost post to be deleted
     */
    public boolean deletePost(Post targetPost) {
        return (targetPost.deletePost(this) != null);
    }

    /**
     * "Teachers can view replies for a specific student on one page
     * and assign a point value to their work."
     */
    public boolean gradePost(Post targetPost, int grade) {
        return targetPost.grade(this, grade);
    }
}
