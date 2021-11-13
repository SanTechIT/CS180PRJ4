import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Project 4 - Post
 * <p>
 * Post class
 *
 * @author briankwon25 (Brian Kwon)
 * @version 0.2 - 2021-11-12
 */

public class Post {
    public static List<Post> POST_LIST;

    private int id;
    private String content;
    private int grade;
    private int maxGrade;
    private Post parent;
    private List<Post> posts;
    private int creator;
    private Date timestamp;

    private Post() {
        // Deny Instantiation
    }

    private Post(String content, Post parent, int creator) {
        this.content = content;
        this.parent = parent;
        this.creator = creator;
        this.maxGrade = 100;
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
    public static Post createPost(String content, User user) {
        if (!user.canPost()) {
            return null;
        }
        return new Post(content, null, user.getId());
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
    public static Post createPost(String content, Post parent, User user) {
        if (!user.canPost()) {
            return null;
        }
        return new Post(content, parent, user.getId());
    }

    /**
     * Allows editing of the post if the user has permission to edit or if
     * the user is the creator of the post
     *
     * @param newContent
     * @param user
     * @return
     */
    public boolean editPost(String newContent, User user) {
        if (user.canModifyPost() || user.getId() == creator) {
            this.content = newContent;
            return true;
        }
        return false;
    }

    public Post deletePost(User user) {
        if (user.canModifyPost() || user.getId() == creator) {
            return POST_LIST.set(id, null);
        }
        return null;
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


}
