package edu.school21.chaselogic.chase;

import edu.school21.chaselogic.coordinates.Coordinates;
import edu.school21.chaselogic.objects.Enemy;
import edu.school21.chaselogic.objects.Player;

public class DestinationChooser {
    private Player player = null;

    public DestinationChooser(Player player) {
        this.player = player;
    }

    public Coordinates tryY(Enemy enemy) {
        Coordinates dest = null;

        if (!enemy.getY().equals(player.getY())) {
            if (enemy.getY() < player.getY()) {
                dest = new Coordinates(enemy.getX(), enemy.getY() + 1);
            } else {
                dest = new Coordinates(enemy.getX(), enemy.getY() - 1);
            }
        }

        return dest;
    }

    public Coordinates tryX(Enemy enemy) {
        Coordinates dest = null;

        if (!enemy.getX().equals(player.getX())) {
            if (enemy.getX() < player.getX()) {
                dest = new Coordinates(enemy.getX() + 1, enemy.getY());
            } else {
                dest = new Coordinates(enemy.getX() - 1, enemy.getY());
            }
        }

        return dest;
    }

}
