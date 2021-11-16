# CS180 Project 4 Design Specifications
## Documentation
- Aarini - Submitted Report on Brightspace
- Sara - Submitted code on Vocareum workspace

To compile the program: In the Terminal, cd to the src folder. Run `javac Main.java`.

To run the program: In the Terminal, after compiling, run `java Main`.

To run the test cases: You can use IntelliJ to individually run the test classes for each class. The Tests class contains no tests, only utility methods.

Alternatively, to run the tests, you can run the bash script runTestCases.sh in the src folder.

Using IntelliJ is best, since everything should work on it.

## Notes

- When shutting down, serialize the User List, Course List
- Connect method in user handles all loop logic (better architecture)
- Whenever anything gets deleted, set its index on the List to `null`
- Index on Lists act as IDs
- Always store users using their `id`s in `Course`s/`Discussion`s/`Post`s and store
  `Course`s/`Discussion`s/`Post`s as `id`s in `User`s

_______________

## Class Documentation

### Main Class

#### Fields

| Field      | Signature   | Description | Getter/Setter |
| ---------- | ----------- | ----------- | ------------- |
| useSer     | `private static boolean` | whether serialization is used |  |
| pathSep    | `private static final String` | OS-dependent path separator for file handling |  |

#### Methods

| Method      | Signature   | Parameters | Description |
| ----------- | ----------- | ----------- | ---------- |
| main | `public static void` | `(String[] args)` | Main Method |
| writeData | `private static void` | `(Object obj, String filename)` | Writes the data into the file |
| readData | `private static Serializable` | `(String filename)` | Reads the data |

### User Class (abstract) (Serializable)

#### Fields

| Field       | Signature   | Description | Getter/Setter |
| ----------- | ----------- | ----------- | ------------- |
| posts       | `private List<Integer> posts` | ID of every post the User has made | G |
| userList    | `public static List<User>` | Lists all users |  |
| id          | `private int` | Id is same as index in list | G |
| username    | `private String`  | None | G  |
| password    | `private String`  | NaN | |
| name        | `private String`  | -NaN | G/S |

#### Constructors

| Signature   | Parameters  | Description |
| ----------- | ----------- | ----------- |
| `public` | `(String username, String password, String name)` | Creates new User, adds to USER_LIST |

#### Methods

| Method      | Signature   | Parameters | Description |
| ----------- | ----------- | ---------- | ----------- |
| connect        | `public static void` | `(Scanner in)` |  For user to login with their credentials |
| createAccount  | `public static void` | `(Scanner in)` |  Creates Account and appends to account list, does not log user in |
| getUser        | `public static User` | `(String username)` | Returns user with username, null if none have that username
| modifyUsername | `public void` | `(Scanner in)` |  Modifies username, can only be done to self, username should be unique |
| modifyName     | `public void` | `(Scanner in)`  | Modifies name, can only be done to self   |
| modifyPassword | `public void` | `(Scanner in)` | Modifies password, can only be done to self   |
| deleteAccount  | `public void` | `(Scanner in)` |  Deletes the user account and sets its index in list to null |
| makePostReply  | `public Post` | `(Post parentPost, String newContent, Discussion parentDiscussion)` | Reply to a reply to a discussion form |
| editPost       | `public boolean`   | `(Post targetPost, String newContent)` | Calls post's editPost method to edit post |
| deletePost     | `public boolean`   | `(Post targetPost)` | Calls post's deletePost method to delete post |
| getVoteCount   | `public int`   | `()` | Sums all upvotes and downvotes user's posts have received for a total "vote count" |
| loop           | `public abstract void` | `(Scanner in)` | Primary Loop Handler |
| canVote        | `public abstract boolean` | `()` |  Whether the user has permission to vote |
| canGrade       | `public abstract boolean` | `()` |  Whether the user has permission to grade |
| canPost        | `public abstract boolean` | `()` |  Whether the user has permission to make or reply to posts, and edit their own posts |
| canCreateCourse |`public abstract boolean` | `()` | Whether the user can create courses |
| canModifyCourse |`public abstract boolean` | `()` | Whether the user can modify courses |
| canModifyDiscussion | `public abstract boolean` | `()` |  Whether the user has permission to modify or delete Discussions |
| canModifyPost  | `public abstract boolean` | `()` | (EC) Whether the user has permission to modify or delete Posts made by others |

### UserRunner Class (abstract)

#### Fields

| Field      | Signature   | Description | Getter/Setter |
| ---------- | ----------- | ----------- | ------------- |
| user   | `private User` | user who's logged in |  |
| currentCourse    | `private Course`  | current course user's looking at | G/S |
| currentDiscussion    | `private Discussion`  | current discussion user's looking at | G/S |
| logOut    | `private boolean`  | whether to log out of the program | S |

#### Constructors

| Signature   | Parameters  | Description |
| ----------- | ----------- | ----------- |
| `public` | `(User user)` | Creates new UserRunner |

