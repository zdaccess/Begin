package edu.school21.game.logic.userinterface;

import java.util.Properties;

import edu.school21.game.logic.map.MapWithObjects;
import edu.school21.game.logic.mode.GameMode;
import edu.school21.game.logic.symbols.ColoredSymbols;

public class MapPrinter {
    private MapWithObjects map = null;
    private Integer size = null;
    private ColoredSymbols symbols = null;

    public MapPrinter(MapWithObjects map, Properties properties) {
        this.map = map;
        size = map.size();
        symbols = new ColoredSymbols(properties);

    }

    public void print() {
        String result = "";

        for (int y = 0; y < size; ++y) {
            for (int x = 0; x < size; ++x) {
                result += symbols.chooseSymbol(map.getObject(x, y));
            }

            result += "\n";
        }

        if (GameMode.get().equals(GameMode.PRODUCTION)) {
            System.out.print("\033[H\033[2J");
            System.out.flush();
        }

        System.out.print(result);
    }

}
