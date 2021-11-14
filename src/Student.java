import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Student class
 *
 * @author [author], chang794
 * @version 0.1
 */
public class Student extends User {
    // Track posts?
    private List<Integer> posts;

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
     * Reply to a student reponse to a discussion forum
     *
     * @param parentPost parent post the new post is replying to
     * @param newContent content of new post
     */
    public Post makePostReply(Post parentPost, String newContent, Discussion parentDiscussion) {
        //        return new Post(parentPost, this, newContent);
        return Post.createPost(newContent, parentDiscussion, parentPost, this);
    }

    /**
     * Edit own post
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

    public String getPostsString() {
        String str = "";
        for (int post : posts) {
            str += Post.POST_LIST.get(post);
        }
        return str;
    }
    public int getVoteCount() {
        int voteCount = 0;
        for (int post : posts) {
            Post p = Post.POST_LIST.get(post);
            voteCount += p.getUpvotes();
            voteCount -= p.getDownvotes();
        }
        return voteCount;
    }
}