#### Methods

| Method      | Signature   | Parameters | Description |
| ----------- | ----------- | ---------- | ----------- |
| loop | `public void` | `(Scanner reader)` |  Handles all control flow and UI interaction |
| loopEditAccount | `private void` | `(Scanner reader)` |  loop for editing account |
| loopCourse | `private void` | `(Scanner reader)` |  Loop for 1 course and its discussions |
| loopDiscussion | `private void` | `(Scanner reader)` |  Loop for 1 discussion forum and its posts |
| parse2WordInput | `protected boolean` | `(String input, Scanner reader)` |  Checks whether 2-word input for loopDiscussion has valid length and post number |
| menuPostReply | `private boolean` | `(Post targetPost, Scanner reader)` | Menu for posting reply to other post |
| menuEditPost | `private boolean` | `(Post targetPost, Scanner reader)` | Menu for editing a post |
| loopMainOverride | `protected boolean` | `(Scanner reader, String input)` |  For commands exclusive to Teacher or Student, returns false |
| loopCourseOverride | `protected boolean` | `(Scanner reader, String input)` |  For commands exclusive to Teacher or Student, returns false |
| loopDiscussionOverride | `protected boolean` | `(Scanner reader, String input)` |  For commands exclusive to Teacher or Student, returns false |
| parse2WordInputOverride | `protected boolean` | `(Post targetPost, Scanner reader, String inputWord1)` |  For commands exclusive to Teacher or Student, returns false |


### Teacher Class (extends `User`) (Serializable)

#### Fields

None that aren't inherited.

#### Constructors

| Signature   | Parameters  | Description |
| ----------- | ----------- | ----------- |
| `public` | `(String username, String password, String name)` | Auto sets id to next id in list |

#### Methods

| Method      | Signature   | Parameters | Description |
| ----------- | ----------- | ---------- | ----------- |
| loop | `public void` | `(Scanner in)` |  Primary Loop Handler |
| canVote | `public boolean` | `()` | returns false |
| canGrade | `public boolean` | `()` | returns true |
| canPost | `public boolean` | `()` | returns true |
| canCreateCourse | `public boolean` | `()` | returns true |
| canCreateDiscussion | `public boolean` | `()` | returns true |
| canModifyCourse | `public boolean`  `()` | returns true |
| canModifyDiscussion | `public boolean` | `()` | returns true |
| canModifyPost | `public boolean` | `()` | returns true |
| createCourse | `public boolean` | `(String topic)` | Creates course |
| editCourse | `public boolean` | `(String newTopic, Course course)` | Edits course topic |
| deleteCourse | `public boolean` | `(Course course)` | Deletes course |
| createDiscussion | `public boolean` | `(String topic, Course course)` | Creates discussion |
| editDiscussion | `public boolean` | `(Dicussion discussion, String newTopic)` | Edits discussion forum |
| deleteDiscussion | `public boolean` | `(Discussion discussion)` | Deletes discussion form  |
| gradePost | `public boolean` | `(Post targetPost, int grade)` | Teachers can assign a point value to student's work|

### TeacherRunner Class

#### Fields

| Field      | Signature   | Description | Getter/Setter |
| ---------- | ----------- | ----------- | ------------- |
| teacher   | `private Teacher` | Teacher who's logged in |  |

#### Constructors

| Signature   | Parameters  | Description |
| ----------- | ----------- | ----------- |
| `public` | `(Teacher teacher)` | Creates new TeacherRunner |

#### Methods

| Method      | Signature   | Parameters | Description |
| ----------- | ----------- | ---------- | ----------- |
| loopMainOverride | `protected boolean` | `(Scanner reader, String input)` |  Handles the menu options just for teacher |
| menuCreateCourse | `private void` | `(Scanner reader)` |  The menu for creating new course |
| loopViewStudent | `private void` | `(Scanner reader)` |  Shows the dashabord and the data with the name and vote count |
| loopIndividualStudent | `private void` | `(Scanner reader, Student currentStudent)` |  Loop for viewing/editing all posts of 1 student |
| loopCourseOverride | `protected boolean` | `(Scanner reader, String input)` |  For menu options exclusive to teacher |
| menuCreateDiscussion | `private void` | `(Scanner reader)` |  The menu for creating new discussion forum |
| menuEditCourse | `private void` | `(Scanner reader)` |  The menu for editing course topic  |
| menuDeleteCourse | `private void` | `(Scanner reader)` | The menu for deleting a course |
| loopDiscussionOverride | `protected boolean` | `(Scanner reader, String input)` |  Deleting discussion forum menu option for teacher |
| menuEditDiscussion | `private void` | `(Scanner reader)` |  The menu for editing discussion topic |
| loopViewVoteboard | `private void` | `(Scanner reader)` |  Shows the dashboard by the number of votes |
| parse2WordInputStudent | `protected boolean` | `(Scanner reader, String input)` |  Modified version of UserRunner's parse2WordInput for the viewIndividualStudent menu |
| parse2WordInputOverride | `protected boolean` | `(Post targetPost, Scanner reader, String inputWord1)` |  Parses the word given as input |
| menuGradePost | `private boolean` | `(Post targetPost, Scanner reader)` | menu for grading a post |
| menuDeletePost | `private boolean` | `(Post targetPost, Scanner reader)` | menu for deleting a post |


