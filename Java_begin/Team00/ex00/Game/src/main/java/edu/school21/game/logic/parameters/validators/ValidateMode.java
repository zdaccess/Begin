package edu.school21.game.logic.parameters.validators;

import com.beust.jcommander.IParameterValidator;
import com.beust.jcommander.ParameterException;

public class ValidateMode implements IParameterValidator {

    @Override
    public void validate(String name, String s1) throws ParameterException {
        if (!s1.equals("production") && !s1.equals("development")) {
            System.err.println("Ошибка в " + name
                    + "! Игра запустится только в двух режимах: production, development!");
            throw new ParameterException(name + s1);
        }
    }
}
