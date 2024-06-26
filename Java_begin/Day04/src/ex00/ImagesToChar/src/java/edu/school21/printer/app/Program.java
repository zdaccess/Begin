package edu.school21.printer;

import java.io.IOException;
import java.nio.file.Paths;

public class Program {
    public static void main(String[] args) throws IOException {
        if (args.length == 3) {
            if (args[0].length() == 1 && args[1].length() == 1) {
                if (Paths.get(args[2]).isAbsolute()) {
                    if (Paths.get(args[2]).isAbsolute()) {
                        String[] pathBmp = args[2].split("/");
                        String[] bmpName = pathBmp[pathBmp.length - 1].split(
                                "\\.");
                        if (bmpName[bmpName.length - 1].equals("bmp")
                        || bmpName[bmpName.length - 1].equals("BMP")) {
                            ColorDeterminant work = new ColorDeterminant(
                                    args[0],
                                    args[1],
                                    args[2]);
                            work.defineColor();
                        } else
                            System.out.println("Error! File without bmp " +
                                                       "permission!");
                    } else
                        System.out.println("Error! File does not exist!");
                } else
                    System.out.println("Error! You must enter an absolute " +
                                               "path!");
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