### Student Class (extends `User`) (Serializable)

#### Fields

| Field      | Signature   | Description | Getter/Setter |
| ---------- | ----------- | ----------- | ------------- |
| votedPosts | ` private HashMap<Integer, Integer>` | ID of every post the student has voted on |  |

#### Constructors

| Signature   | Parameters  | Description |
| ----------- | ----------- | ----------- |
| `public` | `(String username, String password, String name)` | Auto sets id to next id in list |

#### Methods

| Method      | Signature   | Parameters | Description |
| ----------- | ----------- | ---------- | ----------- |
| loop | `public void` | `(Scanner in)` |  Primary Loop Handler |
| canVote | `public boolean` | `()` | returns true |
| canGrade | `public boolean` | `()` | returns false |
| canPost | `public boolean` | `()` | returns true |
| canCreateCourse | `public boolean` | `()` | returns false |
| canModifyCourse | `public boolean`  `()` | returns false |
| canModifyDiscussion | `public boolean` | `()` | returns false |
| canModifyPost | `public boolean` | `()` | returns false |
| canCreateDiscussion | `public boolean` | `()` | returns false |
| makeDiscussionReply | `public Post` | `(String newContent, Discussion parentDiscussion)` | Reply directly to a discussion form |
| getPostsString | `public String` | `()` | Gets the Posts String |
| getPostVote | `public int` | `(Post targetPost)` | returns whether votedPost has been upvoted or downvoted by user |
| upvotePost | `public boolean` | `(Post targetPost)` | Upvote post if possible |
| downvotePost | `public boolean` | `(Post targetPost)` | Downvote post if possible |
| noVotePost | `public boolean` | `(Post targetPost)` | Remove student's vote on post |

### StudentRunner Class (extends `UserRunner`)

#### Fields

| Field      | Signature   | Description | Getter/Setter |
| ---------- | ----------- | ----------- | ------------- |
| student | ` private Student` | Student who's logged in | |

#### Constructors

| Signature   | Parameters  | Description |
| ----------- | ----------- | ----------- |
| `public` | `(Student student)` | Creates new StudentRunner |

#### Methods

| Method      | Signature   | Parameters | Description |
| ----------- | ----------- | ---------- | ----------- |
| loopDiscussionOverride | `protected boolean` | `(Scanner reader, String input)` |  Replying to discussion forum menu option for student |
| menuViewGrades | `private boolean` | `(Scanner reader)` |  Menu for viewing all grades received for all posts in the forum (Student exclusive) |
| menuDiscussionReply | `private boolean` | `(Scanner reader)` |  Menu for replying to a discussion form |
| parse2WordInputOverride | `protected boolean` | `(Scanner reader, String input)` |  Parses the word given as input |

### Course Class (Serializable)

#### Fields

| Field      | Signature   | Description |
| ---------- | ----------- | ----------- |
| serialVersionUID | `private static final long` | Required for serialization | |
| courseList    | `public static List<Course>` | Lists all courses | |
| discussions | `private List<Integer>` | IDs of all `Discussion`s of course| G |
| id | `private int` | Id is same as index in list | G |
| topic | `private String` | Course name, can only be changed by a `Teacher` | G/S |
| creator | `private int` | Keeps track of the UID of who created this `Course` | G |

#### Constructors

| Signature   | Parameters  | Description |
| ----------- | ----------- | ----------- |
| `private` | `()` | Deny instantiation; courses are created with createCourse |
| `private` | `(String topic, int creator)` | Auto sets id to next id in list (called internally) |

#### Methods

| Method      | Signature   | Parameters | Description |
| ----------- | ----------- | ---------- | ----------- |
| createCourse | `public static Course ` | `(String topic, User user)` | Creates and returns a new course  object if the user has permission to |
| deleteCourse | `public static Course ` | `(int id, User user)` | Deletes the course with the given id if permissions allow |
| getDiscussionsString | `public String ` | `()` | Returns list of all discussions with id + discussion topic (see Console Example) |
| getCoursesString | `public static String` | `()`  |  Returns list of all courses with id + course name (see Console Example) |
| setTopic | `public boolean` | `(String newTopic, User user)` | Changes topic of course if permission allows |

### Discussion Class (Serializable)

#### Fields

