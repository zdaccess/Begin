package edu.school21.printer;

import com.beust.jcommander.*;
import java.io.IOException;

public class Program {
    public static void main(String[] args) {
        ArgumentsColor color = new ArgumentsColor();
        try {
            JCommander.newBuilder().addObject(color).build().parse(args);
            ColorDeterminant print = new ColorDeterminant(
                    color.getWhite(),
                    color.getBlack()
            );
            print.defineColor();
        } catch (ParameterException e) {
            System.out.println("Error! You must enter two arguments: " +
                                       "example --white=RED --black=GREEN");
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (IllegalArgumentException e) {
            System.out.println("Error! This color doesn't exist!");
        }
    }
}