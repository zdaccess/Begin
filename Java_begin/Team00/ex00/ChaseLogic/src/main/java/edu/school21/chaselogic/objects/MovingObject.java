package edu.school21.chaselogic.objects;

import edu.school21.chaselogic.coordinates.Coordinates;
import edu.school21.chaselogic.map.GameMap;

public class MovingObject extends Coordinates {
    protected GameMap map = null;

    public MovingObject(Coordinates coordinates, GameMap map) {
        super(coordinates);
        this.map = map;
    }

}