| Field      | Signature   | Description | Getter/Setter |
| ---------- | ----------- | ----------- | ------------- |
| serialVersionUID | `private static final long` | Required for serialization | |
| discussionList   | `public static List<Discussion>` | Lists all `Discussion`s |  |
| id | `private int` | Id is same as index in list | G |
| topic | `private String` | Discussion Topic | G/S |
| posts | `private List<Integer>` | IDs of all posts related to this `Discussion` | G |
| course | `private int` | ID of Parent Course | G |
| timestamp | `private Date` | Keeps track of when the `Discussion` was created | G/S |
| creator | `private int` | Keeps track of the user ID of who created this `Discussion` | G |

#### Constructors

| Signature   | Parameters  | Description |
| ----------- | ----------- | ----------- |
| `private` | `()` | Deny instantiation; discussions are created with createDiscussion |
| `private` | `(Course course, String topic, int creator)` | Auto sets id to next id in list |

#### Methods

| Method      | Signature   | Parameters | Description |
| ----------- | ----------- | ---------- | ----------- |
| createDiscussion | `public static Discussion` | `(Course course, String topic, User user)` |  Creates and returns a new Discussion object if the user has permission to |
| deleteDiscussion | `public static Discussion` | `(int id, User user)` |  Deletes the discussion with the given id |
| getAllPosts | `public List<Integer>` | `()` | Returns ID of every post in the discussion |
| setTopic | `public boolean` | `(String newTopic, User user)` | Changes topic of discussion if permission allows |

### Post Class (Serializable)

Note: posts can be under both discussions and other posts

#### Fields

| Field      | Signature   | Description | Getter/Setter |
| ---------- | ----------- | ----------- | ------------- |
| serialVersionUID | `private static final long` | Required for serialization | |
| postList  | `public static List<Post>` | Lists all `Post`s |  |
| id | `private int` | `id` is same as index in list | G |
| posts | `private List<Integer>` | IDs of all posts related to this `Post` | G |
| timestamp | `private Date` | Keeps track of when the `Post` was created | G/S |
| creatorID | `private int` | Keeps track of the user ID of who created this `Post` | G |
| content | `private String` | Keeps track of the contents in this `Post`, can be changed only by `creator` (EC or a `Teacher`) | G/S |
| grade | `private int` | Keeps track of the grade of this post `Post`, Only shown if `User` is creator of `Post` or is a `Teacher` | G |
| maxGrade | `private int` | Keeps track of the max grade of this `Post`, Only shown if `User` is creator of `Post` or is a `Teacher`  | G |
| upvotes | `private int` | Number of upvotes | G |
| downvotes | `private int` | Number of downvotes | G |
| parent | `private int`  | ID of post that this Post is a reply to; null if this Post is not a reply   | G  |
| discussion  | `private int` | ID of discussion that this Post is a part of  | G |

#### Constructors

| Signature   | Parameters  | Description |
| ----------- | ----------- | ----------- |
| `private` | `(String content, Discussion discussion, Post parent, User user)` | Constructor for Post (always called internally) |

#### Methods

| Method      | Signature   | Parameters | Description |
| ----------- | ----------- | ---------- | ----------- |
| createPost | `public static`   | `(String content, Discussion discussion, Post parent, User user)` | Returns a new post if the user has permission to post |
| editPost | `public boolean`   | `(String newContent, User user)` | Replaces post content with newContent if permission allows |
| deletePost | `public Post`   | `(User user)` | Deletes post if permission allows |
| grade | `public boolean`   | `(User user, int grade)` | Grades the post if the user has permission |
| getPostAndReplies | `public List<Integer>` | `()` | Returns list with ID of post and all its replies |
| getControversy | `public int`   | `()` | Returns an int representing controversy |
| upvote | `public boolean`   | `(User user, int oldVote)` | Decides if the user can vote, if so, then upvotes |
| downvote | `public boolean`   | `(User user, int oldVote)` | Decides if the user can vote, if so, then downvotes |
| removeVote | `public boolean`   | `(int oldVote)` | Removes an upvote/downvote based on value of oldVote |

### Display Class

##### Methods

