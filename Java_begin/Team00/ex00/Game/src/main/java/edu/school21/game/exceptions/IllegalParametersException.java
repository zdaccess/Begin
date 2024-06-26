package edu.school21.game.exceptions;

public class IllegalParametersException extends RuntimeException {
    public IllegalParametersException(String message) {
        System.out.println(message);
        System.err.println(
                "Ошибка! Количество эелементов превышает размер карты!");
        System.exit(1);
    }
}
