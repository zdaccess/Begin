package edu.school21.chaselogic.map;

import edu.school21.chaselogic.coordinates.Coordinates;

public class GameMap {
    public enum MapObjectType {
        GOAL, PLAYER, ENEMY, WALL, EMPTY, ACTIVE;
    }

    protected MapObjectType[][] map = null;
    protected Integer size = null;

    public void setObject(Coordinates coordinates, MapObjectType object) {
        map[coordinates.getY()][coordinates.getX()] = object;
    }

    public MapObjectType getObject(Coordinates coordinates) {
        return map[coordinates.getY()][coordinates.getX()];
    }

    public MapObjectType getObject(int x, int y) {
        return map[y][x];
    }

    public Integer size() {
        return size;
    }

    public void swap(Coordinates a, Coordinates b) {
        MapObjectType temp = getObject(a);

        setObject(a, getObject(b));
        setObject(b, temp);
    }

}
