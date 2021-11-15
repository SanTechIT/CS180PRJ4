# [Name] Design specifications
## Documentation
- Aarini - Submitted Report on Brightspace
- Richard - Submitted Vocareum workspace.

To compile the program in Vocareum: In the Terminal, cd to the src folder.
To run the program in Vocareum: Run javac Main.java. Run java Main.
Note: Using IntelliJ is best, everything works on it
## Notes

- When shutting down, serialize the User List, Course List
- Connect method in user handles all loop logic (better architecture)
- Whenever anything gets deleted, set its index on the List to `null`
- Index on Lists act as IDs
- Always store users using their `id`s in `Course`s/`Discussion`s/`Post`s and store
  `Course`s/`Discussion`s/`Post`s as `id`s in `User`s

## Main Class

| Method      | Signature   | Parameters | Description |
| ----------- | ----------- | ----------- | ---------- |
| main | `public static void` | `(String[] args)` | Main Method |
| writeData | `private static void` | `(Object obj, String filename)` | Writes the data into the file |
| readData | `private static Serializable` | `(String filename)` | Reads the data |

## User Class (abstract) (Serializable)

##### Fields

| Field      | Signature   | Description | Getter/Setter |
| ---------- | ----------- | ----------- | ------------- |
| USER_LIST   | `public static List<User>` | Lists all users |  |
| username    | `private String`  | None | G  |
| password    | `private String`  | NaN | |
| name        | `private String`  | -NaN | G/S |
| id | `private int` | Id is same as index in list | G |

#### Constructors

| Signature   | Parameters  | Description |
| ----------- | ----------- | ----------- |
| `public` | `(String username, String password, String name)` |  |

##### Methods

| Method      | Signature   | Parameters | Description |
| ----------- | ----------- | ---------- | ----------- |
| connect        | `public static void` | `(Scanner in)` |  For user to login with their credentials |
| loop           | `public abstract void` | `(Scanner in)` | Primary Loop Handler |
| FIXME | FIXME | FIXME | FIXME |
| createAccount  | `public static void` | `(Scanner in)` |  Creates Account and appends to account list, does not log user in |
| modifyUsername  | `public void` | `(Scanner in)` |  Modifies username, can only be done to self, username should be unique |
| modifyName | `public void` | `(Scanner in)`  | Modifies name, can only be done to self   |
| modifyPassword  | `public void` | `(Scanner in)` | Modifies password, can only be done to self   |
| deleteAccount  | `public void` | `(Scanner in)` |  Deletes the user account and sets its index in list to null |
| canVote  | `public abstract boolean` | `()` |  Whether the user has permission to vote |
| canGrade  | `public abstract boolean` | `()` |  Whether the user has permission to grade |
| canPost  | `public abstract boolean` | `()` |  Whether the user has permission to make or reply to posts, and edit their own posts |
| canCreateCourse |`public abstract boolean` | `()` | Whether the user can create courses |
| canModifyCourse |`public abstract boolean` | `()` | Whether the user can modify courses |
| canModifyDiscussion | `public abstract boolean` | `()` |  Whether the user has permission to modify or delete Discussions |
| canModifyPost | `public abstract boolean` | `()` | (EC) Whether the user has permission to modify or delete Posts made by others |

## UserRunner Class (abstract)

##### Fields

| Field      | Signature   | Description | Getter/Setter |
| ---------- | ----------- | ----------- | ------------- |
| user   | `private User` | user who's logged in |  |
| currentCourse    | `private Course`  | current course user's looking at | G/S |
| currentDiscussion    | `private Discussion`  | current discussion user's looking at | G/S |
| logOut    | `private boolean`  | whether to logout of the program | G/S |

#### Constructors

| Signature   | Parameters  | Description |
| ----------- | ----------- | ----------- |
| `public` | `(User user)` | Creates new UserRunner |

##### Methods

