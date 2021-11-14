import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Project 4 - Post
 * <p>
 * Post class
 *
 * @author briankwon25 (Brian Kwon)
 *
 * @version 0.2 - 2021-11-12
 */

public class Post implements Serializable {
    // As per https://stackoverflow.com/
    // questions/10378855/java-io-invalidclassexception-local-class-incompatible
    private static final long serialVersionUID = 01L;

    public static List<Post> POST_LIST;

    private int id;
    private int grade;
    private int maxGrade;
    private String content;
    private Discussion discussion;
    private Post parent;
    private List<Post> posts;
    private int creatorId;
    private Date timestamp;
    private int upvotes;
    private int downvotes;

    private Post() {
        // Deny Instantiation
    }

    private Post(String content, Discussion discussion, Post parent, int creatorId) {
        this.content = content;
        this.parent = parent;
        this.discussion = discussion;
        this.creatorId = creatorId;
        this.maxGrade = 100;
        if (parent == null) {
            discussion.getPosts().add(this);
        } else {
            parent.getPosts().add(this);
        }
        id = POST_LIST.size();
        posts = new ArrayList<>();
        POST_LIST.add(this);
    }

    /**
     * Returns a new post if the user has permission to post
     *
     * @param content
     * @param user
     * @return
     */
    public static Post createPost(String content, Discussion discussion, User user) {
        if (!user.canPost()) {
            return null;
        }
        return new Post(content, discussion, null, user.getId());
    }

    /**
     * Returns a new post if the user has permission to post
     * Post is a reply to another post
     *
     * @param content
     * @param parent
     * @param user
     * @return
     */
    public static Post createPost(String content, Discussion discussion, Post parent, User user) {
        if (!user.canPost()) {
            return null;
        }
        return new Post(content, discussion, parent, user.getId());
    }

    public static Post searchPostsById(int postId) {
        for (Post post : POST_LIST) {
            if (post.getId() == postId) {
                return post;
            }
        }
        return null;
    }

    /**
     * Allows editing of the post if the user has permission to edit or if
     * the user is the creatorId of the post
     *
     * @param newContent
     * @param user
     * @return
     */
    public boolean editPost(String newContent, User user) {
        if (user.canModifyPost() || user.getId() == creatorId) {
            this.content = newContent;
            return true;
        }
        return false;
    }

    public Post deletePost(User user) {
        // TODO: delete post in discussion / posts
        if (user.canModifyPost() || user.getId() == creatorId) {
            discussion.getPosts().remove(this);
            if (parent != null) {
                parent.getPosts().remove(this);
            }
            return POST_LIST.set(id, null);
        }
        return null;
    }

    public String toString() {
        String postString = "";
        User creator = User.USER_LIST.get(creatorId);
        String timestamp = "TIMESTAMP NOT IMPLEMENTED"; // TODO

        postString += creator.getUsername() + " | " + creator.getName() + " (ID " + creatorId + ") posted";
        postString += " at time " + timestamp;

        postString += "\n(votes: +" + getUpvotes() + " | -" + getDownvotes() + ")";
        postString += "\n" + getContent();
        postString += "\n";

        return postString;
    }

    /**
     * Returns the Id of the post
     *
     * @return
     */
    public int getId() {
        return id;
    }

    /**
     * Returns the grade of the post
     *
     * @return
     */
    public int getGrade() {
        return grade;
    }

    /**
     * Grades the post if the user has permission
     *
     * @param user
     * @param grade
     * @return
     */
    public boolean grade(User user, int grade) {
        if (!user.canGrade()) {
            return false;
        }
        this.grade = grade;
        return true;
    }

    /**
     * Returns the content of the post
     *
     * @return
     */
    public String getContent() {

        return content;
    }

    /**
     * Returns the max grade
     *
     * @return
     */
    public int getMaxGrade() {
        return maxGrade;
    }

    /**
     * Return the list of posts under this post as a string
     *
     * @return
     */
    public String getPostsString() {
        String str = "";
        for (Post post : posts) {
            str += post.toString() + "\n";
        }
        return str;
    }

    /**
     * Returns the list of posts associated with this discussion
     *
     * @return
     */
    public List<Post> getPosts() {
        return posts;
    }

    /**
     * Returns the amount of upvotes in a post
     *
     * @return
     */
    public int getUpvotes() {
        return upvotes;
    }

    /**
     * Returns the amount of downvotes in a post
     *
     * @return
     */
    public int getDownvotes() {
        return downvotes;
    }

    /**
     * Decides if the user can vote, if so, then able to upvote
     *
     * @param user
     *
     */
    public boolean upvote(User user) {
        if (!user.canVote()) {
            return false;
        }
        this.upvotes++;
        return true;
    }

    /**
     * Decides if the user can vote, if so, then able to downvote
     *
     * @param user
     *
     */
    public boolean downvote(User user) {
        if (!user.canVote()) {
            return false;
        }
        this.upvotes++;
        return true;
    }
}