| Method      | Signature   | Parameters | Description |
| ----------- | ----------- | ---------- | ----------- |
| displayStart | `public static void` | `()`  |  Displays output for main login/create account loop |
| displayWelcome | `public static void` | `(User user)`  |  Displays output for User main loop (viewing all courses after login) |
| displayBadInput | `public static void` | `()`  |  Displays output for invalid input |
| displayExit | `public static void` | `()`  |  Displays output for program exit |
| displayLogout | `public static void` | `()`  |  Displays output for logging out |
| displayCreateCourse | `public static void` | `()`  |  Displays output for creating a course (accessed from main menu) |
| displayCourse | `public static void` | `(Course currentCourse, User user)`  |  Displays output for course loop (viewing all discussions in 1 course) |
| displayCreateDiscussion | `public static void` | `()`  |  Displays output for creating a discussion |
| displayEditDiscussion | `public static void` | `()`  |  Displays output for editing a discussion |
| displayDiscussion | `public static void` | `(Discussion currentDiscussion, User user)`  |  Displays output for discussion loop (viewing all posts in 1 discussion) |
| getDiscussionString | `private static String` | `(Discussion discussion, User user)`  |  Gets the posts of direct child in discussion and return as string |
| getPostStrings | `private static String` | `(Post postin, int indent, User user)`  |  Gets string representation of 1 post + all its replies |
| getPostString | `private static String` | `(Post postin, int indent, User user)`  | Returns string representation of a post |
| displayDeleteCourse | `public static void` | `(Course currentCourse)`  |  Displays output for deleting a course |
| displayEditCourse | `public static void` | `()`  |  Displays output for editing a course topic |
| displayDiscussionReply | `public static void` | `(Discussion currentDiscussion)`  |  Displays menu for replying directly to discussion (Student only) |
| displayPostReply | `public static void` | `(Post targetPost)`  |  Displays menu for replying to post |
| displayEditPost | `public static void` | `(Post targetPost)`  |  Displays menu for editing post |
| displayDeletePost | `public static void` | `(Post targetPost)`  |  Displays menu for deleting post |
| displayGradePost | `public static void` | `(Post targetPost)`  |  Displays menu for grading post |
| displayEditAccount | `public static void` | `(User user)`  |  Displays output for edit account loop (an option from the main loop) |
| displayModifyUsername | `public static void` | `(User user)`  |  Displays output for modifying the username |
| displayModifyName | `public static void` | `(User user)`  |  Displays output for modifying the name |
| displayModifyPassword | `public static void` | `(User user)`  |  Displays output for modifying the password |
| displayDeleteAccount | `public static void` | `(User user)`  |  Displays output for deleting an account |
| displayViewStudent | `public static void` | `()`  |  Displays output for view student loop (option from the main loop) |
| displayIndividualStudent | `public static void` | `(Student currentStudent)`  |  Displays output for an individual student |
| displayPostsDepth0 | `private static void` | `(List<Integer> posts, User user)` |  Displays list of posts without indenting |
| displayViewVoteboard | `public static void` | `(Discussion currentDiscussion, String currentSort, User user)` | Displays voteboard, which shows all posts in a forum ranked by votes with sorting options. |
| displayViewGrades | `public static void` | `(Discussion currentDiscussion, Student currentStudent)` | Displays grades for all of 1 student's posts in 1 forum |
| displayPostsGrades | `public static void` | `(List<Integer> posts, User user)` | Given a list of posts, prints them all in order with 0 depth AND INCLUDES ONLY GRADE INFO for displayViewGrades |
| toStringGrades | `public static String`   | `(Post postin)` | Returns post as formatted string (used when student is viewing grades) |

_______________

## Test Classes

### Tests

#### Fields
| Field      | Signature   | Description | Getter/Setter |
| ---------- | ----------- | ----------- | ------------- |
| OUT_STREAM | `private static final PrintStream` | For changing IO streams during testing | |
| out  | `private ByteArrayOutputStream` | For setting output stream to program output | G/S  |
| CREATE_TEACHER  | `public static final String` | For easily executing a set of commands during testing |  |
| CREATE_STUDENT  | `public static final String` | For easily executing a set of commands during testing |  |
| LOGIN_TEACHER  | `public static final String` | For easily executing a set of commands during testing |  |
| LOGIN_STUDENT_ALICE  | `public static final String` | For easily executing a set of commands during testing |  |
| LOGIN_STUDENT_S  | `public static final String` | For easily executing a set of commands during testing |  |
| CREATE_DEFAULT_CLASSES  | `public static final String` | For easily executing a set of commands during testing |  |
| CREATE_DISCUSSION  | `public static final String` | For easily executing a set of commands during testing |  |
| CREATE_SINGLE_CLASS  | `public static final String` | For easily executing a set of commands during testing |  |
| CREATE_DEFAULT_DISCUSSIONS  | `public static final String` | For easily executing a set of commands during testing |  |
| NAVIGATE_TO_DISCUSSION  | `public static final String` | For easily executing a set of commands during testing |  |
| EXIT_STRING  | `public static final String` | For easily executing a set of commands during testing |  |

#### Methods
| Method      | Signature   | Parameters | Description |
| ----------- | ----------- | ---------- | ----------- |
| setIOStreams | `public void` | `(String commands)`  | Changes I/O streams to pipe a passed-in String to the program |
| checkOutputContainsExpected | `public void` | `(String expected)`  | Asserts that program output contains expected String |
| setIOStreamsAfter | `public void` | `()`  | Changes I/O streams after a test is over so program output can be seen |

### TestsCourse