| Method      | Signature   | Parameters | Description |
| ----------- | ----------- | ---------- | ----------- |
| loop | `public void` | `(Scanner reader)` |  Handles all control flow and UI interaction |
| loopEditAccount | `private void` | `(Scanner reader)` |  loop for editing account |
| loopCourse | `private void` | `(Scanner reader)` |  Loop for 1 course and its discussions |
| loopdiscussion | `private void` | `(Scanner reader)` |  Loop for 1 discussion forum and its posts |
| parse2WordInput | `protected boolean` | `(String input, Scanner reader)` |  Checks whether 2-word input for loopDiscussion has valid length and post number |
| menuPostReply | `private boolean` | `(Post targetPost, Scanner reader)` | Menu for posting reply to other post |
| menuEditPost | `private boolean` | `(Post targetPost, Scanner reader)` | Menu for editing a post |
| menuDeletePost | `private boolean` | `(Post targetPost, Scanner reader)` | Menu for deleting a post |
| loopMainOverride | `protected boolean` | `(Scanner reader, String input)` |  For commands exclusive to Teacher or Student, returns false |
| loopCourseOverride | `protected boolean` | `(Scanner reader, String input)` |  For commands exclusive to Teacher or Student, returns false |
| loopDiscussionOverride | `protected boolean` | `(Scanner reader, String input)` |  For commands exclusive to Teacher or Student, returns false |
| parse2WordInputOverride | `protected boolean` | `(Post targetPost, Scanner reader, String inputWord1)` |  For commands exclusive to Teacher or Student, returns false |


## Teacher Class (extends `User`) (Serializable)

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
| canModifyDiscussion | `public boolean` | `()` | returns true |
| canModifyPost | `public boolean` | `()` | returns true |
| createCourse | `public boolean` | `()` | Creates course |
| createDiscussion | `public boolean` | `()` | Creates discussion |
| editDiscussion | `public boolean` | `()` | Edits discussion form |
| deleteDiscussion | `public boolean` | `()` | Deletes discussion form  |
| gradePost | `public boolean` | `()` | Teachers can assign a point value to student's work|

* private utility methods exist, have not been implemented yet - will be handled internally so shouldn't be a concern

## TeacherRunner Class

##### Fields

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
| loodIndividualStudent | `private void` | `(Scanner reader, Student currentStudent)` |  Loop for viewing/editing all posts of 1 student |
| loopCourseOverride | `protected boolean` | `(Scanner reader, String input)` |  For menu options exclusive to teacher |
| menuCreateDiscussion | `private void` | `(Scanner reader)` |  The menu for creating new discussion forum |
| menuEditCourse | `private void` | `(Scanner reader)` |  The menu for editing course topic  |
| menuEditDiscussion | `private void` | `(Scanner reader)` |  The menu for editing discussion topic |
| loopDiscussionOverride | `protected boolean` | `(Scanner reader, String input)` |  Deleting discussion forum menu option for teacher |
| loopViewVoteboard | `private void` | `(Scanner reader)` |  Shows the dashboard by the number of votes |
| parse2WordInputOverride | `protected boolean` | `(Post targetPost, Scanner reader, String inputWord1)` |  Parses the word given as input |
| parse2WordInputStudent | `protected boolean` | `(Scanner reader, String input)` |  Modified version of UserRunner's parse2WordInput for the viewIndividualStudent menu |
| menuGradePost | `private boolean` | `(Post targetPost, Scanner reader)` | menu for grading a post |


## Student Class (extends `User`) (Serializable)

##### Fields

| Field      | Signature   | Description | Getter/Setter |
| ---------- | ----------- | ----------- | ------------- |
| posts | ` private List<Integer>` | ID of every post the student has made | G |

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
| isAdmins | `public boolean` | `()` | returns true |
| makePostReply | `public Post` | `(Post parentPost, String newContent, Discussion parentDiscussion)` | Reply to a reply to a discussion form |
| makeDiscussionReply | `public Post` | `(String newContent, Discussion parentDiscussion)` | Reply directly to a discussion form |
| getPostsString | `public String` | `()` | Gets the Posts String |
| getPostVote | `public int` | `(Post targetPost)` | returns whether votedPost has been upvoted or downvoted by user |
| upvotePost | `public boolean` | `(Post targetPost)` | Determines whether upvote goes through for post |
| downvotePost | `public boolean` | `(Post targetPost)` | Get downvote for post |

- get / store all student posts
- view scores

## StudentRunner Class (extends `UserRunner`)

##### Fields

| Field      | Signature   | Description | Getter/Setter |
| ---------- | ----------- | ----------- | ------------- |
| student | ` private Student` | Student who's logged in | |

#### Constructors

