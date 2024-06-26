package edu.school21.game.logic.symbols;

import java.util.Properties;

import edu.school21.chaselogic.map.GameMap;
import edu.school21.game.logic.mode.GameMode;

public class ColoredSymbols {
    public ColoredSymbol enemy = null;
    public ColoredSymbol player = null;
    public ColoredSymbol goal = null;
    public ColoredSymbol wall = null;
    public ColoredSymbol empty = null;
    public ColoredSymbol active = null;

    public ColoredSymbols(Properties properties) {
        enemy = new ColoredSymbol(properties, GameMap.MapObjectType.ENEMY);
        player = new ColoredSymbol(properties, GameMap.MapObjectType.PLAYER);
        goal = new ColoredSymbol(properties, GameMap.MapObjectType.GOAL);
        wall = new ColoredSymbol(properties, GameMap.MapObjectType.WALL);
        empty = new ColoredSymbol(properties, GameMap.MapObjectType.EMPTY);

        if (GameMode.get().equals(GameMode.DEVELOP)) {
            active = new ColoredSymbol(properties,
                    GameMap.MapObjectType.ACTIVE);
        }

    }

    public String chooseSymbol(GameMap.MapObjectType object) {
        ColoredSymbol sym = null;

        switch (object) {
        case ENEMY:
            sym = enemy;
            break;
        case PLAYER:
            sym = player;
            break;
        case GOAL:
            sym = goal;
            break;
        case WALL:
            sym = wall;
            break;
        case EMPTY:
            sym = empty;
            break;
        case ACTIVE:
            sym = active;
            break;
        }

        return sym.getColoredSymbol();
    }

}