#### Methods
| Method      | Signature   | Parameters | Description |
| ----------- | ----------- | ---------- | ----------- |
| main | `public static void` | `(String[] args)`  | executes JUnit tests |
| testCreateCourseTeacher | `public void` | `()`  | Tests if teacher can create courses |
| testCreateCourseStudent | `public void` | `()`  | Tests if a student can create courses (should not) |
| testAccessCourseStudent | `public void` | `()`  | Tests if user can see courses and if invalid course id crashes |
| testDeleteCourseTeacher | `public void` | `()`  | Tests if teacher can delete courses |

### TestsDiscussion

#### Methods
| Method      | Signature   | Parameters | Description |
| ----------- | ----------- | ---------- | ----------- |
| main | `public static void` | `(String[] args)`  | executes JUnit tests |
| testCreateDiscussionTeacher | `public void` | `()`  | Tests if teachers can create discussions |
| testCreateDiscussionStudent | `public void` | `()`  | Checks students can't create discussions |
| testDeleteDiscussionTeacher | `public void` | `()`  | Checks teachers can delete discussions |
| testDeleteDiscussionStudent | `public void` | `()`  | Checks students can't delete discussions |

### TestsMain

#### Methods
| Method      | Signature   | Parameters | Description |
| ----------- | ----------- | ---------- | ----------- |
| main | `public static void` | `(String[] args)`  | executes JUnit tests |
| testBasic | `public void` | `()`  | Tests running the program |
| testLoginLogout | `public void` | `()`  | Tests logging in and out |
| testInvalidInput | `public void` | `()`  | Tests handling invalid input |
| testCreateAccountLogin | `public void` | `()`  | Tests account creation and logging in with new account |
| testInvalidUsername | `public void` | `()`  | Tests account creation with username that's already been used |

### TestsPost

#### Methods
| Method      | Signature   | Parameters | Description |
| ----------- | ----------- | ---------- | ----------- |
| main | `public static void` | `(String[] args)`  | executes JUnit tests |
| testReplyTeacherStudent | `public void` | `()`  | Tests if students can reply to posts |
| testVote | `public void` | `()`  | Tests if voting works properly |
| testGradeTeacher | `public void` | `()`  | Tests if teachers can grade properly |
| testGradeStudent | `public void` | `()`  | Tests if students can't grade (they shouldn't) |

### TestsStudentRunner

#### Methods
| Method      | Signature   | Parameters | Description |
| ----------- | ----------- | ---------- | ----------- |
| main | `public static void` | `(String[] args)`  | executes JUnit tests |
| testViewGrades | `public void` | `()`  | Tests if students can use "view grades" feature properly |

### TestsTeacherRunner

#### Methods
| Method      | Signature   | Parameters | Description |
| ----------- | ----------- | ---------- | ----------- |
| main | `public static void` | `(String[] args)`  | executes JUnit tests |
| testNavigation | `public void` | `()`  | Tests basic menu nav to a discussion forum |
| testViewStudent | `public void` | `()`  | Tests view student feature |
| testDeletePost | `public void` | `()`  | Tests teacher can delete post |

_______________

## Console Example

* *To write a new example, put 3 backticks at the start and end to make it a code block - otherwise Markdown formatting
  makes it weird*

Note: output shown in console examples is not final. Test cases contain verification for proper output.

___

### Main Loop:

```
Welcome to [Name]!
Please type one of these commands:
login
create account
exit

Example input:<br>
[login]
```

___

### Student Loop:

##### Student Example 1 (contains general commands)

```
Welcome [Name]!
Please type the number of a course to view:
[1] [Course Name]
[2] [Course Name]
[3] [Course Name]
[4] [Course Name]
Or, please type one of these commands:
edit account
delete account
create course
view student
logout

Example input:
[3]

Welcome to [Course Name]!
Please type the number of a discussion to view:
[1] [Discussion Name]
[22] [Discussion Name]
[25] [Discussion Name]
Or, please type one of these commands:
back
create forum
delete forum
logout

Example input:
[22]

[Discussion Topic]
Commands: back, reply to discussion, reply [num], edit [num], delete [num], upvote [num], downvote [num], logout
Replace [num] with the number of the post you want to interact with!
[12] [Post Content]
[33] [Post Content]
[98] [Post Content]
[113] [Post Content]

Example input:
[reply 33]

Reply to post 33:
You are replying to an existing post in the discussion.
What should be the content in your new reply post?

Example input:
[contents of post]

New post [114] (reply to [33]) has been created!
-----
[Discussion Topic]
Commands: back, reply to discussion, reply [num], edit [num], delete [num], upvote [num], downvote [num], logout
Replace [num] with the number of the post you want to interact with!
[12] [Post Content]
[33] [Post Content]
[98] [Post Content]
[113] [Post Content]
[114] (reply to 33) [Post Content]

Example input:
[edit 12]

Edit post 12:
What should be the new content in post 12?

Example input:
[contents of post]

Post [12] has been edited!
-----
[Discussion Topic]
Commands: back, reply to discussion, reply [num], edit [num], delete [num], upvote [num], downvote [num], logout
Replace [num] with the number of the post you want to interact with!
[12] [Post Content]
[33] [Post Content]
[98] [Post Content]
[113] [Post Content]
[114] (reply to 33) [Post Content]

Example input:
[delete 12]

Delete post 12:
Deleted posts can't be recovered. Are you sure you want to do this? Type yes to confirm.

Example input:
[yes]

Post 12 has been deleted.
-----
[Discussion Topic]
Commands: back, reply to discussion, reply [num], edit [num], delete [num], upvote [num], downvote [num], logout
Replace [num] with the number of the post you want to interact with!
[33] [Post Content]
[98] [Post Content]
[113] [Post Content]
[114] (reply to 33) [Post Content]

Example input:
[logout]

```

