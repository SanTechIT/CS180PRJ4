import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap; // for tracking voted posts
import java.util.Scanner;

/**
 * Project 4 - Student
 *
 * Represents a student user of the class.
 *
 * @author Sara Xiao, Brian Kwon, Richard Chang, Aarini Panzade
 * @version 2021-11-15
 */
public class Student extends User implements Serializable {
    // hashmap guide: https://docs.oracle.com/javase/8/docs/api/java/util/HashMap.html
    private HashMap<Integer, Integer> votedPosts; // all posts the Student has voted on
    // key: ID of post student has voted on
    // value: -1 = downvote, 1 = upvote

    // TODO need to keep track of user grades
    /**
     * Student Constructor
     *
     * @param username
     * @param password
     * @param name
     */
    public Student(String username, String password, String name) {
        super(username, password, name);
        votedPosts = new HashMap<>();
    }

    /**
     * User Action Loop
     *
     * @param in Scanner input
     */
    @Override
    public void loop(Scanner in) {
        StudentRunner sr = new StudentRunner(this);
        sr.loop(in);
    }

    /**
     * Whether user can vote
     *
     * @return true since Students can vote
     */
    @Override
    public boolean canVote() {
        return true;
    }

    /**
     * Whether user can grade
     *
     * @return false since Students can't grade
     */
    @Override
    public boolean canGrade() {
        return false;
    }

    /**
     * Whether user can post
     *
     * @return true since Students can post
     */
    @Override
    public boolean canPost() {
        return true;
    }

    /**
     * Whether user can create courses
     *
     * @return false since Students can't create courses
     */
    @Override
    public boolean canCreateCourse() {
        return false;
    }

    /**
     * Whether user can modify courses
     *
     * @return false since Students can't modify courses
     */
    @Override
    public boolean canModifyCourse() {
        return false;
    }

    /**
     * Whether user modify discussions
     *
     * @return false since Students can't modify discussions
     */
    @Override
    public boolean canModifyDiscussion() {
        return false;
    }

    /**
     * Whether user can modify posts
     *
     * @return false since Students can't modify posts
     */
    @Override
    public boolean canModifyPost() {
        return false;
    }

    /**
     * Whether user can create discussions
     *
     * @return false since Students can't create discussions
     */
    @Override
    public boolean canCreateDiscussion() {
        return false;
    }


    //public boolean isAdmins() {
        //return true;
    //}

    /* ----- Methods that Modify Things -----
     * Will be called as part of StudentRunner's loop method.
     * For now, most return a boolean representing operation success/failure.
     *
     * These are pseudo-private methods that will only be called by StudentRunner
     * They're not really public.
     * They should be private, but Student and StudentRunner are 2 separate classes..
     *
     * I'm not sure if these are actually necessary. You may be able to
     * just call Post methods from StudentRunner directly.
     *
     * WIP.
     */


    /**
     * Reply DIRECTLY to a discussion forum
     * ie. make a new post that's not a reply to a previous post
     *
     * @param newContent content of new post
     * @param parentDiscussion discussion forum that contains the new post
     */
    public Post makeDiscussionReply(String newContent, Discussion parentDiscussion) {
        return super.makePostReply(null, newContent, parentDiscussion);
    }

    /**
     * Gets Posts String
     *
     * @return str which is the posts string
     */
    public String getPostsString() {
        String str = "";
        for (int postId : getPosts()) {
            Post p = Post.POST_LIST.get(postId);
            if (p != null) { // post hasn't been deleted
                str += p.toString();
            }
        }
        return str;
    }

    /**
     * returns whether votedPost has been upvoted or downvoted by user
     *
     * @param targetPost post to look at
     * @return -1 if downvoted, 1 if upvoted, null if no vote
     */
    public int getPostVote(Post targetPost) {
        int postId = targetPost.getId();

        if (votedPosts.containsKey(postId)) {
            return votedPosts.get(postId);
        }

        return 0;
    }

    /**
     * Determines whether upvote goes through for post
     *
     * @param targetPost
     *
     * @return
     */
    public boolean upvotePost(Post targetPost) {
        int oldVoteValue = getPostVote(targetPost);

        if (
            oldVoteValue != 1 // student hasn't already upvoted post
            && targetPost.upvote(this, oldVoteValue)) { // student has permission to vote

            // upvote post
            votedPosts.put(targetPost.getId(), 1);
            return true;
        }

        return false;
    }

    /**
     * Get downvote for post
     * @param targetPost
     * @return downvote count
     */
    public boolean downvotePost(Post targetPost) {
        int oldVoteValue = getPostVote(targetPost);

        if (
            oldVoteValue != -1 // student hasn't already downvoted post
            && targetPost.downvote(this, oldVoteValue)) { // student has permission to vote

            // downvote post
            votedPosts.put(targetPost.getId(), -1);
            return true;
        }

        return false;
    }

    /**
     * Removes all votes that Student has made
     *
     * @param targetPost
     *
     * @return
     */
    public boolean noVotePost(Post targetPost) {
        int oldVoteValue = getPostVote(targetPost);

        if (
            oldVoteValue != 0 // student has voted on targetPost
            && targetPost.removeVote(oldVoteValue)) {

            // remove vote on post
            votedPosts.remove(targetPost.getId());
            return true;
        }

        return false;
    }
}
