package edu.school21.game.logic.map;

import edu.school21.chaselogic.coordinates.Coordinates;
import edu.school21.chaselogic.coordinates.CoordinatesRandomizer;
import edu.school21.chaselogic.map.GameMap;
import edu.school21.chaselogic.objects.Enemy;
import edu.school21.chaselogic.objects.Player;
import edu.school21.game.logic.enemies.EnemiesArray;
import edu.school21.game.logic.parameters.GameArguments;

public class MapWithObjects extends GameMap {
    private Player player = null;
    private EnemiesArray enemies = null;

    public MapWithObjects(GameArguments arguments) {
        CoordinatesRandomizer random = null;
        Coordinates goal = null;

        size = arguments.getMapSize();
        map = new MapObjectType[size][size];
        random = new CoordinatesRandomizer(this);

        player = new Player(random.generate(), this);
        setObject(player.getCoordinates(), MapObjectType.PLAYER);

        goal = random.generate();
        setObject(goal, MapObjectType.GOAL);
        buildPathToGoal(goal);

        for (int i = 0; i < arguments.getWallsCount(); ++i) {
            setObject(random.generate(), MapObjectType.WALL);
        }

        enemies = new EnemiesArray(arguments.getEnemiesCount(), this, player);

        for (int i = 0; i < arguments.getEnemiesCount(); ++i) {
            Enemy enemy = new Enemy(random.generate(), this);

            setObject(enemy.getCoordinates(), MapObjectType.ENEMY);
            enemies.add(enemy);
        }

        for (int y = 0; y < size; ++y) {
            for (int x = 0; x < size; ++x) {
                Coordinates coordinates = new Coordinates(y, x);

                if (getObject(coordinates) == null) {
                    setObject(coordinates, MapObjectType.EMPTY);
                }
            }
        }

    }

    public Player player() {
        return player;
    }

    public EnemiesArray enemies() {
        return enemies;
    }

    private void buildPathToGoal(Coordinates goal) {
        Coordinates from = player.getCoordinates();

        while (true) {
            if (from.getY() < goal.getY()) {
                from = new Coordinates(from.getX(), from.getY() + 1);
            } else if (from.getY() > goal.getY()) {
                from = new Coordinates(from.getX(), from.getY() - 1);
            } else if (from.getX() < goal.getX()) {
                from = new Coordinates(from.getX() + 1, from.getY());
            } else if (from.getX() > goal.getX()) {
                from = new Coordinates(from.getX() - 1, from.getY());
            }

            if (from.getX().equals(goal.getX())
                    && from.getY().equals(goal.getY())) {
                break;
            } else {
                this.setObject(from, MapObjectType.EMPTY);
            }

        }
    }

}
