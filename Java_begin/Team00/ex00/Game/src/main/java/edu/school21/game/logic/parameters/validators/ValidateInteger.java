package edu.school21.game.logic.parameters.validators;

import com.beust.jcommander.IParameterValidator;
import com.beust.jcommander.ParameterException;

public class ValidateInteger implements IParameterValidator {
    @Override
    public void validate(String name, String s1) throws ParameterException {
        int nmb;
        if (s1.matches("-?\\d+(\\.\\d+)?")) {
            nmb = Integer.parseInt(s1);
            if (nmb <= 0) {
                System.err.println("Ошибка в " + name
                        + "! Необходимо ввести целое положительное число!");
                throw new ParameterException(name + s1);
            }
        } else {
            System.err.println("Ошибка в " + name
                    + "! Необходимо ввести целое положительное число!");
            throw new ParameterException(name + s1);
        }
    }
}
