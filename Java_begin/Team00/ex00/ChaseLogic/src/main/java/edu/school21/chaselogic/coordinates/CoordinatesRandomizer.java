package edu.school21.chaselogic.coordinates;

import java.util.Random;

import edu.school21.chaselogic.map.GameMap;

public class CoordinatesRandomizer {
    private int bounds = 0;
    private Random random = new Random();
    private GameMap map = null;

    public CoordinatesRandomizer(GameMap map) {
        this.map = map;
        bounds = map.size();
    }

    public Coordinates generate() {
        Coordinates coordinates = getRandom();

        while (map.getObject(coordinates) != null) {
            coordinates = getRandom();
        }

        return coordinates;
    }

    private Coordinates getRandom() {
        return new Coordinates(random.nextInt(bounds), random.nextInt(bounds));
    }

}
