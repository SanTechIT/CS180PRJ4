import org.junit.Test;

import static org.hamcrest.CoreMatchers.allOf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

/**
 * Project 4 - Tests Teacher Runner
 * <p>
 * Contains JUnit tests for TeacherRunner (Teacher loop)
 *
 * @author Sara Xiao
 * @version 2021-11-15
 */
public class TestsStudentRunner extends Tests {
    /**
     * Tests view grades feature
     */
    @Test
    public void testViewGrades() {
        String commands =
            LOGIN_STUDENT_S +
            NAVIGATE_TO_DISCUSSION +
            "view grades\n" +
            "back\n" +
            EXIT_STRING;

        setIOStreams(commands);
        Main.main(new String[]{"test"});

        String expected = "> \n" +
            "View Grades - default discussion\n" +
            "You are viewing the grades for every post you have made in this forum.\n" +
            "Minimum grade is 1. If a post has grade 0, it has not been graded yet.\n" +
            "\n" +
            "\n" +
            "|  --------------------\n" +
            "|  Post ID 0\n" +
            "|  (grade: 0/100)\n" +
            "|  test post 0\n" +
            "|  --------------------\n" +
            "|  --------------------\n" +
            "|  Post ID 1 (reply to 0)\n" +
            "|  (grade: 0/100)\n" +
            "|  test post 1\n" +
            "|  --------------------\n" +
            "|  --------------------\n" +
            "|  Post ID 3 (reply to 1)\n" +
            "|  (grade: 0/100)\n" +
            "|  test post 3\n" +
            "|  --------------------\n" +
            "|  --------------------\n" +
            "|  Post ID 2 (reply to 0)\n" +
            "|  (grade: 33/100)\n" +
            "|  test post 2\n" +
            "|  --------------------\n" +
            "|  --------------------\n" +
            "|  Post ID 4 (reply to 2)\n" +
            "|  (grade: 0/100)\n" +
            "|  test post 4\n" +
            "|  --------------------\n" +
            "|  --------------------\n" +
            "|  Post ID 5\n" +
            "|  (grade: 0/100)\n" +
            "|  test post 5\n" +
            "|  --------------------\n" +
            "> ";
        checkOutputContainsExpected(expected);
    }
}