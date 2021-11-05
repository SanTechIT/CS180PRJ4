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
| ----------- | ----------- | ----------- | ----------- |
| main        | public static void | String[] args | Main Method |
## User Class (abstract) (Serializable)
##### Fields
| Field      | Signature   | Description | Getter/Setter |
| ----------- | ----------- | ----------- | ----------- |
| USER_LIST   | `public static List<User>` | Lists all users |  |
| username    | `private String`  | None |  |
| password    | `private String`  | NaN |  |
| name        | `private String`  | -NaN | G |
| id | `private int` | Id is same as index in list | G/S |

#### Constructors
| Signature   | Parameters  | Description |
| ----------- | ----------- | ----------- |
| `public` | `(String username, String password, String name)` |  |
##### Methods
| Method      | Signature   | Parameters | Description |
| ----------- | ----------- | ----------- | ----------- |
| connect        | `public static void` | `(Scanner in)` |  Primary Loop Handler, if logged in, run loops defined in implemented subclasses |
| createAccount  | `public static void` | `(Scanner in)` |  Creates Account and appends to account list, does not log user in |
| modifyAccount  | `public static void` | `(Scanner in)` |  Modifies user account details, username should be unique |
| deleteAccount  | `public static void` | `(Scanner in)` |  Deletes the user account and sets its index in list to null |
| canVote  | `public static boolean` | `()` |  Whether the user has permission to vote |
| canGrade  | `public static boolean` | `()` |  Whether the user has permission to grade |
| canPost  | `public static boolean` | `()` |  Whether the user has permission to make or reply to posts, and edit their own posts |
| canModifyDiscussion | `public static boolean` | `()` |  Whether the user has permission to modify or delete Discussions |
| canModifyPost | `public static boolean` | `()` | (EC) Whether the user has permission to modify or delete Posts made by others |


## Teacher Class (extends teacher)
## Student Class (extends student)
- get / store all student posts
- view scores
## Course Class (Serializable)
##### Fields
| Field      | Signature   | Description |
| ----------- | ----------- | ----------- |
| ALL_COURSES    | `public static List<Course>` | Lists all courses |
| id | `private int` | Id is same as index in list | G/S |
| creator | `private int` | Keeps track of the UID of who created this `Course` | G |


### Course Discussion Class (Serializable)
##### Fields
| Field      | Signature   | Description | Getter/Setter |
| ----------- | ----------- | ----------- | ----------- |
| ALL_DISCUSSIONS   | `public static List<Discussion>` | Lists all `Discussion`s |  |
| discussions | `private List<Discussion>` | | G/S |
| id | `private int` | Id is same as index in list | G |
| topic | `private String` | Discussion Topic | G/S |
| posts | `private List<Post>` | List of all posts related to this `Discussion` | G |
| timestamp | `private Date` | Keeps track of when the `Discussion` was created | G/S |
| creator | `private int` | Keeps track of the UID of who created this `Discussion` | G |

- Get most popular posts

### Post Class (Serializable)
Note: posts can be under both discussions and other posts
##### Fields
| Field      | Signature   | Description | Getter/Setter |
| ----------- | ----------- | ----------- | ----------- |
| ALL_POSTS  | `public static List<Discussion>` | Lists all `Post`s |  |
| id | `private int` | `id` is same as index in list | G |
| posts | `private List<Post>` | List of all posts related to this `Post` | G |
| timestamp | `private Date` | Keeps track of when the `Post` was created | G/S |
| creator | `private int` | Keeps track of the UID of who created this `Post` | G |
| grade | `private int` | Keeps track of the grade of this post `Post`, Only shown if `User` is creator of `Post` or is a `Teacher` | G |
| maxGrade | `private int` | Keeps track of the max grade of this `Post`, Only shown if `User` is creator of `Post` or is a `Teacher`  | G |
| upvotes | `private List<Integer>` | Keeps track of all `Student` UIDs who have up-voted this post | G |
| downvotes | `private List<Integer>` | OPTIONAL BUT COOL? | G |

## Console Example
___
Main Loop:

Welcome to [Name]! <br>
Please type an option: <br>
login <br>
create account <br>
exit 

Example input:<br>
[login]
___
Student Loop:

Welcome [Name]!  <br>
Please choose a course to view:  <br>
[1] [Course Name] <br>
[2] [Course Name] <br>
[3] [Course Name] <br>
[4] [Course Name]

Example input:<br>
[course 3]
