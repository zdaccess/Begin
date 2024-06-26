package edu.school21.reflection;

import java.util.Random;
import java.util.Scanner;
import java.util.StringJoiner;

public class Car {
    private String model;
    private String govNumber;
    private String owner;

    public Car() {
        this.model = "model";
        this.govNumber = "Default last name";
        this.owner = "T458TN16RUS";
    }

    public Car(String firstName, String lastName, String owner) {
        this.model = firstName;
        this.govNumber = lastName;
        this.owner = owner;
    }

    public String randomNumber() {
        String str = "";
        Random random = new Random();
        System.out.println("Change the license plate number of your car! " +
                                   "If you want to change, select YES.");
        Scanner scanner = new Scanner(System.in);
        String answer = scanner.nextLine();
        if (answer.equals("YES")) {
            String one = String.valueOf((char)random.nextInt(65, 91));
            Integer two = Integer.valueOf(random.nextInt(0, 10));
            Integer three = Integer.valueOf(random.nextInt(0, 10));
            Integer four = Integer.valueOf(random.nextInt(0, 10));
            String five = String.valueOf((char)random.nextInt(65, 91));
            String six = String.valueOf((char)random.nextInt(65, 91));
            Integer seven = Integer.valueOf(random.nextInt(0, 10));
            Integer eight = Integer.valueOf(random.nextInt(0, 10));
            govNumber = one + two + three + four + five + six + seven + eight +
                    "RUS";
            str = "The state number has changed to " + govNumber;
        } else {
            str = "The state number has not changed!";
        }
        return str;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Car.class.getSimpleName() + "[", "]")
                .add("model='" + model + "'")
                .add("govNumber='" + govNumber + "'")
                .add("owner=" + owner)
                .toString();
    }
}