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
| password    | `private String`  | NaN | |
| name        | `private String`  | -NaN | G/S |
| id | `private int` | Id is same as index in list | G |

#### Constructors
| Signature   | Parameters  | Description |
| ----------- | ----------- | ----------- |
| `public` | `(String username, String password, String name)` |  |
##### Methods
| Method      | Signature   | Parameters | Description |
| ----------- | ----------- | ----------- | ----------- |
| connect        | `public static void` | `(Scanner in)` |  Loop Handler for login |
| loop           | `public abstract void` | `(Scanner in)` | Primary Loop Handler |
| FIXME | FIXME | FIXME | FIXME |
| createAccount  | `public static void` | `(Scanner in)` |  Creates Account and appends to account list, does not log user in |
| modifyAccount  | `public void` | `(Scanner in)` |  Modifies user account details, can only be done to self, username should be unique |
| deleteAccount  | `public void` | `(Scanner in)` |  Deletes the user account and sets its index in list to null |
| canVote  | `public abstract boolean` | `()` |  Whether the user has permission to vote |
| canGrade  | `public abstract boolean` | `()` |  Whether the user has permission to grade |
| canPost  | `public abstract boolean` | `()` |  Whether the user has permission to make or reply to posts, and edit their own posts |
| canCreateCourse |`public abstract boolean` | `()` | Whether the user can create courses |
| canModifyCourse |`public abstract boolean` | `()` | Whether the user can modify courses |
| canModifyDiscussion | `public abstract boolean` | `()` |  Whether the user has permission to modify or delete Discussions |
| canModifyPost | `public abstract boolean` | `()` | (EC) Whether the user has permission to modify or delete Posts made by others |
| isAdmin | `public abstract boolean` | `()` | For debugging purposes, overrides all permissions |


## Teacher Class (extends `User`)

#### Constructors
| Signature   | Parameters  | Description |
| ----------- | ----------- | ----------- |
| `public` | `(String username, String password, String name)` | Auto sets id to next id in list |

#### Methods
| Method      | Signature   | Parameters | Description |
| ----------- | ----------- | ----------- | ----------- |
| loop | `public void` | `(Scanner in)` |  Primary Loop Handler |
## Student Class (extends `User`)

#### Constructors
| Signature   | Parameters  | Description |
| ----------- | ----------- | ----------- |
| `public` | `(String username, String password, String name)` | Auto sets id to next id in list |

#### Methods
| Method      | Signature   | Parameters | Description |
| ----------- | ----------- | ----------- | ----------- |
| loop | `public void` | `(Scanner in)` |  Primary Loop Handler |
|

- get / store all student posts
- view scores

## Course Class (Serializable)
##### Fields
| Field      | Signature   | Description |
| ----------- | ----------- | ----------- |
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

### Discussion Class (Serializable)
##### Fields
| Field      | Signature   | Description | Getter/Setter |
| ----------- | ----------- | ----------- | ----------- |
| ALL_DISCUSSIONS   | `public static List<Discussion>` | Lists all `Discussion`s |  |
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
| `public` | `(String topic, User creator)` | Auto sets id to next id in list |

#### Methods
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
| content | `private String` | Keeps track of the contents in this `Post`, can be changed only by `creator` (EC or a `Teacher`) | G/S |
| grade | `private int` | Keeps track of the grade of this post `Post`, Only shown if `User` is creator of `Post` or is a `Teacher` | G |
| maxGrade | `private int` | Keeps track of the max grade of this `Post`, Only shown if `User` is creator of `Post` or is a `Teacher`  | G |
| upvotes | `private List<Integer>` | Keeps track of all `Student` UIDs who have up-voted this post | G |
| downvotes | `private List<Integer>` | OPTIONAL BUT COOL? | G |

#### Constructors
| Signature   | Parameters  | Description |
| ----------- | ----------- | ----------- |
| `public` | `(String reply, User creator)` | Auto sets id to next id in list |

#### Methods

## Console Example

* If you're viewing this in plaintext, the <br> is for markdown formatting (line breaks) and shouldn't appear in anything.

Suggestions:
* Users need to be able to "create, edit, and delete" their own accounts, so those should be menu options.
* We should also have a 'back' option so people can go back through menus.
* We should also have some part of the menu that tells users what commands they can type when viewing a discussion forum - eg. beneath [Discussion Topic], a line saying `Type "commands" to view possible commands.`
* Clarify the purpose of 'exit'?

___
### Main Loop:

Welcome to [Name]! <br>
Please type an option: <br>
login <br>
create account <br>
exit

Example input:<br>
[login]
___
### Student Loop:

Welcome [Name]!  <br>
Please choose a course to view:  <br>
[1] [Course Name] <br>
[2] [Course Name] <br>
[3] [Course Name] <br>
[4] [Course Name] <br>
exit

Example input: <br>
[course 3] <br>
[exit]

Welcome to [Course Name]! <br>
Please choose a Discussion to view: <br>
[1] [Discussion Name] <br>
[22] [Discussion Name] <br>
[25] [Discussion Name] <br>
exit

Example input: <br>
[discussion 22] <br>
[exit]

[Discussion Topic] <br>
[12] [Post Content] <br>
[33] [Post Content] <br>
[98] [Post Content] <br>
[113] [Post Content] <br>
exit

Example input: <br>
[reply 33] <br>
[upvote 98] <br>
[edit 12] <br>
[exit]

___

### Teacher Loop:

* The menus for the teacher loop include a "back" option.

##### Example 1

Welcome [Name]! <br>
Please choose a course to view: <br>
[1] [Course Name] <br>
[2] [Course Name] <br>
[3] [Course Name] <br>
[4] [Course Name] <br>
Or, please type an option: <br>
edit account <br>
delete account <br>
create course <br>
view student <br>
exit

Example input: <br>
[course 3] <br>
[exit]

Welcome to [Course Name]! <br>
Please choose a Discussion to view: <br>
[1] [Discussion Name] <br>
[22] [Discussion Name] <br>
[25] [Discussion Name] <br>
Or, please type an option: <br>
back <br>
create forum <br>
delete forum <br>
exit

Example input: <br>
[discussion 22] <br>
[exit]

[Discussion Topic] <br>
[12] [Post Content] <br>
[33] [Post Content] <br>
[98] [Post Content] <br>
[113] [Post Content] <br>
exit

Example input: <br>
[reply 33] <br>
[edit 12] <br>
[delete 12] <br>
[grade 98] <br>
[exit]

##### Example 2

Welcome [Name]! <br>
Please choose a course to view: <br>
[1] [Course Name] <br>
[2] [Course Name] <br>
[3] [Course Name] <br>
[4] [Course Name] <br>
Or, please type an option: <br>
back <br>
create course <br>
delete course <br>
view student <br>
exit

Example input: <br>
[view student] <br>
[exit]

View Student: <br>
This shows all of a student's posts and lets you grade them. <br>
Enter the name or ID of the student to view: <br>
Or, please type an option: <br>
back <br>
exit

Example input: <br>
[student id] <br>
[exit]

[Student Name]'s Posts <br>
[23] [Post Content] <br>
[35] [Post Content] <br>
[42] [Post Content] <br>
exit

Example input: <br>
[edit 23] <br>
[delete 42] <br>
[grade 35] <br>
exit
