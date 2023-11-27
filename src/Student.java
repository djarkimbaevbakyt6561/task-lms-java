public class Student {
    String firstName;
    String lastName;
    String email;
    String password;
    String phoneNumber;
    String gender;

    @Override
    public String toString() {
        return "Ученик " +
                firstName + " " + lastName +
                ", email: " + email +
                ", пароль: '" + password +
                ", номер телефона: " + phoneNumber +
                ", пол: " + gender;
    }
}
