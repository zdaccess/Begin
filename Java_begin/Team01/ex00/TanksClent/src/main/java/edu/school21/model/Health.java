package edu.school21.model;

import javafx.scene.Group;
import javafx.scene.shape.Rectangle;
import lombok.Getter;
import lombok.Setter;


public class Health {
    private static final int MAX_HEALTH = 100;
    private final Rectangle lifeRect;

    @Getter
    @Setter
    private double healthLvl = MAX_HEALTH * 1.5;

    public Health(Group group, double x, double y) {
        var border = new Rectangle(MAX_HEALTH * 1.5 + 3, 18);
        border.setId("border");
        border.setX(x);
        border.setY(y);
        lifeRect = new Rectangle(healthLvl, 14);
        lifeRect.setId("healthLvl");
        lifeRect.setX(x + 2);
        lifeRect.setY(y + 2);
        group.getChildren().addAll(border, lifeRect);
    }

    public boolean isDead() {
        return healthLvl <= 0;
    }

    public void takeDamage() {
        if (healthLvl > 0) {
            healthLvl -= 5 * 1.5;
            lifeRect.setWidth(healthLvl);
        }
    }
}