#### Student Example 2 (incorrect input and logging out)

```
Welcome [Name]!
Please type the number of a course to view:
[1] [Course Name]
[2] [Course Name]
[3] [Course Name]
[4] [Course Name]
Or, please type one of these commands:
edit account
delete account
create course
view student
logout

Example input:
[aklsdjflkdsanhfcl]

Input Error:
Sorry, I couldn't understand what you typed. Please try again!
-----
Welcome [Name]!
Please type the number of a course to view:
[1] [Course Name]
[2] [Course Name]
[3] [Course Name]
[4] [Course Name]
Or, please type one of these commands:
edit account
delete account
create course
view student
logout

Example input:
[logout]

Exit:
Logging out...
Thank you for using our program. Goodbye!
```

#### Student Example 3 (edit username)

```
Welcome [Name]!
Please type the number of a course to view:
[1] [Course Name]
[2] [Course Name]
[3] [Course Name]
[4] [Course Name]
Or, please type one of these commands:
edit account
delete account
create course
view student
logout

Example input:
[edit account]

Editing Your Account - [Username]:
Please type one of these commands:
back
change username
change name
change password
logout

Example input:
[change username]

Editing Your Account - [Username]:
Current username: [current username]
What would you like your new username to be? It can't be an already existing username.

Example input:
[username that exists]

Sorry! You can't use that username.
Editing Your Account - [Username]:
Please type one of these commands:
back
change username
change name
change password
logout
```

---

```
Example input:
[change username]

Editing Your Account - [Username]:
Current username: [current username]
What would you like your new username to be? It can't be an already existing username.

Example input:
[username that DOESN'T already exist]

Congratulations! You have changed your username.
Editing Your Account - [Username]:
Please type one of these commands:
back
change username
change name
change password
logout
```

#### Student Example 4 (delete account)

```
Welcome [Name]!
Please type the number of a course to view:
[1] [Course Name]
[2] [Course Name]
[3] [Course Name]
[4] [Course Name]
Or, please type one of these commands:
edit account
delete account
create course
view student
logout

Example input:
[delete account]

Delete Account - [Username]:
Deleted accounts can't be recovered. Are you sure you want to do this? Type yes to confirm.

Example input:
[yes]

Your account has been deleted.
Welcome to [Name]!
Please type one of these commands:
login
create account
logout
```

___

### Teacher Loop:

* The menus for the teacher loop include a "back" option.

##### Teacher Example 1 (contains general commands)

```
Welcome [Name]!
Please type the number of a course to view:
[1] [Course Name]
[2] [Course Name]
[3] [Course Name]
[4] [Course Name]
Or, please type one of these commands:
edit account
delete account
create course
view student
logout

Example input:
[3]

Welcome to [Course Name]!
Please choose a discussion to view:
[1] [Discussion Name]
[22] [Discussion Name]
[25] [Discussion Name]
Or, please type one of these commands:
back
create forum
delete forum
logout

Example input:
[22]

[Discussion Topic]
Commands: back, reply [num], edit [num], delete [num], grade [num], logout
Replace [num] with the number of the post you want to interact with!
[12] [Post Content]
[33] [Post Content]
[98] [Post Content]
[113] [Post Content]

Example input:
[reply 33]

Reply to post 33:
You are replying to an existing post in the discussion.
What should be the content in your new reply post?

Example input:
[contents of post]

New post [114] (reply to [33]) has been created!
-----
[Discussion Topic]
Commands: back, reply [num], edit [num], delete [num], grade [num], logout
Replace [num] with the number of the post you want to interact with!
[12] [Post Content]
[33] [Post Content]
[98] [Post Content]
[113] [Post Content]
[114] (reply to 33) [Post Content]

Example input:
[edit 12]

Edit post 12:
What should be the new content in post 12?

Example input:
[contents of post]

Post [12] has been edited!
-----
[Discussion Topic]
Commands: back, reply [num], edit [num], delete [num], grade [num], logout
Replace [num] with the number of the post you want to interact with!
[12] [Post Content]
[33] [Post Content]
[98] [Post Content]
[113] [Post Content]
[114] (reply to 33) [Post Content]

Example input:
[delete 12]

Delete post 12:
Deleted posts can't be recovered. Are you sure you want to do this? Type yes to confirm.

Example input:
[yes]

Post 12 has been deleted.
-----
[Discussion Topic]
Commands: back, reply [num], edit [num], delete [num], grade [num], logout
Replace [num] with the number of the post you want to interact with!
[33] [Post Content]
[98] [Post Content]
[113] [Post Content]
[114] (reply to 33) [Post Content]

Example input:
[grade 98]

Grade post 98:
The minimum grade is 0, and the maximum grade is [max grade].
Enter the grade to assign to post 98:

Example input:
[66]

Post 98 has been assigned the grade: 66/[max grade].
-----
[Discussion Topic]
Commands: back, reply [num], edit [num], delete [num], grade [num], logout
Replace [num] with the number of the post you want to interact with!
[33] [Post Content]
[98] (grade: 66/[max grade]) [Post Content]
[113] [Post Content]
[114] (reply to 33) [Post Content]


[logout]
```

