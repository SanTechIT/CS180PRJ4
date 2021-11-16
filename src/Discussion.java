import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Project 4 - Discussion
 * <p>
 * Represents a teacher-created Discussion which
 * belongs to a Course and has Posts.
 *
 * @author Richard Chang, Sara Xiao
 * @version 2021-11-15
 */
public class Discussion implements Serializable {
    // As per https://stackoverflow.com/
    // questions/10378855/java-io-invalidclassexception-local-class-incompatible
    @Serial
    private static final long serialVersionUID = 01L;

    public static List<Discussion> DISCUSSION_LIST;

    private int id;
    private String topic;
    private List<Integer> posts;
    private int course;
    private Date timestamp;
    private int creator;

    private Discussion() {
        // Deny Instantiation
    }

    /**
     * Constructor for Discussion (always called internally)
     * Not accessible by other classes!
     * Other classes must create Discussion with createDiscussion method
     *
     * @param course  course Discussion belongs to
     * @param topic   topic of Discussion
     * @param creator ID of user creating discussion
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
     * Returns the Id of the discussion
     *
     * @return id
     */
    public int getId() {
        return id;
    }

    /**
     * Returns the topic of the discussion
     *
     * @return topic
     */
    public String getTopic() {
        return topic;
    }

    /**
     * Returns the list of posts associated with this discussion
     *
     * @return posts
     */
    public List<Integer> getPosts() {
        return posts;
    }

    /**
     * Creates and returns a new Discussion object if the user has permission to.
     *
     * @param course parent course
     * @param topic  discussion topic
     * @param user   user creating discussion
     * @return created discussion, null if failed
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
     * @param id   id of discussion to be deleted
     * @param user user trying to delete discussion
     * @return deleted discussion, null if discussion deletion failed
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
     * Returns ID of every single post in this discussion
     *
     * @return every single post in this discussion
     */
    public List<Integer> getAllPosts() {
        List<Integer> returnList = new ArrayList<>();
        for (int postId : posts) {
            Post p = Post.POST_LIST.get(postId);
            returnList.addAll(p.getPostAndReplies());
        }

        return returnList;
    }

    /**
     * Checks the user's permissions and tries
     * to set the topic of discussion accordingly
     *
     * @return operation success boolean
     */
    public boolean setTopic(String topic, User user) {
        if (user.canModifyDiscussion()) {
            this.topic = topic;
            return true;
        }
        return false;
    }
}
