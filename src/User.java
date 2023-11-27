import exceptions.EmailException;
import exceptions.ValidationEmailException;
import exceptions.VoidException;

import java.util.Scanner;

public class User {
    private static final Scanner scanner = new Scanner(System.in);

    public User() {
    }

    public User(String firstName, String lastName, String email, String password, String phoneNumber, String gender, Roles role) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.gender = gender;
        this.role = role;
    }

    String firstName;
    String lastName;
    String email;
    String password;
    String phoneNumber;
    String gender;
    Roles role;

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

    public static User register(Management management) {
        User newUser = new User();
        newUser.firstName = inputString("Введите имя: ");
        newUser.lastName = inputString("Введите фамилию: ");
        System.out.print("Введите номер телефона: ");
        while (true) {
            String num = scanner.nextLine();
            if (num.length() == 10 && num.matches("\\d+")) {
                newUser.phoneNumber = num;
                break;
            } else {
                System.out.println("Введите правильный номер телефона!");
            }

        }
        System.out.print("Введите пол (male/female): ");
        while (true) {
            String gender = scanner.nextLine();
            if (gender.equalsIgnoreCase("male") || gender.equalsIgnoreCase("female")) {
                newUser.gender = gender;
                break;
            } else {
                System.out.println("Введите правильный гендер!");
            }

        }
        System.out.print("Введите email: ");
        while (true) {
            String email = scanner.nextLine();
            try {
                for (User user : management.users) {
                    if (user.email.equalsIgnoreCase(email)) {
                        throw new EmailException();
                    }
                }
                if (email.isEmpty()) {
                    throw new VoidException();
                }
                if (email.contains("@") && email.length() > 6) {
                    newUser.email = email;
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
        System.out.print("Введите пароль: ");
        while (true) {
            String password = scanner.nextLine();

            if (password.isEmpty()) {
                System.out.println("Значение не должно быть пустым!");
            } else {
                if (password.length() >= 4) {
                    newUser.password = password;
                    break;
                } else {
                    System.out.println("Длина пароля должна быть больше или равно 4");
                }
            }
        }
        return newUser;
    }

    public static User login(User[] users) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Введите email: ");
        String email = scanner.nextLine();
        System.out.print("Введите пароль: ");
        String password = scanner.nextLine();
        for (User user : users) {
            if (user != null && email.equals(user.email) && password.equals(user.password)) {
                System.out.println("Вы успешно вошли в аккаунт!✅");
                return user;
            }
        }
        System.out.println("Не верный пароль или логин❌");
        return null;
    }

    public static User forgotYourPassword(Management management) {
        System.out.print("Введите email: ");
        String email = scanner.nextLine();
        User newUser = null;
        for (User user : management.users) {
            if (user.email.equalsIgnoreCase(email)) {
                newUser = user;
                break;
            }
        }
        if (newUser != null) {
            System.out.print("Введите пароль: ");
            while (true) {
                String password = scanner.nextLine();

                if (password.isEmpty()) {
                    System.out.println("Значение не должно быть пустым!");
                } else {
                    if (password.length() >= 4) {
                        newUser.password = password;
                        break;
                    } else {
                        System.out.println("Длина пароля должна быть больше или равно 4");
                    }
                }
            }
            System.out.println("Вы успешно поменяли пароль!");
            return newUser;
        }
        return null;
    }
}