| Signature   | Parameters  | Description |
| ----------- | ----------- | ----------- |
| `public` | `(Student student)` | Creates new StudentRunner |
098
#### Methods

| Method      | Signature   | Parameters | Description |
| ----------- | ----------- | ---------- | ----------- |
| loopDiscussionOverride | `protected boolean` | `(Scanner reader, String input)` |  Replying to discussion forum menu option for student |
| menuViewGrades | `protected boolean` | `(Scanner reader)` |  Menu for viewing all grades received for all posts in the forum (Student exclusive) |
| menuDiscussionReply | `private boolean` | `(Scanner reader)` |  Menu for replying to a discussion form |
| parse2WordInputOverride | `protected boolean` | `(Scanner reader, String input)` |  Parses the word given as input |

## Course Class (Serializable)

##### Fields

| Field      | Signature   | Description |
| ---------- | ----------- | ----------- |
| COURSE_LIST    | `public static List<Course>` | Lists all courses |
| discussions | `private List<Discussions` | List of all `Discussion`s of `Course`| G |
| id | `private int` | Id is same as index in list | G |
| topic | `private String` | Course name, can only be changed by a `Teacher` | G/S |
| creator | `private int` | Keeps track of the UID of who created this `Course` | G |

#### Constructors

| Signature   | Parameters  | Description |
| ----------- | ----------- | ----------- |
| `private` | `(String topic, int creator)` | Auto sets id to next id in list |

#### Methods

| Method      | Signature   | Parameters | Description |
| ----------- | ----------- | ---------- | ----------- |
| getCoursesString | `public static g` | `()`  |  Returns list of all courses with id + course name (see Console Example) |
| getDiscussionsString | `public String ` | `()` | Returns list of all discussions with id + discussion topic (see Console Example) |
| createCourse | `public static Course ` | `()` | Creates and returns a new course  object if the user has permission to |

### Discussion Class (Serializable)

##### Fields

| Field      | Signature   | Description | Getter/Setter |
| ---------- | ----------- | ----------- | ------------- |
| DISCUSSION_LIST   | `public static List<Discussion>` | Lists all `Discussion`s |  |
| id | `private int` | Id is same as index in list | G |
| topic | `private String` | Discussion Topic | G/S |
| posts | `private List<Post>` | List of all posts related to this `Discussion` | G |
| course | `private Course` | Parent Course | G |
| timestamp | `private Date` | Keeps track of when the `Discussion` was created | G/S |
| creator | `private int` | Keeps track of the UID of who created this `Discussion` | G |

#### Constructors

| Signature   | Parameters  | Description |
| ----------- | ----------- | ----------- |
| `private` | `(Course course, String topic, int creator)` | Auto sets id to next id in list |

#### Methods

| Method      | Signature   | Parameters | Description |
| ----------- | ----------- | ---------- | ----------- |
| createDiscussion | `public static Discussion` | `(Course course, String topic, User user)` |  Creates and returns a new Discussion object if the user has permission to |
| deleteDiscussion | `public static Discussion` | `(int id, User user)` |  Deletes the discussion with the given id |
| getPostsString | `public String` | `(Course course, String topic, User user)` |  Returns a string with all the posts listed |
| sortByUpvotes | `public List<Post>` | `(Course course, String topic, User user)` |  Sorts the list by upvotes |

- Get most popular posts

### Post Class (Serializable)

Note: posts can be under both discussions and other posts

##### Fields

| Field      | Signature   | Description | Getter/Setter |
| ---------- | ----------- | ----------- | ------------- |
| POST_LIST  | `public static List<Post>` | Lists all `Post`s |  |
| id | `private int` | `id` is same as index in list | G |
| posts | `private List<Post>` | List of all posts related to this `Post` | G |
| timestamp | `private Date` | Keeps track of when the `Post` was created | G/S |
| creatorID | `private int` | Keeps track of the UID of who created this `Post` | G |
| content | `private String` | Keeps track of the contents in this `Post`, can be changed only by `creator` (EC or a `Teacher`) | G/S |
| grade | `private int` | Keeps track of the grade of this post `Post`, Only shown if `User` is creator of `Post` or is a `Teacher` | G |
| maxGrade | `private int` | Keeps track of the max grade of this `Post`, Only shown if `User` is creator of `Post` or is a `Teacher`  | G |
| upvotes | `private int` | Keeps track of all `Student` UIDs who have up-voted this post | G |
| downvotes | `private int` | OPTIONAL BUT COOL? | G |
| parent | `private Post`  | Post that this Post is a reply to; null if this Post is not a reply   | G  |
| discussion  | `private Discussion` | Discussion that this Post is a part of  | G |

