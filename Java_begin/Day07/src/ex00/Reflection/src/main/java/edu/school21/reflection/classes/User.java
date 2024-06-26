package edu.school21.reflection;

import java.util.Random;
import java.util.Scanner;
import java.util.StringJoiner;

public class User {
    private final String firstName;
    private final String lastName;
    private Integer age;

    public User() {
        this.firstName = "Firstname";
        this.lastName = "Lastname";
        this.age = 0;
    }

    public User(String firstName, String lastName, int height) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = height;
    }

    public String ageRandom() {
        Random random = new Random();
        String str = "";
        System.out.println("To change age, enter YES.");
        Scanner scanner = new Scanner(System.in);
        String[] answer = scanner.nextLine().split(" ");
        if (answer.length == 1) {
            if (answer[0].equals("YES")) {
                age = random.nextInt(18, 101);
                str = "Age changed to " + age;
            } else {
                str = "The age hasn't changed!";
            }
        }
        return str;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", User.class.getSimpleName() + "[", "]")
                .add("firstName='" + firstName + "'")
                .add("lastName='" + lastName + "'")
                .add("age=" + age)
                .toString();
    }
}

