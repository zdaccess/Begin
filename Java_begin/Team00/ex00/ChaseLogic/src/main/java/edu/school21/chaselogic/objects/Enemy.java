package edu.school21.chaselogic.objects;

import edu.school21.chaselogic.coordinates.Coordinates;
import edu.school21.chaselogic.map.GameMap;
import edu.school21.chaselogic.map.GameMap.MapObjectType;
import edu.school21.chaselogic.status.PlayerStatus;

public class Enemy extends MovingObject {
    private enum MovePossibility {
        CAN, CANT
    }

    public Enemy(Coordinates coordinates, GameMap map) {
        super(coordinates, map);
    }

    public void go(Coordinates to) {
        MapObjectType type = null;
        MovePossibility move = MovePossibility.CANT;

        type = map.getObject(to);

        if (type.equals(MapObjectType.EMPTY)) {
            move = MovePossibility.CAN;
        } else if (type.equals(MapObjectType.WALL)) {
            move = MovePossibility.CANT;
        } else if (type.equals(MapObjectType.ENEMY)) {
            move = MovePossibility.CANT;
        } else if (type.equals(MapObjectType.GOAL)) {
            move = MovePossibility.CANT;
        } else if (type.equals(MapObjectType.PLAYER)) {
            PlayerStatus.setLose();
        }

        if (move.equals(MovePossibility.CAN)) {
            map.swap(this.getCoordinates(), to);
            this.setCoordinates(to);
        } else if (PlayerStatus.get().equals(PlayerStatus.LOSE)) {
            map.setObject(to, MapObjectType.EMPTY);
            map.swap(this.getCoordinates(), to);
        }

    }

    public void setActive() {
        map.setObject(getCoordinates(), MapObjectType.ACTIVE);
    }

    public void setNonActive() {
        map.setObject(getCoordinates(), MapObjectType.ENEMY);
    }
}
