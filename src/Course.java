import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Course Class
 *
 * @author chang794
 * @version 0.2
 */
public class Course implements Serializable {
    public static List<Course> COURSE_LIST;
    private int id;
    private String topic;
    private int creator;
    private List<Discussion> discussions;

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
    public List<Discussion> getDiscussions() {
        return discussions;
    }

    /**
     * @return
     */
    public String getDiscussionsString() {
        // TODO: Reference Integer or Object
        return "";
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
                str += i + " - " + COURSE_LIST.get(i).getTopic();
            }
        }
        return str;
    }

    /**
     * Sets a new topic for the class
     *
     * @param topic
     */
    public void setTopic(String topic, User user) {
        if (user.canModifyCourse()) {
            this.topic = topic;
        }
    }
}
