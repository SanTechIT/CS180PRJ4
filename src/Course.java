import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Project 4 - Course
 *
 * Represents a teacher-created Course which can
 * have Discussions.
 *
 * @author Richard Chang, Sara Xiao
 * @version 2021-11-15
 */
public class Course implements Serializable {
    // As per https://stackoverflow.com/
    // questions/10378855/java-io-invalidclassexception-local-class-incompatible
    private static final long serialVersionUID = 01L;

    public static List<Course> COURSE_LIST;
    private int id;
    private String topic;
    private int creator;
    private List<Integer> discussions;

    private Course() {
        // Deny Instantiation
    }

    /**
     * Course Constructor
     * (Not to be Accessed outside create course method)
     *
     * @param topic
     * @param creator
     */
    private Course(String topic, int creator) {
        this.creator = creator;
        this.topic = topic;
        // TOD: Note to self, bad concurrency
        id = COURSE_LIST.size();
        discussions = new ArrayList<>();
        COURSE_LIST.add(this);
    }

    /**
     * Creates and returns a new course object if the user has permission to.
     *
     * @param topic
     * @param user
     * @return
     */
    public static Course createCourse(String topic, User user) {
        if (user.canCreateCourse()) {
            return new Course(topic, user.getId());
        } else {
            return null;
        }
    }

    /**
     * Returns the id of this class
     *
     * @return
     */
    public int getId() {
        return id;
    }

    /**
     * Returns the topic of this class
     *
     * @return
     */
    public String getTopic() {
        return topic;
    }

    /**
     * Changes topic of course if user has permission
     *
     * @param topic new topic of course
     * @param user user trying to change course topic
     * @return whether course topic is changed (whether user had permission)
     */
    public boolean setTopic(String topic, User user) {
        if (!user.canModifyCourse()) {
            return false;
        }
        this.topic = topic;
        return true;
    }

    /**
     * Deletes the discussion with the given id
     *
     * @param id
     * @param user
     * @return
     */
    public static Course deleteCourse(int id, User user) {
        if (!user.canModifyCourse()) {
            return null;
        }
        Course deleted = COURSE_LIST.set(id, null);
        return deleted;
    }

    /**
     * Returns the user who created this course
     *
     * @return
     */
    public int getCreator() {
        return creator;
    }

    /**
     * Returns a list of all discussions associated with this course
     *
     * @return
     */
    public List<Integer> getDiscussions() {
        return discussions;
    }

    /**
     * @return
     */
    public String getDiscussionsString() {
        String str = "";
        for(Integer discussionId: discussions){
            Discussion discussion = Discussion.DISCUSSION_LIST.get(discussionId);
            str += discussion.getId() + " - " + discussion.getTopic() + "\n";
        }
        return str;
    }

    /**
     * Returns list of all courses with id + course name
     *
     * @return
     */
    public static String getCoursesString() {
        String str = "";
        for (int i = 0; i < COURSE_LIST.size(); i++) {
            if (COURSE_LIST.get(i) != null) {
                str += i + " - " + COURSE_LIST.get(i).getTopic() + "\n";
            }
        }
        return str;
    }
}
