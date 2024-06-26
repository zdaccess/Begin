package edu.school21.chaselogic.objects;

import edu.school21.chaselogic.coordinates.Coordinates;
import edu.school21.chaselogic.map.GameMap;
import edu.school21.chaselogic.map.GameMap.MapObjectType;
import edu.school21.chaselogic.status.PlayerStatus;

public class Player extends MovingObject {
    public enum Action {
        UP, DOWN, LEFT, RIGTH, INVALID, CLOSE, WATCH;
    }

    public Player(Coordinates coordinates, GameMap map) {
        super(coordinates, map);
    }

    public void go(Player.Action action) {
        Coordinates to = null;

        try {
            MapObjectType type = null;

            if (action.equals(Player.Action.UP)) {
                to = new Coordinates(getX(), getY() - 1);
            } else if (action.equals(Player.Action.DOWN)) {
                to = new Coordinates(getX(), getY() + 1);
            } else if (action.equals(Player.Action.LEFT)) {
                to = new Coordinates(getX() - 1, getY());
            } else if (action.equals(Player.Action.RIGTH)) {
                to = new Coordinates(getX() + 1, getY());
            }

            type = map.getObject(to);

            if (type.equals(MapObjectType.EMPTY)) {
                PlayerStatus.setContinue();
            } else if (type.equals(MapObjectType.WALL)) {
                PlayerStatus.setWait();
            } else if (type.equals(MapObjectType.ENEMY)) {
                PlayerStatus.setLose();
            } else if (type.equals(MapObjectType.GOAL)) {
                PlayerStatus.setWin();
            }

            if (PlayerStatus.get().equals(PlayerStatus.CONTINUE)) {
                map.swap(this.getCoordinates(), to);
                this.setCoordinates(to);
            } else if (PlayerStatus.get().equals(PlayerStatus.LOSE)) {
                map.setObject(this.getCoordinates(), MapObjectType.EMPTY);
                map.swap(this.getCoordinates(), to);
            } else if (PlayerStatus.get().equals(PlayerStatus.WIN)) {
                map.setObject(this, MapObjectType.EMPTY);
            }

        } catch (Exception e) {
            map.setObject(this.getCoordinates(), MapObjectType.EMPTY);
            PlayerStatus.setLose();
        }
    }

    public boolean isSurrounded() {
        boolean result = true;

        result &= isObstacle(new Coordinates(getX(), getY() - 1));
        result &= isObstacle(new Coordinates(getX(), getY() + 1));
        result &= isObstacle(new Coordinates(getX() - 1, getY()));
        result &= isObstacle(new Coordinates(getX() + 1, getY()));

        return result;
    }

    private boolean isObstacle(Coordinates coordinates) {
        boolean result = false;

        try {
            MapObjectType type = map.getObject(coordinates);

            if (type.equals(MapObjectType.WALL)
                    || type.equals(MapObjectType.ENEMY)) {
                result = true;
            }

        } catch (Exception e) {
            result = true;
        }

        return result;
    }

}
