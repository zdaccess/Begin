package edu.school21.model;

import edu.school21.ui.MainWindow;
import javafx.animation.AnimationTimer;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import lombok.Getter;

public class Shoot extends AnimationTimer {
    private final Tank enemy;
    private final double x;
    private final double step;
    private final boolean isMyBullet;
    private final GraphicsContext gc;
    private final Image fire;

    private Image image;
    @Getter
    private double y;
    private double width;
    private double height;

    public Shoot(Tank player, String filename, Tank enemy, GraphicsContext gc, double forCorrect) {
        x = (player.getX() + player.getBoundary().getMaxX()) / 2;
        y = player.getY() + forCorrect;
        fire = new Image("fire.png");
        isMyBullet = forCorrect == 0;
        this.enemy = enemy;
        this.gc = gc;
        if (y == 630) step = -10;
        else step = 10;
        setImage(new Image(filename));
    }

    private void setImage(Image image) {
        this.image = image;
        width = image.getWidth();
        height = image.getHeight();
    }

    public void render() {
        gc.drawImage(image, x, y);
    }

    public void move() {
        y += step;
    }

    public Rectangle2D getBoundary() {
        return new Rectangle2D(x, y, width, height);
    }

    @Override
    public void handle(long l) {
        if (y > 720 || y < 10) stop();
        else if (enemy.getBoundary().intersects(getBoundary())) {
            enemy.takeDamage();
            gc.drawImage(fire, x, y);

            if (isMyBullet) MainWindow.sendCommandToServer("hit " + enemy.getHealth().getHealthLvl());

            stop();
        } else {
            move();
            render();
        }
    }
}
