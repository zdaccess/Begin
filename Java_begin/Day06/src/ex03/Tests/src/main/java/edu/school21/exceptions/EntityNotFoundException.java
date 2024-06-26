package edu.school21.numbers;

public class EntityNotFoundException extends Exception {
    public EntityNotFoundException() {
        System.err.println("Error! This user does not exist!");
    }
}
