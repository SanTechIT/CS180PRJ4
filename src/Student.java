import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap; // for tracking voted posts
import java.util.Scanner;

/**
 * Student class
 *
 * @author [author], chang794 (Richard Chang), briankwon25 (Brian Kwon)
 * @version 0.1
 */
public class Student extends User implements Serializable {
    private List<Integer> posts; // ID of every post the Student has made

    // hashmap guide: https://docs.oracle.com/javase/8/docs/api/java/util/HashMap.html
    private HashMap<Integer, Integer> votedPosts; // all posts the Student has voted on
    // key: ID of post student has voted on
    // value: -1 = downvote, 1 = upvote

    /**
     * Student Constructor
     *
     * @param username
     * @param password
     * @param name
     */
    public Student(String username, String password, String name) {
        super(username, password, name);
        posts = new ArrayList<>();
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

    @Override
    public boolean canVote() {
        return true;
    }

    @Override
    public boolean canGrade() {
        return false;
    }

    @Override
    public boolean canPost() {
        return true;
    }

    @Override
    public boolean canCreateCourse() {
        return false;
    }

    @Override
    public boolean canModifyCourse() {
        return false;
    }

    @Override
    public boolean canModifyDiscussion() {
        return false;
    }

    @Override
    public boolean canModifyPost() {
        return false;
    }

    @Override
    public boolean canCreateDiscussion() {
        return false;
    }

    public boolean isAdmins() {
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
     * Reply to a reply to a discussion forum
     * Overrides because all posts a Student makes must be added to their posts field
     *
     * @param parentPost parent post the new post is replying to
     * @param newContent content of new post
     * @param parentDiscussion discussion forum that contains both posts
     */
    @Override
    public Post makePostReply(Post parentPost, String newContent, Discussion parentDiscussion) {
        Post p = Post.createPost(newContent, parentDiscussion, parentPost, this);

        int postId = p.getId();
        if (!posts.contains(postId)) {
            posts.add(p.getId());
        }

        return p;
    }


    /**
     * Reply DIRECTLY to a discussion forum
     * ie. make a new post that's not a reply to a previous post
     *
     * @param newContent content of new post
     * @param parentDiscussion discussion forum that contains the new post
     */
    public Post makeDiscussionReply(String newContent, Discussion parentDiscussion) {
        Post p = Post.createPost(newContent, parentDiscussion, this);

        int postId = p.getId();
        if (!posts.contains(postId)) {
            posts.add(p.getId());
        }

        return p;
    }

    /**
     * Gets Posts String
     *
     * @return str which is the posts string
     */
    public String getPostsString() {
        String str = "";
        for (int postId : posts) {
            str += Post.POST_LIST.get(postId).toString();
        }
        return str;
    }

    /**
     * Get Vote Count
     *
     * @return voteCount which is the total number of upvotes and downvotes together
     */
    public int getVoteCount() {
        int voteCount = 0;
        for (int post : posts) {
            Post p = Post.POST_LIST.get(post);
            voteCount += p.getUpvotes();
            voteCount -= p.getDownvotes();
        }
        return voteCount;
    }

    /**
     * returns whether votedPost has been upvoted or downvoted by user
     *
     * @param votedPost post to look at
     * @return -1 if downvoted, 1 if upvoted, null if no vote
     */
    public int getPostVote(Post targetPost) {
        int postId = targetPost.getId();

        if (votedPosts.containsKey(postId)) {
            return votedPosts.get(postId);
        }

        return 0;
    }

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

    public boolean novotePost(Post targetPost) {
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
