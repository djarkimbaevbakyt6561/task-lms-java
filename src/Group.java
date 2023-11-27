import java.util.Arrays;
import java.util.Scanner;

public class Group {
    private static final Scanner scanner = new Scanner(System.in);
    String groupName;
    String description;
    Lesson[] lessons = new Lesson[0];
    Student[] students = new Student[0];
    public void addStudent(Student student) {
        Student[] newArray = Arrays.copyOf(students, students.length + 1);
        newArray[newArray.length - 1] = student;
        students = newArray;
    }
    public void addLesson(Lesson lesson) {
        Lesson[] newArray = Arrays.copyOf(lessons, lessons.length + 1);
        newArray[newArray.length - 1] = lesson;
        lessons = newArray;
    }
    private static String inputString(String prompt) {
        System.out.print(prompt);
        String returnText;
        while (true) {
            String text = scanner.nextLine();

            if (text.isEmpty()) {
                System.out.println("Значение не должно быть пустым!");
            } else {
                returnText = text;
                break;
            }
        }
        return returnText;
    }

    public static Group createGroup(Management management) {
        Group newGroup = new Group();
        boolean found = false;
        while (true){
            String groupName = inputString("Введите название группы: ");
            for (Group group : management.groups) {
                if (group.groupName.equals(groupName)) {
                    found = true;
                    break;
                }
            }
            if(!found){
                newGroup.groupName = groupName;
                newGroup.description = inputString("Введите описание группы: ");
                break;
            } else {
                System.out.println("Группа с таким названием уже существует!");
            }
        }
        return newGroup;

    }

    @Override
    public String toString() {
        return "Группа " +
                 groupName +
                ", Oписание: " + description +
                ", Уроки: " + Arrays.toString(lessons) +
                ", Студенты: " + Arrays.toString(students);
    }
}
