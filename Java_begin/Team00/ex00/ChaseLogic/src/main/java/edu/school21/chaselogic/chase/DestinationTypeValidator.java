package edu.school21.chaselogic.chase;

import edu.school21.chaselogic.coordinates.Coordinates;
import edu.school21.chaselogic.map.GameMap;
import edu.school21.chaselogic.map.GameMap.MapObjectType;

public class DestinationTypeValidator {
    public GameMap map = null;

    public DestinationTypeValidator(GameMap map) {
        this.map = map;
    }

    public Coordinates validate(Coordinates destination) {
        Coordinates result = destination;
        MapObjectType type = null;

        try {
            type = map.getObject(destination);

            if (type.equals(MapObjectType.ENEMY)
                    || type.equals(MapObjectType.WALL)
                    || type.equals(MapObjectType.GOAL)) {
                result = null;
            }
        } catch (Exception e) {
            result = null;
        }

        return result;
    }

    public Coordinates isEmpty(Coordinates coordinates) {
        Coordinates result = null;
        MapObjectType type = null;

        try {
            type = map.getObject(coordinates);

            if (type.equals(MapObjectType.EMPTY)) {
                result = coordinates;
            }
        } catch (Exception e) {
            result = null;
        }

        return result;
    }

}
