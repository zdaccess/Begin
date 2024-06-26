package edu.school21.chaselogic.chase;

import edu.school21.chaselogic.coordinates.Coordinates;
import edu.school21.chaselogic.map.GameMap;
import edu.school21.chaselogic.objects.Enemy;
import edu.school21.chaselogic.objects.Player;

public class Chaser {
    public enum MoveStatus {
        DONE, SKIP;
    }

    private DestinationChooser chooser = null;
    private DestinationTypeValidator validator = null;

    public Chaser(GameMap map, Player player) {
        chooser = new DestinationChooser(player);
        validator = new DestinationTypeValidator(map);
    }

    public Coordinates chooseDestionation(Enemy enemy) {
        Coordinates dest = null;

        dest = chooser.tryX(enemy);
        dest = validator.validate(dest);

        if (dest == null) {
            dest = chooser.tryY(enemy);
            dest = validator.validate(dest);
        }

        if (dest == null) {
            dest = findFree(enemy);
        }

        if (dest == null) {
            dest = enemy;
        }

        return dest;
    }

    private Coordinates findFree(Enemy enemy) {
        Coordinates result = null;

        result = validator
                .isEmpty(new Coordinates(enemy.getX(), enemy.getY() - 1));

        if (result != null) {
            return result;
        }

        result = validator
                .isEmpty(new Coordinates(enemy.getX(), enemy.getY() + 1));

        if (result != null) {
            return result;
        }

        result = validator
                .isEmpty(new Coordinates(enemy.getX() - 1, enemy.getY()));

        if (result != null) {
            return result;
        }

        result = validator
                .isEmpty(new Coordinates(enemy.getX() + 1, enemy.getY()));

        if (result != null) {
            return result;
        }

        return result;
    }

}
