import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Project 4 - Post
 * <p>
 * Represents a post that is a reply to a discussion forum
 * or a reply to another post.
 *
 * @author Richard Chang, Brian Kwon, Sara Xiao
 * @version 2021-11-15
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
    private int discussion;
    private int parent;
    private List<Integer> posts;
    private int creatorId;
    private Date timestamp;
    private int upvotes;
    private int downvotes;

    private Post() {
        // Deny Instantiation
    }

    /**
     * Post Constructor
     *
     * @param content
     * @param discussion
     * @param parent
     * @param creatorId
     */
    private Post(String content, Discussion discussion, Post parent, int creatorId) {
        this.content = content;
        this.parent = parent != null ? parent.getId() : -1;
        this.discussion = discussion.getId();
        this.creatorId = creatorId;
        this.maxGrade = 100;
        this.timestamp = new Date();
        id = POST_LIST.size();
        if (parent == null) {
            discussion.getPosts().add(id);
        } else {
            parent.getPosts().add(id);
        }
        posts = new ArrayList<>();
        POST_LIST.add(this);
    }

    /**
     * Returns a new post if the user has permission to post
     *
     * @param content content of post
     * @param user    user who posted the post
     * @return the created post
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
     * @param content content of post
     * @param parent  parent post (null if nonexistent)
     * @param user    user who posted the post
     * @return the created post
     */
    public static Post createPost(String content, Discussion discussion, Post parent, User user) {
        if (!user.canPost()) {
            return null;
        }
        return new Post(content, discussion, parent, user.getId());
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

    /**
     * Deletes posts in discussions / posts
     *
     * @param user
     * @return
     */
    public Post deletePost(User user) {
        if (user.canModifyPost()) {
            Discussion.DISCUSSION_LIST.get(discussion).getPosts().remove(Integer.valueOf(id));
            if (parent != -1) {
                Post.POST_LIST.get(parent).getPosts().remove(Integer.valueOf(id));
            }
            User.USER_LIST.get(creatorId).getPosts().remove(Integer.valueOf(id));
            for (int i = 0; i < posts.size(); i++) {
                Post.POST_LIST.get(posts.get(i)).deletePost(user);
            }
            return POST_LIST.set(id, null);
        }
        return null;
    }

    /**
     * toString for post
     *
     * @return postString
     */
    public String toString() {
        String postString = "";
        User creator = User.USER_LIST.get(creatorId);

        postString += "\n";

        postString += "Post ID " + getId();
        if (parent != -1) {
            postString += " (reply to " + parent + ")";
        }

        postString += "\n" + creator.getUsername() + " | " + creator.getName() + " (ID " + creatorId + ") posted";
        postString += " at time " + timestamp.toString();

        postString += "\n(votes: +" + getUpvotes() + " | -" + getDownvotes() + ")";

        postString += "\n(grade: " + getGrade() + "/" + getMaxGrade() + ")";

        postString += "\n" + getContent();
        postString += "\n";

        return postString;
    }

    public String toStringVoteboard() {
        String postString = "";
        User creator = User.USER_LIST.get(creatorId);

        postString += "\n";

        postString += "Post ID " + getId();
        if (parent != -1) {
            postString += " (reply to " + parent + ")";
        }

        postString += "\n" + creator.getUsername() + " | " + creator.getName() + " (ID " + creatorId + ") posted";
        postString += " at time " + timestamp.toString();

        postString += "\nPoster's vote count: " + creator.getVoteCount();

        postString += "\n(votes: +" + getUpvotes() + " | -" + getDownvotes() + ")";

        postString += "\n(grade: " + getGrade() + "/" + getMaxGrade() + ")";

        postString += "\n" + getContent();
        postString += "\n";

        return postString;
    }

    /**
     * Returns the parent of the post
     *
     * @return
     */
    public int getParent() {
        return parent;
    }

    /**
     * Returns the id of the creator of the post
     *
     * @return
     */
    public int getCreatorId() {
        return creatorId;
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
        str += this.toString();
        for (Integer postid : posts) {
            Post post = Post.POST_LIST.get(postid);
            str += post.getPostsString() + "\n";
        }
        return str;
    }

    /**
     * Returns the discussions
     *
     * @return
     */
    public int getDiscussion() {
        return discussion;
    }

    /**
     * Returns the timestamp
     *
     * @return
     */
    public Date getTimestamp() {
        return timestamp;
    }

    /**
     * Returns the list of posts associated with this discussion
     *
     * @return
     */
    public List<Integer> getPosts() {
        return posts;
    }

    /**
     * Returns the amount of upvotes in a post
     *
     * @return upvotes
     */
    public int getUpvotes() {
        return upvotes;
    }

    /**
     * Returns the amount of downvotes in a post
     *
     * @return downvotes
     */
    public int getDownvotes() {
        return downvotes;
    }

    /**
     * Returns a total vote score.
     * <p>
     * Used for ranking posts for voteboard
     *
     * @return votes = upvotes - downvotes
     */
    public int getVotes() {
        return getUpvotes() - getDownvotes();
    }

    /**
     * Returns an int representing controversy
     * smaller = more controversial
     * Used for ranking posts for voteboard
     *
     * @return controversy number
     */
    public int getControversy() {
        // number that grows smaller the more votes
        // the post has
        // because more popular = more likely to be controversial
        float inversePopular = 1 / ((float) getUpvotes() + getDownvotes());

        // number that grows larger the less
        // "balanced" the ratio of upvotes : downvotes is
        // because if people agree that a post is good/bad,
        // it's not a controversial post
        float ratio = 100 * (Math.abs(1 - (float) getUpvotes() / getDownvotes()));

        return (int) (1000 * inversePopular * ratio);
    }

    /**
     * Decides if the user can vote, if so, then upvotes
     *
     * @param user    user who's voting
     * @param oldVote value representing user's previous vote on the post (-1, 1, 0)
     */
    public boolean upvote(User user, int oldVote) {
        if (!user.canVote()) {
            return false;
        }

        removeVote(oldVote);

        this.upvotes++;
        return true;
    }

    /**
     * Decides if the user can vote, if so, then downvotes
     *
     * @param user    user who's voting
     * @param oldVote value representing user's previous vote on the post (-1, 1, 0)
     */
    public boolean downvote(User user, int oldVote) {
        if (!user.canVote()) {
            return false;
        }

        removeVote(oldVote);

        this.downvotes++;
        return true;
    }

    /**
     * Removes 1 upvote or downvote from the code
     *
     * @param oldVote 1 for upvote, -1 for downvote
     */
    public boolean removeVote(int oldVote) {
        if (oldVote == 1) {
            this.upvotes--;
        } else if (oldVote == -1) {
            this.downvotes--;
        }

        return true;
    }
}
