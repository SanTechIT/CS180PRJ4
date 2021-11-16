import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Project 4 - Course
 * <p>
 * Represents a teacher-created Course which can
 * have Discussions.
 *
 * @author Richard Chang, Sara Xiao
 * @version 2021-11-16
 */
public class Course implements Serializable {
    // As per https://stackoverflow.com/
    // questions/10378855/java-io-invalidclassexception-local-class-incompatible
    @Serial
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
     * (Not to be accessed outside createCourse method)
     *
     * @param topic   topic of course
     * @param creator ID of user trying to create course
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
     * @param topic topic of course
     * @param user  User trying to create course
     * @return created course or null if it failed
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
     * @return id
     */
    public int getId() {
        return id;
    }

    /**
     * Returns the topic of this class
     *
     * @return topic
     */
    public String getTopic() {
        return topic;
    }

    /**
     * Changes topic of course if user has permission
     *
     * @param topic new topic of course
     * @param user  user trying to change course topic
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
     * Returns id of the user who created this course
     *
     * @return creator's id
     */
    public int getCreator() {
        return creator;
    }

    /**
     * Returns the ids of all discussions associated with this course
     *
     * @return discussions
     */
    public List<Integer> getDiscussions() {
        return discussions;
    }

    /**
     * Deletes the course with the given id if permissions allow
     *
     * @param id   id of course to be deleted
     * @param user user trying to delete course
     * @return deleted course or null if no deletion happened
     */
    public static Course deleteCourse(int id, User user) {
        if (!user.canModifyCourse()) {
            return null;
        }
        Course deleted = COURSE_LIST.set(id, null);
        return deleted;
    }

    /**
     * returns String representation of all discussions in course
     *
     * @return formatted discussion list
     */
    public String getDiscussionsString() {
        String str = "";
        for (Integer discussionId : discussions) {
            Discussion discussion = Discussion.DISCUSSION_LIST.get(discussionId);
            str += discussion.getId() + " - " + discussion.getTopic() + "\n";
        }
        return str;
    }

    /**
     * Returns list of all courses with id + course name
     *
     * @return formatted course list
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
