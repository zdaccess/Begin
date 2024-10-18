package edu.school21.model;


import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import lombok.Getter;
import lombok.Setter;

public class Tank {
    @Getter
    private final double y;
    private final double lol;
    @Getter
    @Setter
    private Health health;
    @Getter
    @Setter
    private double x;
    private Image image;
    private double width;
    private double height;

    public Tank(String filename, double x, double y, Group group, double healthX, double healthY, double lol) {
        this.x = x;
        this.y = y;
        this.lol = lol;
        health = new Health(group, healthX, healthY);
        setImage(new Image(filename));
    }

    private void setImage(Image image) {
        this.image = image;
        width = image.getWidth();
        height = image.getHeight();
    }

    public void moveLeft(int step) {
        if (x >= 3) x -= step;
    }

    public void moveRight(int step) {
        if (x < 748 - width) x += step;
    }

    public void render(GraphicsContext gc) {
        gc.drawImage(image, x, y);
    }

    public Rectangle2D getBoundary() {
        return new Rectangle2D(x, y + lol, width, height);
    }

    public void takeDamage() {
        health.takeDamage();
    }

    public void kill() {
        health.setHealthLvl(-10);
    }

    public boolean checkLeaveGame() {
        return health.getHealthLvl() == -10;
    }

    public boolean isDead() {
        return health.isDead();
    }
}
