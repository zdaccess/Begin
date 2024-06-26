package edu.school21.printer;

import java.io.IOException;
import java.nio.file.Paths;
public class Program {
    public static void main(String[] args) throws IOException {
        if (args.length == 2) {
            if (args[0].length() == 1 && args[1].length() == 1) {
                ColorDeterminant work = new ColorDeterminant(args[0], args[1]);
                work.defineColor();
            } else
                System.out.println("Error! The first two arguments must " +
                                   "contain one character each!");
        } else {
            System.out.println("Error! Enter 3 arguments: white character, " +
                               "black character, absolute address of " +
                               "the BMP file!");
        }
    }
}
