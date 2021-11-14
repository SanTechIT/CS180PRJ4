# TODO

Missing features
* serialization/permanence
* test cases
* the report
* comment the code
* improve post formatting - grade should only be visible to teacher/poster
* ungraded posts have a grade of 0 - change that to n/a? will need to keep track of whether a post has been graded
* Teacher shouldn't be able to reply to a student's posts while viewing their student dashboard, remove that feature.
* Teachers should be able to edit courses

Problems
- MAJOR: Validate inputs for class/discussion/post Ids in reply/edit/delete actions
- Ask if user wants to reply/edit/delete posts of another grouping?
- MAJOR: Teacher can't delete discussion forums
- MAJOR: You can log into any account with any password. We don't have authentication.
- MAJOR: Posts don't show up correctly! Only posts that are a reply to a post with ID 0 show up, everything else doesn't show.
- non-major: teacher can't logout from ViewIndividualStudent menu, only go "back" (effect of loop design)
- MAJOR: in ViewIndividualStudent, each of the student's posts shows up twice
- MAJOR: students have multiple IDs? does each student exist twice in USER_LIST?
    - eg. in Tests.java, Alice has both ID 2 and ID 3
- MAJOR: Deleting a post from the ViewIndividualStudent menu causes the program to crash, don't know why (example below)
- MAJOR: replying to a nonexistent post crashes the program
- Students can upvote/downvote posts an infinite number of times

```
Commands: back, reply [num], edit [num], delete [num], grade [num], logout
Replace [num] with the number of the post you want to interact with!
s | s (ID 4) posted
at time TIMESTAMP NOT IMPLEMENTED
(votes: +0 | -0)
sadlkfjdlaskf
s | s (ID 4) posted
at time TIMESTAMP NOT IMPLEMENTED
(votes: +0 | -0)
sdalkfjlkdasf

> delete 0     

Delete post 0:
Deleted posts can't be recovered. Are you sure?
Type yes to confirm.
> yes
Post 0has been deleted.

s's Posts:

Commands: back, reply [num], edit [num], delete [num], grade [num], logout
Replace [num] with the number of the post you want to interact with!
Exception in thread "main" java.lang.NullPointerException
	at Student.getPostsString(Student.java:133)
	at Display.displayIndividualStudent(Display.java:282)
	at TeacherRunner.loopIndividualStudent(TeacherRunner.java:127)
	at TeacherRunner.loopViewStudent(TeacherRunner.java:110)
	at TeacherRunner.loopMainOverride(TeacherRunner.java:55)
	at UserRunner.loop(UserRunner.java:119)
	at Teacher.loop(Teacher.java:29)
	at User.connect(User.java:82)
	at Tests.main(Tests.java:45)
```

# [Name] Design specifications

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
| main        | public static void | String[] args | Main Method |

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
| connect        | `public static void` | `(Scanner in)` |  Loop Handler for login |
| loop           | `public abstract void` | `(Scanner in)` | Primary Loop Handler |
| FIXME | FIXME | FIXME | FIXME |
| createAccount  | `public static void` | `(Scanner in)` |  Creates Account and appends to account list, does not log user in |
| modifyUsername  | `public void` | `(Scanner in)` |  Modifies username, can only be done to self, username should be unique |
| modifyName | `public void` | `(Scanner in)`  | Modifies name, can only be done to self   |
| modifyPassword  | `public void` | `(Scanner in)` | Modifies password, can only be done to self   |
| deleteAccount  | `public void` | `(Scanner in)` |  Deletes the user account and sets its index in list to null |
| canVote  | `public abstract boolean` | `()` |  Whether the user has permission to vote |
| canGrade  | `public abstract boolean` | `()` |  Whether the u ser has permission to grade |
| canPost  | `public abstract boolean` | `()` |  Whether the user has permission to make or reply to posts, and edit their own posts |
| canCreateCourse |`public abstract boolean` | `()` | Whether the user can create courses |
| canModifyCourse |`public abstract boolean` | `()` | Whether the user can modify courses |
| canModifyDiscussion | `public abstract boolean` | `()` |  Whether the user has permission to modify or delete Discussions |
| canModifyPost | `public abstract boolean` | `()` | (EC) Whether the user has permission to modify or delete Posts made by others |

## Teacher Class (extends `User`)

#### Constructors

| Signature   | Parameters  | Description |
| ----------- | ----------- | ----------- |
| `public` | `(String username, String password, String name)` | Auto sets id to next id in list |

#### Methods

| Method      | Signature   | Parameters | Description |
| ----------- | ----------- | ---------- | ----------- |
| loop | `public void` | `(Scanner in)` |  Primary Loop Handler |

* methods inherited from User - the canVote, etc. have been implemented
* private utility methods exist, have not been implemented yet - will be handled internally so shouldn't be a concern

## TeacherRunner Class 

#### Constructors

| Signature   | Parameters  | Description |
| ----------- | ----------- | ----------- |
| `public` | `(Teacher teacher)` | Auto sets id to next id in list |

#### Methods

| Method      | Signature   | Parameters | Description |
| ----------- | ----------- | ---------- | ----------- |
| loopMainOverride | `protected boolean` | `(Scanner reader, String input)` |  Handles the menu options just for teacher |
| menuCreateCourse | `private void` | `(Scanner reader)` |  The menu for creating new course |
| loopViewStudent | `private void` | `(Scanner reader)` |  Shows the dashabord and the data with the name and vote count |
| loodIndividualStudent | `private void` | `(Scanner reader, Student currentStudent)` |  Loop for viewing/editing all posts of 1 student |
| loopCourseOverride | `protected boolean` | `(Scanner reader, String input)` |  For menu options exclusive to teacher |
| menuCreateDiscussion | `private void` | `(Scanner reader)` |  The menu for creating new discussion forum |
| loopDioscussionOverrie | `protected boolean` | `(Scanner reader, String input)` |  Deleting discussion forum menu option for teacher |
| parse2WordInputOverride | `protected boolean` | `(Scanner reader, String input)` |  Parses the word given as input |
| menuGradePost | `private boolean` | `(Scanner reader, String input)` | menu for grading a post |


