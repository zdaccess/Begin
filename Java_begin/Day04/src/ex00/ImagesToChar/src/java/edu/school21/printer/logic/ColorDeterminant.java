package edu.school21.printer;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
public class ColorDeterminant {
    private Path        uri;
    private String[][]  arrayImage;
    private String      WHITE;
    private String      BLACK;

    public ColorDeterminant(String white, String black, String uri) {
        this.uri = Paths.get(uri);
        arrayImage = new String[16][16];
        WHITE = white;
        BLACK = black;
    }

    public void defineColor() throws IOException {
        BufferedImage image = ImageIO.read(uri.toFile());
        int width = image.getWidth();
        int height = image.getHeight();
        for (Integer x = 0; x < height; x++) {
            for (Integer y = 0; y < width; y++) {
                if (image.getRGB(y, x) == Color.WHITE.getRGB()) {
                    arrayImage[y][x] = WHITE;
                    System.out.print(arrayImage[y][x] + " ");
                } else {
                    arrayImage[y][x] = BLACK;
                    System.out.print(arrayImage[y][x] + " ");
                }
            }
            System.out.println();
        }
    }
}
