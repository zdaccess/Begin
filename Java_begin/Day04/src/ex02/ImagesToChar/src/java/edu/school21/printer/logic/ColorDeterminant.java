package edu.school21.printer;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import com.diogonunes.jcdp.color.ColoredPrinter;
import com.diogonunes.jcdp.color.api.Ansi;
import java.io.InputStream;

public class ColorDeterminant {
    private InputStream path;
    private String[][]  arrayImage;
    private Ansi.BColor WHITE;
    private Ansi.BColor BLACK;

    public ColorDeterminant(String white, String black) {
        path = ColorDeterminant.class.getResourceAsStream("/resources/image.bmp");
        arrayImage = new String[16][16];
        WHITE = Ansi.BColor.valueOf(white);
        BLACK = Ansi.BColor.valueOf(black);
    }

    public void defineColor() throws IOException {
        BufferedImage image = ImageIO.read(path);
        ColoredPrinter color = new ColoredPrinter();
        int width = image.getWidth();
        int height = image.getHeight();
        for (Integer x = 0; x < height; x++) {
            for (Integer y = 0; y < width; y++) {
                try {
                    if (image.getRGB(y, x) == Color.WHITE.getRGB()) {
                        color.print("  ", Ansi.Attribute.NONE,
                                    Ansi.FColor.NONE,
                                    WHITE);
                    } else {
                        color.print("  ", Ansi.Attribute.NONE,
                                    Ansi.FColor.NONE,
                                    BLACK);
                    }
                } catch (IllegalArgumentException e) {
                    System.out.println("Error! This color doesn't exist!");
                }
            }
            System.out.println();
        }
    }
}