## Student Class (extends `User`)

#### Constructors

| Signature   | Parameters  | Description |
| ----------- | ----------- | ----------- |
| `public` | `(String username, String password, String name)` | Auto sets id to next id in list |

#### Methods

| Method      | Signature   | Parameters | Description |
| ----------- | ----------- | ---------- | ----------- |
| loop | `public void` | `(Scanner in)` |  Primary Loop Handler |

- get / store all student posts
- view scores

## Course Class (Serializable)

##### Fields

| Field      | Signature   | Description |
| ---------- | ----------- | ----------- |
| COURSE_LIST    | `public static List<Course>` | Lists all courses |
| discussions | `private List<Discussions` | List of all `Discussion`s of `Course`|
| id | `private int` | Id is same as index in list | G/S |
| topic | `private String` | Course name, can only be changed by a `Teacher` | G/S |
| creator | `private int` | Keeps track of the UID of who created this `Course` | G |

#### Constructors

| Signature   | Parameters  | Description |
| ----------- | ----------- | ----------- |
| `private` | `(String topic, User creator)` | Auto sets id to next id in list |

#### Methods

| Signature   | Parameters  | Description |
| ----------- | ----------- | ----------- |
| `public static Course` | `(String topic, User creator)` | Creates a new course, appends to list, and returns |
| `public static String getCoursesString` | `()`  |  Returns list of all courses with id + course name (see Console Example) |
| `public static Course searchCourses` | `(int id)` | Search COURSE_LIST for course with that id, return null if not found |
| `public String getDiscussionsString` | `()` | Returns list of all discussions with id + discussion topic (see Console Example) |
| `public String searchDiscussions`  | `(int id)`  | Search `discussions` for discussion with that id, return null if not found   |

### Discussion Class (Serializable)

##### Fields

| Field      | Signature   | Description | Getter/Setter |
| ---------- | ----------- | ----------- | ------------- |
| DISCUSSION_LIST   | `public static List<Discussion>` | Lists all `Discussion`s |  |
| discussions | `private List<Discussion>` | | G/S |
| id | `private int` | Id is same as index in list | G |
| topic | `private String` | Discussion Topic | G/S |
| posts | `private List<Post>` | List of all posts related to this `Discussion` | G |
| course | `private Course` | Parent Course | G |
| timestamp | `private Date` | Keeps track of when the `Discussion` was created | G/S |
| creator | `private int` | Keeps track of the UID of who created this `Discussion` | G |

#### Constructors

| Signature   | Parameters  | Description |
| ----------- | ----------- | ----------- |
| `public` | `(String topic, Course course, User creator)` | Auto sets id to next id in list |

#### Methods

| Signature   | Parameters | Description |
| ----------- | ---------- | ----------- |
| `public String getPostsString` | `()` |  Returns chronological list of all posts with id + post topic (see Console Example) |
| `public Post searchPosts` | `(int id)` | Search `posts` for post with that id, return null if not found  |
| `public void addPost`   | `(User poster, String newContent)` | Calls Post constructor to add new post to `posts` |
| `public void addPost`   | `(User poster, Post parentPost, String newContent)` | Calls Post constructor to add new post (*
which is a reply to an existing post*) to `posts` |
| `public void delete` | `(User deleter)` | Deletes this forum and all posts within it |

- Get most popular posts

### Post Class (Serializable)

Note: posts can be under both discussions and other posts

##### Fields

| Field      | Signature   | Description | Getter/Setter |
| ---------- | ----------- | ----------- | ------------- |
| ALL_POSTS  | `public static List<Post>` | Lists all `Post`s |  |
| id | `private int` | `id` is same as index in list | G |
| posts | `private List<Post>` | List of all posts related to this `Post` | G |
| timestamp | `private Date` | Keeps track of when the `Post` was created | G/S |
| creator | `private int` | Keeps track of the UID of who created this `Post` | G |
| content | `private String` | Keeps track of the contents in this `Post`, can be changed only by `creator` (EC or a `Teacher`) | G/S |
| grade | `private int` | Keeps track of the grade of this post `Post`, Only shown if `User` is creator of `Post` or is a `Teacher` | G |
| maxGrade | `private int` | Keeps track of the max grade of this `Post`, Only shown if `User` is creator of `Post` or is a `Teacher`  | G |
| upvotes | `private List<Integer>` | Keeps track of all `Student` UIDs who have up-voted this post | G |
| downvotes | `private List<Integer>` | OPTIONAL BUT COOL? | G |
| parentPost | `private Post`  | Post that this Post is a reply to; null if this Post is not a reply   | G  |
| parentDiscussion  | `private Discussion` | Discussion that this Post is a part of  |   |

#### Constructors

| Parameters  | Description |
| ----------- | ----------- |
| `(User creator, String content)` | Auto sets id to next id in list |
| `(Post parentPost, User creator, String content)` | New post is a reply to parentPost  |

#### Methods

| Signature   | Parameters  | Description |
| ----------- | ----------- | ----------- |
| `public void edit`   | `(User editor, String newContent)` | Replaces content of Post with newContent  |
| `public void delete` | `(User deleter)` | Deletes post |
| `public void grade` | `(User grader, int grade)` | Assigns grade |
| `public void upvote`  | `(User upvoter)`  | Upvotes post |
| `public void downvote`   |  `(User downvoter)`  | Downvotes post  |

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
