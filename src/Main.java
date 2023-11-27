import exceptions.EmailException;
import exceptions.ValidationEmailException;
import exceptions.VoidException;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);

    private static String inputString(String prompt) {
        System.out.print(prompt);
        String returnText;
        while (true) {
            try {
                String text = scanner.nextLine();

                if (text.isEmpty()) {
                    throw new VoidException();
                } else {
                    returnText = text;
                    break;
                }
            } catch (VoidException e) {
                System.out.println("Значение не должно быть пустым!");
            }

        }
        return returnText;
    }

    public static void main(String[] args) {
        Management management = new Management();
        User currentUser;
        String[] printTexts = {"Add new group",
                "Add new student to group",
                "Add new lesson to group",
                "Get group by name",
                "Get all groups",
                "Get student by email",
                "Get all students by group name",
                "Get lesson by name",
                "Get all student's lesson",
                "Get all lessons by group name",
                "Update student by email",
                "Update group by name",
                "Delete lesson by name",
                "Delete group by name",
                "Delete student by email",
                "Logout"};
        boolean exit = false;
        boolean exitLogin = false;
        boolean isLoggedIn = false;
        while (!exit) {
            while (!exitLogin) {
                System.out.println("""
                        1.Войти
                        2.Забыли пароль?
                        3.Зарегистрироваться
                        4.Выйти""");
                try {
                    int num = scanner.nextInt();
                    switch (num) {
                        case 1 -> {
                            currentUser = User.login(management.users);
                            if (currentUser != null) {
                                isLoggedIn = true;
                                exitLogin = true;
                            }
                        }
                        case 2 -> {
                            User user = User.forgotYourPassword(management);
                            if (user == null) {
                                System.out.println("Такого email нету!");
                            } else {
                                for (User user1 : management.users) {
                                    if (user.email.equalsIgnoreCase(user1.email)) {
                                        user1.password = user.password;
                                        break;
                                    }
                                }
                            }
                        }
                        case 3 -> {
                            User user = User.register(management);
                            user.role = Roles.ADMIN;
                            management.addUser(user);
                            System.out.println("Вы успешно зарегестрировались!");
                        }
                        case 4 -> {
                            exit = true;
                            exitLogin = true;
                            System.out.println("Вы успешно вышли!");
                        }
                        default -> System.out.println("Введите правильное число!");
                    }
                } catch (InputMismatchException e) {
                    System.err.println("Введите число!");
                    scanner.nextLine();
                }
            }
            if (isLoggedIn) {
                while (isLoggedIn) {
                    System.out.println("*** Выберите действие ***");
                    for (int i = 0; i < printTexts.length; i++) {
                        if (i >= 9) {
                            System.out.println((i + 1) + " -> " + printTexts[i]);
                        } else {
                            System.out.println((i + 1) + "  -> " + printTexts[i]);

                        }
                    }
                    try {
                        System.out.print("Команда: ");
                        int num = scanner.nextInt();
                        switch (num) {
                            case 1 -> {
                                Group group = Group.createGroup(management);
                                management.addGroup(group);
                                System.out.println("Вы успешно добавили группу");
                            }
                            case 2 -> {
                                if (management.groups.length == 0) {
                                    System.out.println("Групп нету!");
                                } else {
                                    User user = User.register(management);
                                    user.role = Roles.STUDENT;
                                    Student student = new Student();
                                    student.email = user.email;
                                    student.firstName = user.firstName;
                                    student.lastName = user.lastName;
                                    student.gender = user.gender;
                                    student.password = user.password;
                                    student.phoneNumber = user.phoneNumber;
                                    while (true) {
                                        System.out.print("Введите название группы: ");
                                        String groupName = scanner.nextLine();
                                        boolean found = false;
                                        for (Group group : management.groups) {
                                            if (groupName.equals(group.groupName)) {
                                                group.addStudent(student);
                                                management.addStudentToAllStudents(student);
                                                System.out.println("Студент успешно добавлен!");
                                                found = true;
                                                break;
                                            }
                                        }
                                        if (found) {
                                            break;
                                        } else {
                                            System.out.println("Такой группы нету");
                                        }
                                    }
                                }

                            }
                            case 3 -> {
                                if (management.groups.length == 0) {
                                    System.out.println("Групп нету!");
                                } else {
                                    Lesson lesson = new Lesson();
                                    lesson.lessonName = inputString("Введите название урока: ");
                                    lesson.taskDescription = inputString("Введите описание задачи");
                                    while (true) {
                                        System.out.print("Введите название группы: ");
                                        String groupName = scanner.nextLine();
                                        boolean found = false;
                                        for (Group group : management.groups) {
                                            if (groupName.equals(group.groupName)) {
                                                boolean lessonFound = false;
                                                for (Lesson lesson1 : group.lessons) {
                                                    if (lesson1.lessonName.equals(lesson.lessonName)) {
                                                        lessonFound = true;
                                                        break;
                                                    }
                                                }
                                                if (!lessonFound) {
                                                    group.addLesson(lesson);
                                                    management.addLesson(lesson);
                                                    System.out.println("Урок успешно добавлен!");
                                                    found = true;

                                                } else {
                                                    System.out.println("Урок с таким названием уже существует!");
                                                }
                                                break;
                                            }
                                        }
                                        if (found) {
                                            break;
                                        } else {
                                            System.out.println("Такой группы нету!");
                                        }
                                    }
                                }
                            }
                            case 4 -> {
                                scanner.nextLine();
                                System.out.print("Введите название группы: ");
                                String groupName = scanner.nextLine();
                                Group group = null;
                                for (Group group1 : management.groups) {
                                    if (groupName.equals(group1.groupName)) {
                                        group = group1;
                                        break;
                                    }
                                }
                                if (group != null) {
                                    System.out.println(group);
                                } else {
                                    System.out.println("Такой группы нету!");
                                }
                            }
                            case 5 -> {
                                if (management.groups.length == 0) {
                                    System.out.println("Групп нету!");
                                } else {
                                    System.out.println("Все группы: ");
                                    for (Group group : management.groups) {
                                        System.out.println(group);
                                    }
                                }
                            }
                            case 6 -> {
                                scanner.nextLine();
                                System.out.print("Введите email: ");
                                String email = scanner.nextLine();
                                Student student = null;
                                for (Student student1 : management.allStudents) {
                                    if (student1.email.equals(email)) {
                                        student = student1;
                                        break;
                                    }
                                }
                                if (student != null) {
                                    System.out.println(student);
                                } else {
                                    System.out.println("Студента с таким email нету!");
                                }

                            }
                            case 7 -> {
                                scanner.nextLine();
                                System.out.print("Введите название группы: ");
                                String groupName = scanner.nextLine();
                                Group group = null;
                                for (Group group1 : management.groups) {
                                    if (groupName.equals(group1.groupName)) {
                                        group = group1;
                                        break;
                                    }
                                }
                                if (group != null) {
                                    if (group.students.length == 0) {
                                        System.out.println("Студентов нету!");
                                    } else {
                                        System.out.println("Все студенты группы " + groupName + ": ");
                                        for (Student student : group.students) {
                                            System.out.println(student);
                                        }
                                    }
                                } else {
                                    System.out.println("Такой группы нету!");
                                }
                            }
                            case 8 -> {
                                scanner.nextLine();
                                System.out.print("Введите название урока: ");
                                String lessonName = scanner.nextLine();
                                boolean found = false;
                                for (Lesson lesson : management.allLessons) {
                                    if (lesson.lessonName.equals(lessonName)) {
                                        found = true;
                                        System.out.println(lesson);
                                        break;
                                    }
                                }
                                if (!found) {
                                    System.out.println("Такого урока нету!");
                                }
                            }
                            case 9 -> {
                                scanner.nextLine();
                                System.out.println("Введите email: ");
                                String email = scanner.nextLine();
                                Student student = null;
                                for (Student student1 : management.allStudents) {
                                    if (student1.email.equals(email)) {
                                        student = student1;
                                        break;
                                    }
                                }
                                if (student != null) {
                                    for (Group group : management.groups) {
                                        for (Student student1 : group.students) {
                                            if (student1.email.equals(student.email)) {
                                                System.out.println("Все уроки ученика: ");
                                                for (Lesson lesson : group.lessons) {
                                                    System.out.println(lesson);
                                                }
                                            }
                                        }
                                    }
                                } else {
                                    System.out.println("Студента с таким email нету!");
                                }
                            }
                            case 10 -> {
                                scanner.nextLine();
                                System.out.print("Введите название группы: ");
                                String groupName = scanner.nextLine();
                                Group group = null;
                                for (Group group1 : management.groups) {
                                    if (group1.groupName.equals(groupName)) {
                                        group = group1;
                                        break;
                                    }
                                }
                                if (group != null) {
                                    System.out.println("Все уроки группы " + group.groupName + ": ");
                                    for (Lesson lesson : group.lessons) {
                                        System.out.println(lesson);
                                    }
                                } else {
                                    System.out.println("Студента с таким email нету!");
                                }
                            }
                            case 11 -> {
                                scanner.nextLine();
                                System.out.print("Введите email: ");
                                String email = scanner.nextLine();
                                Student student = null;
                                for (Student student1 : management.allStudents) {
                                    if (student1.email.equals(email)) {
                                        student = student1;
                                        break;
                                    }
                                }
                                if (student != null) {
                                    boolean updateStudentBoolean = false;
                                    while (!updateStudentBoolean) {
                                        System.out.println("""
                                                Что вы хотите изменить?
                                                1.Имя
                                                2.Фамилия
                                                3.Email
                                                4.Пароль
                                                5.Номер телефона
                                                6.Пол
                                                7.Завершить изменения""");
                                        try {
                                            int studentNum = scanner.nextInt();
                                            switch (studentNum) {
                                                case 1 -> student.firstName = inputString("Введите имя: ");
                                                case 2 -> student.lastName = inputString("Введите фамилию: ");
                                                case 3 -> {
                                                    System.out.print("Введите email: ");
                                                    while (true) {
                                                        String studentEmail = scanner.nextLine();
                                                        try {
                                                            for (User user : management.users) {
                                                                if (user.email.equalsIgnoreCase(studentEmail)) {
                                                                    throw new EmailException();
                                                                }
                                                            }
                                                            if (studentEmail.isEmpty()) {
                                                                throw new VoidException();
                                                            }
                                                            if (studentEmail.contains("@") && email.length() > 6) {
                                                                student.email = studentEmail;
                                                                break;
                                                            } else {
                                                                throw new ValidationEmailException();
                                                            }

                                                        } catch (EmailException e) {
                                                            System.err.println("Пользователь с таким email уже существует!");
                                                        } catch (VoidException e) {
                                                            System.err.println("Значение не должно быть пустым!");
                                                        } catch (ValidationEmailException e) {
                                                            System.out.println("Введите email правильно");
                                                        }
                                                    }

                                                }
                                                case 4 -> {
                                                    System.out.print("Введите пароль: ");
                                                    while (true) {
                                                        String password = scanner.nextLine();

                                                        if (password.isEmpty()) {
                                                            System.out.println("Значение не должно быть пустым!");
                                                        } else {
                                                            if (password.length() >= 4) {
                                                                student.password = password;
                                                                break;
                                                            } else {
                                                                System.out.println("Длина пароля должна быть больше или равно 4");
                                                            }
                                                        }
                                                    }
                                                }
                                                case 5 -> {
                                                    System.out.print("Введите номер телефона: ");
                                                    while (true) {
                                                        String phoneNumber = scanner.nextLine();
                                                        if (phoneNumber.length() == 10 && phoneNumber.matches("\\d+")) {
                                                            student.phoneNumber = phoneNumber;
                                                            break;
                                                        } else {
                                                            System.out.println("Введите правильный номер телефона!");
                                                        }

                                                    }
                                                }
                                                case 6 -> {
                                                    System.out.print("Введите пол (male/female): ");
                                                    while (true) {
                                                        String gender = scanner.nextLine();
                                                        if (gender.equalsIgnoreCase("male") || gender.equalsIgnoreCase("female")) {
                                                            student.gender = gender;
                                                            break;
                                                        } else {
                                                            System.out.println("Введите правильный гендер!");
                                                        }

                                                    }
                                                }
                                                case 7 -> {
                                                    for (Student student2 : management.allStudents) {
                                                        if (email.equals(student2.email)) {
                                                            student2.email = student.email;
                                                            student2.firstName = student.firstName;
                                                            student2.phoneNumber = student.phoneNumber;
                                                            student2.password = student.password;
                                                            student2.lastName = student.lastName;
                                                            student2.gender = student.gender;
                                                        }
                                                    }
                                                    for (User user : management.users) {
                                                        if (email.equals(user.email)) {
                                                            user.email = student.email;
                                                            user.firstName = student.firstName;
                                                            user.phoneNumber = student.phoneNumber;
                                                            user.password = student.password;
                                                            user.lastName = student.lastName;
                                                            user.gender = student.gender;
                                                        }
                                                    }
                                                    for (Group group : management.groups) {
                                                        for (Student student1 : group.students) {
                                                            if (email.equals(student1.email)) {
                                                                student1.email = student.email;
                                                                student1.firstName = student.firstName;
                                                                student1.phoneNumber = student.phoneNumber;
                                                                student1.password = student.password;
                                                                student1.lastName = student.lastName;
                                                                student1.gender = student.gender;
                                                            }
                                                        }
                                                    }
                                                    System.out.println("Вы успешно поменяли данные студента!");
                                                    updateStudentBoolean = true;
                                                }
                                                default -> System.out.println("Введите правильное число!");
                                            }
                                        } catch (InputMismatchException e) {
                                            System.out.println("Введите число!");
                                        }
                                    }
                                }
                            }
                            case 12 -> {
                                if (management.groups.length > 0) {
                                    System.out.println("Все доступные группы: ");
                                    for (int i = 0; i < management.groups.length; i++) {
                                        if (management.groups[i] != null) {
                                            Group currentGroup = management.groups[i];
                                            System.out.println(currentGroup);
                                        }
                                    }
                                    scanner.nextLine();
                                    System.out.print("Введите название группы: ");
                                    String groupName = scanner.nextLine();
                                    Group group = null;
                                    for (Group group1 : management.groups) {
                                        if (group1.groupName.equals(groupName)) {
                                            group = group1;
                                            break;
                                        }
                                    }
                                    if (group != null) {
                                        boolean updateGroupBoolean = false;
                                        while (!updateGroupBoolean) {
                                            System.out.println("""
                                                    Что вы хотите изменить?
                                                    1.Название группы
                                                    2.Описание
                                                    3.Завершить изменения""");
                                            try {
                                                int studentNum = scanner.nextInt();
                                                switch (studentNum) {
                                                    case 1 ->
                                                            group.groupName = inputString("Введите название группы: ");
                                                    case 2 -> group.description = inputString("Введите описание: ");
                                                    case 3 -> {
                                                        for (Group group1 : management.groups) {
                                                            if (group1.groupName.equals(groupName)) {
                                                                group1.groupName = group.groupName;
                                                                group1.description = group.description;
                                                            }

                                                        }
                                                        System.out.println("Группа успешно изменена!");
                                                        updateGroupBoolean = true;
                                                    }
                                                    default -> System.out.println("Введите правильное число!");
                                                }
                                            } catch (InputMismatchException e) {
                                                System.out.println("Введите число!");
                                            }
                                        }
                                    }
                                } else {
                                    System.out.println("Групп нету!");
                                }
                            }
                            case 13 -> {
                                if (management.allLessons.length > 0) {
                                    scanner.nextLine();
                                    System.out.print("Введите название урока: ");
                                    String lessonName = scanner.nextLine();
                                    boolean found = false;
                                    for (Lesson lesson : management.allLessons) {
                                        if (lesson.lessonName.equals(lessonName)) {
                                            found = true;
                                            break;
                                        }
                                    }
                                    if(found){
                                        management.deleteLesson(lessonName);
                                        for (Group group : management.groups) {
                                            for (Lesson lesson : group.lessons) {
                                                if(lesson.lessonName.equals(lessonName)){
                                                    Lesson[] newLessons = new Lesson[10000];
                                                    int newIndex = 0;
                                                    for (Lesson lesson1 : group.lessons) {
                                                        if (lesson1 != null && !(lesson1.lessonName.equals(lessonName))) {
                                                            newLessons[newIndex++] = lesson1;
                                                        }
                                                    }
                                                    group.lessons = newLessons;
                                                }
                                            }
                                        }
                                        System.out.println("Урок успешно удален!");
                                    }
                                } else {
                                    System.out.println("Уроков нету!");
                                }
                            }
                            case 14 -> {
                              if( management.groups.length > 0){
                                  System.out.print("Введите название группы: ");
                                  String groupName = scanner.nextLine();
                                  boolean found = false;
                                  for (Group group : management.groups) {
                                      if(group.groupName.equals(groupName)){
                                          found = true;
                                          break;
                                      }
                                  }
                                  if(found){
                                      management.deleteGroup(groupName);
                                      System.out.println("Группа успешно удалена!");
                                  } else {
                                      System.out.println("Группы с таким именем нету!");
                                  }
                              } else {
                                  System.out.println("Групп нету!");
                              }
                            }
                            case 15 -> {

                            }
                            case 16 -> {
                                isLoggedIn = false;
                                System.out.println("Вы успешно вышли!");
                            }
                            default -> System.out.println("Введите правильное число!");
                        }
                    } catch (InputMismatchException e) {
                        System.err.println("Введите число!");
                        scanner.nextLine();
                    }

                }
            }

        }

    }
}