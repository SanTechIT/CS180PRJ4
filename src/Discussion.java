import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Project 4 - Discussion
 *
 * Represents a teacher-created Discussion which
 * belongs to a Course and has Posts.
 *
 * @author Richard Chang, Sara Xiao
 * @version 2021-11-15
 */
public class Discussion implements Serializable {
    // As per https://stackoverflow.com/
    // questions/10378855/java-io-invalidclassexception-local-class-incompatible
    private static final long serialVersionUID = 01L;

    public static List<Discussion> DISCUSSION_LIST;

    private int id;
    private String topic;
    private List<Integer> posts;
    private int course;
    private Date timestamp;
    private int creator;

    /**
     * CANNOT BE USED. Discussions can't be instantiated with constructor.
     * createDiscussion MUST be used.
     */
    private Discussion() {
        // Deny Instantiation
    }

    /**
     * CANNOT BE USED. Discussions can't be instantiated with constructor.
     * createDiscussion MUST be used.
     */
    private Discussion(Course course, String topic, int creator) {
        this.topic = topic;
        this.creator = creator;
        this.course = course.getId();
        id = DISCUSSION_LIST.size();
        course.getDiscussions().add(id);
        posts = new ArrayList<>();
        DISCUSSION_LIST.add(this);
    }

    /**
     * Creates and returns a new Discussion object if the user has permission to.
     *
     * @param course course it belongs to
     * @param topic topic of discussion
     * @param user user who's creating discussion
     * @return created Discussion
     */
    public static Discussion createDiscussion(Course course, String topic, User user) {
        if (!user.canCreateDiscussion()) {
            return null;
        }
        return new Discussion(course, topic, user.getId());

    }

    /**
     * Deletes the discussion with the given id
     *
     * @param id
     * @param user
     * @return
     */
    public static Discussion deleteDiscussion(int id, User user) {
        if (!user.canModifyDiscussion()) {
            return null;
        }
        Discussion deleted = DISCUSSION_LIST.set(id, null);
        Course.COURSE_LIST.get(deleted.course).getDiscussions().remove(deleted.getId());
        return deleted;
    }

    /**
     * Returns the Id of the discussion
     *
     * @return
     */
    public int getId() {
        return id;
    }

    /**
     * Returns the topic of the discussion
     *
     * @return
     */
    public String getTopic() {
        return topic;
    }

    /**
     * Returns a string with all the posts listed
     *
     * @return
     */
    public String getPostsString() {
        String str = "";
        for (int postId : posts) {
            Post post = Post.POST_LIST.get(postId);
            str += post.getPostsString() + "\n";
        }
        return str;
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
     * Returns ID of every single post in this discussion
     *
     * @return every single post in this discussion
     */
    public List<Integer> getAllPosts() {
        List<Integer> returnList = new ArrayList<>();
        for (int postId : posts) {
            Post p = Post.POST_LIST.get(postId);
            returnList.addAll(getPostAndReplies(p));
        }

        return returnList;
    }

    /**
     * Returns ID of target post and all its replies in 1 list
     * By recursively searching through each post's replies
     */
    public List<Integer> getPostAndReplies(Post targetPost) {
        List<Integer> returnList = new ArrayList<>();
        returnList.add(targetPost.getId());
        for (int postId : targetPost.getPosts()) {
            Post p = Post.POST_LIST.get(postId);
            returnList.addAll(getPostAndReplies(p));
        }

        return returnList;
    }

    /**
     * Checks the user's permissions and tries
     * to set the topic of discussion accordingly
     *
     * @return
     */
    public boolean setTopic(String topic, User user) {
        if (user.canModifyDiscussion()) {
            this.topic = topic;
            return true;
        }
        return false;
    }
}