##### Teacher Example 2 (view student dashboard & grade posts)

```
Welcome [Name]!
Please type the number of a course to view:
[1] [Course Name]
[2] [Course Name]
[3] [Course Name]
[4] [Course Name]
Or, please type one of these commands:
edit account
delete account
create course
view student
logout

Example input:
[view student]

View Student:
This shows all of a student's posts and lets you grade them.
Enter the name or ID of the student to view:
Or, please type one of these commands:
back
logout

Example input:
[a valid student id]

[Student Name]'s Posts
Commands: back, reply [num], edit [num], delete [num], grade [num], logout
Replace [num] with the number of the post you want to interact with!
[23] [Post Content]
[35] [Post Content]
[42] [Post Content]
logout

Example input:
[edit 23]
[delete 42]
[grade 35]
logout
```

#### Teacher Example 3 (incorrect input and logouting program)

```
Welcome [Name]!
Please type the number of a course to view:
[1] [Course Name]
[2] [Course Name]
[3] [Course Name]
[4] [Course Name]
Or, please type one of these commands:
edit account
delete account
create course
view student
logout

Example input:
[aklsdjflkdsanhfcl]

Input Error:
Sorry, I couldn't understand what you typed. Please try again!
-----
Welcome [Name]!
Please type the number of a course to view:
[1] [Course Name]
[2] [Course Name]
[3] [Course Name]
[4] [Course Name]
Or, please type one of these commands:
edit account
delete account
create course
view student
logout

Example input:
[logout]

Exit:
Logging out...
Thank you for using our program. Goodbye!
```

#### Teacher Example 4 (edit username)

```
Welcome [Name]!
Please type the number of a course to view:
[1] [Course Name]
[2] [Course Name]
[3] [Course Name]
[4] [Course Name]
Or, please type one of these commands:
edit account
delete account
create course
view student
logout

Example input:
[edit account]

Editing Your Account - [Username]:
Please type one of these commands:
back
change username
change name
change password
logout

Example input:
[change username]

Editing Your Account - [Username]:
Current username: [current username]
What would you like your new username to be? It can't be an already existing username.

Example input:
[username that exists]

Sorry! You can't use that username.
Editing Your Account - [Username]:
Please type one of these commands:
back
change username
change name
change password
logout
```

---

```
Example input:
[change username]

Editing Your Account - [Username]:
Current username: [current username]
What would you like your new username to be? It can't be an already existing username.

Example input:
[username that DOESN'T already exist]

Congratulations! You have changed your username.
Editing Your Account - [Username]:
Please type one of these commands:
back
change username
change name
change password
logout
```

#### Teacher Example 5 (delete account)

```
Welcome [Name]!
Please type the number of a course to view:
[1] [Course Name]
[2] [Course Name]
[3] [Course Name]
[4] [Course Name]
Or, please type one of these commands:
edit account
delete account
create course
view student
logout

Example input:
[delete account]

Delete Account - [Username]:
Deleted accounts can't be recovered. Are you sure you want to do this? Type yes to confirm.

Example input:
[yes]

Your account has been deleted.
Welcome to [Name]!
Please type one of these commands:
login
create account
logout
```

#### Teacher Example 6 (create course)

```
Welcome [Name]!
Please type the number of a course to view:
[1] [Course Name]
[2] [Course Name]
[3] [Course Name]
[4] [Course Name]
Or, please type one of these commands:
edit account
delete account
create course
view student
logout

Example input:
[create course]

Creating Course:
Please enter the name of the new course:

Example input:
[course name]

Course created successfully!
-----
Welcome [Name]!
Please type the number of a course to view:
[1] [Course Name]
[2] [Course Name]
[3] [Course Name]
[4] [Course Name]
[5] [Course Name]
Or, please type one of these commands:
edit account
delete account
create course
view student
logout

```
