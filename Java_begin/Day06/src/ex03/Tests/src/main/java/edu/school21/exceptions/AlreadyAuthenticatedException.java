package edu.school21.numbers;

public class AlreadyAuthenticatedException extends Exception {
    public AlreadyAuthenticatedException() {
        System.out.println("Authentication has been completed!");
    }
}
