import java.util.Arrays;

public class Management {
    Group[] groups = new Group[0];
    User[] users = new User[]{new User("Bolot", "Sultanich", "admin@gmail.com", "admin123", "0707109090", "male", Roles.ADMIN)};
    Student[] allStudents = new Student[0];
    Lesson[] allLessons = new Lesson[0];
    Instructor[] allInstructors = new Instructor[0];
    public void addUser(User registerUser) {
        User[] newArray = Arrays.copyOf(users, users.length + 1);
        newArray[newArray.length - 1] = registerUser;
        users = newArray;
    }
    public void addLesson(Lesson lesson) {
        Lesson[] newArray = Arrays.copyOf(allLessons, allLessons.length + 1);
        newArray[newArray.length - 1] = lesson;
        allLessons = newArray;
    }
    public void addStudentToAllStudents(Student student) {
        Student[] newArray = Arrays.copyOf(allStudents, allStudents.length + 1);
        newArray[newArray.length - 1] = student;
        allStudents = newArray;
    }
    public void addInstructor(Instructor instructor) {
        Instructor[] newArray = Arrays.copyOf(allInstructors, allInstructors.length + 1);
        newArray[newArray.length - 1] = instructor;
        allInstructors = newArray;
    }
    public void addGroup(Group group) {
        Group[] newArray = Arrays.copyOf(groups, groups.length + 1);
        newArray[newArray.length - 1] = group;
        groups = newArray;
    }
    public void deleteGroup(String groupName) {
        Group[] newGroups = new Group[10000];
        int newIndex = 0;

        for (Group group : groups) {
            if (group != null && !(group.groupName.equals(groupName))) {
                newGroups[newIndex++] = group;
            }
        }

        groups = Arrays.copyOf(newGroups, groups.length - 1);
    }

    public void deleteInstructor(String email) {
        Instructor[] instructors = new Instructor[10000];
        int newIndex = 0;

        for (Instructor instructor1 : instructors) {
            if (instructor1 != null && !(instructor1.email.equals(email))) {
                instructors[newIndex++] = instructor1;
            }
        }

        allInstructors = Arrays.copyOf(instructors, instructors.length - 1);
    }
    public void deleteUser(String email) {
        User[] newUsers = new User[10000];
        int newIndex = 0;

        for (User user : users) {
            if (user != null && !(user.email.equals(email))) {
                newUsers[newIndex++] = user;
            }
        }

        users = Arrays.copyOf(newUsers, users.length - 1);
    }

    public void deleteStudent(String email) {
        Student[] newStudents = new Student[10000];
        int newIndex = 0;

        for (Student student : newStudents) {
            if (student != null && !(student.email.equals(email))) {
                newStudents[newIndex++] = student;
            }
        }

        allStudents = Arrays.copyOf(newStudents, allStudents.length - 1);
    }
    public void deleteLesson(String lessonName) {
        Lesson[] newLessons = new Lesson[10000];
        int newIndex = 0;

        for (Lesson lesson : newLessons) {
            if (lesson != null && !(lesson.lessonName.equals(lessonName))) {
                newLessons[newIndex++] = lesson;
            }
        }

        allLessons = Arrays.copyOf(newLessons, allLessons.length - 1);
    }
}
