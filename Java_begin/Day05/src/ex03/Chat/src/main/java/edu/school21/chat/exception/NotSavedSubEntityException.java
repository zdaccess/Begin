package edu.school21.chat;

public class NotSavedSubEntityException extends RuntimeException {
    public NotSavedSubEntityException(String str) {
        System.err.println("Error! " + str + " not found!");
    }
}