#### Constructors

| Signature   | Parameters  | Description |
| ----------- | ----------- | ----------- |
| `private` | `(String content, Discussion discussion, Post parent, int creatorId)` | Constructor for Post |

#### Methods

| Method      | Signature   | Parameters | Description |
| ----------- | ----------- | ---------- | ----------- |
| createPost | `public static`   | `(String content, Discussion discussion, User user)` | Returns a new post if the user has permission to post |
| createPost | `public static`   | `(String content, Discussion discussion, Post parent, User user)` | Returns a new post if the user has permission to post, Post is a reply to another post |
| editPost | `public boolean`   | `(String newContent, User user)` | Allows editing of the post if the user has permission to edit or if the user is the creatorId of the post |
| toString | `public String`   | `()` | The toString for Post |
| grade | `public boolean`   | `(User user, int grade)` | Grades the post if the user has permission |
| getPostsString | `public String`   | `()` | Return the list of posts under this post as a string |
| getVotes | `public int`   | `(User user, int oldVote)` | Returns the number of votes after calculation |
| getControversy | `public int`   | `(User user, int oldVote)` | Returns the total number of votes |
| upvote | `public boolean`   | `(User user, int oldVote)` | Decides if the user can vote, if so, then upvotes |
| downvote | `public boolean`   | `(User user, int oldVote)` | Decides if the user can vote, if so, then downvotes |
| removeVote | `public boolean`   | `(int oldVote)` | removes the vote |

### Display Class

#### Methods

| Method      | Signature   | Parameters | Description |
| ----------- | ----------- | ---------- | ----------- |
| displayStart | `public static void` | `()`  |  Displays output for main login/create account loop |
| displayWelcome | `public static void` | `(User user)`  |  Displays output for User main loop (viewing all courses after login) |
| displayBadInput | `public static void` | `()`  |  Displays output for invalid input |
| displayExit | `public static void` | `()`  |  Displays output for program exit |
| displayLogout | `public static void` | `()`  |  Displays output for logging out |
| displayCreateCourse | `public static void` | `()`  |  Displays output for creating a course (accessed from main menu) |
| displayCourse | `public static void` | `(Course currentCourse, User user)`  |  Displays output for course loop (viewing all discussions in 1 course) |
| displayCreateDiscussion | `public static void` | `(Course currentCourse, User user)`  |  Displays output for creating a discussion |
| displayEditCourse | `public static void` | `()`  |  Displays output for editing a course topic |
| displayDiscussion | `public static void` | `(Discussion currentDiscussion, User user)`  |  Displays output for discussion loop (viewing all posts in 1 discussion) |
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
| displayPostsVoteboard | `private static void` | `(List<Post> posts))` |  Displays lists of posts |
| displayViewVoteboard | `public static void` | `(Discussion currentDiscussion, String currentSort, User user)` | Displays voteboard, which shows all posts in a forum ranked by votes with sorting options. |
| displayViewGrades | `public static void` | `(Discussion currentDiscussion, Student currentStudent)` | Displays grades for all of 1 student's posts in 1 forum |
| displayPostsGrades | `public static void` | `(List<Integer> posts, User user)` | Given a list of posts, prints them all in order with 0 depth AND INCLUDES ONLY GRADE INFO for displayViewGrades |

### Display Class

#### Methods

| Method      | Signature   | Parameters | Description |
| ----------- | ----------- | ---------- | ----------- |
| testLoginLogout | `public void` | `()`  | Test logging in and logging out |
| testMainInvalidInput | `public void` | `()`  | Test that main loop handles invalid input |
| testMainCreateAccountLogin | `public void` | `()`  | Test account creation and logging in with new account |
| testMainInvalidUsername | `public void` | `()`  | Test account creation with username that's been used |



## Console Example

* *To write a new example, put 3 backticks at the start and end to make it a code block - otherwise Markdown formatting
  makes it weird*

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
