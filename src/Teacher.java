import java.io.Serializable;
import java.util.Scanner;

/**
 * Project 4 - Teacher
 * <p>
 * Represents a teacher.
 *
 * @author Sara Xiao, Richard Chang
 * @version 2021-11-15
 */
public class Teacher extends User implements Serializable {

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
     *
     * @param topic topic of course
     * @return whether operation succeeds
     */
    public boolean createCourse(String topic) {
        //        Course c = new Course(topic, this);
        // Does permissions check for networking so people cant make random courses;
        return (Course.createCourse(topic, this) != null);
    }

    /**
     * Edit course topic
     *
     * @param newTopic new topic of course
     * @param course course to change
     * @return whether operation succeeds (no network errors, etc)
     */
    public boolean editCourse(String newTopic, Course course) {
        return course.setTopic(newTopic, this);
    }

    public boolean deleteCourse(Course course) {
        return (Course.deleteCourse(course.getId(), this) == null);
    }

    /**
     * Create discussion forum
     *
     * @param topic topic of forum
     * @param course course forum belongs to
     * @return if operation succeeds
     */
    public boolean createDiscussion(String topic, Course course) {
        // Discussion d = new Discussion(topic, course, this);
        // Does permissions check for networking so people cant make random discussions;
        return (Discussion.createDiscussion(course, topic, this) != null);
    }

    /**
     * Edit discussion forum
     *
     * @param discussion discussion to edit
     * @param newTopic new topic of discussion
     * @return if operation succeeded
     */
    public boolean editDiscussion(Discussion discussion, String newTopic) {
        return discussion.setTopic(newTopic, this);
    }

    /**
     * Delete discussion forum
     *
     * @param discussion discussion to delete
     * @return if operation succeeded
     */
    public boolean deleteDiscussion(Discussion discussion) {
        return (Discussion.deleteDiscussion(discussion.getId(), this) != null);
    }

    /**
     * grades a post
     *
     * @param targetPost post to grade
     * @param grade grade to assign - goes between 1 and 100
     * @return if operation succeeded
     */
    public boolean gradePost(Post targetPost, int grade) {
        return targetPost.grade(this, grade);
    }
}
