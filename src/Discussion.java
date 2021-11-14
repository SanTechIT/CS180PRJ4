import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Discussion {
    public static List<Discussion> DISCUSSION_LIST;

    private int id;
    private String topic;
    private List<Post> posts;
    private Course course;
    private Date timestamp;
    private int creator;

    private Discussion() {
        // Deny Instantiation
    }

    private Discussion(Course course, String topic, int creator) {
        this.topic = topic;
        this.creator = creator;
        this.course = course;
        id = DISCUSSION_LIST.size();
        posts = new ArrayList<>();
        DISCUSSION_LIST.add(this);
    }

    /**
     * Creates and returns a new Discussion object if the user has permission to.
     *
     * @param course
     * @param topic
     * @param user
     * @return
     */
    public static Discussion createDiscussion(Course course, String topic, User user) {
        if (!user.canCreateDiscussion()) {
            return null;
        }
        return new Discussion(course, topic, user.getId());

    }

    public static Discussion deleteDiscussion(int id, User user) {
        if (!user.canModifyDiscussion()) {
            return null;
        }
        // TODO: Delete discussion in course
        return DISCUSSION_LIST.set(id, null);
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
        for (Post post : posts) {
            str += post.getPostsString();
        }
        return str;
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
