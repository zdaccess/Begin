package edu.school21.game.logic.symbols;

import static com.diogonunes.jcolor.Ansi.colorize;
import java.util.Properties;
import com.diogonunes.jcolor.Attribute;

import edu.school21.chaselogic.map.GameMap;

public class ColoredSymbol {
    protected String symbol = " ";
    protected Attribute color = Attribute.NONE();

    public ColoredSymbol(Properties properties, GameMap.MapObjectType type) {
        String name = type.toString().toLowerCase();
        String symProp = name + ".char";
        String colorProp = name + ".color";

        if (properties.getProperty(symProp).length() > 0) {
            symbol = String.valueOf(properties.getProperty(symProp).charAt(0));
        }

        if (properties.getProperty(colorProp).length() > 0) {
            setColor(properties.getProperty(colorProp).toUpperCase());
        }
    }

    public String getColoredSymbol() {
        return colorize(" " + symbol + " ", color);
    }

    private void setColor(String colorString) {
        switch (colorString) {
        case "WHITE":
            color = Attribute.WHITE_BACK();
            break;
        case "YELLOW":
            color = Attribute.YELLOW_BACK();
            break;
        case "RED":
            color = Attribute.RED_BACK();
            break;
        case "MAGENTA":
            color = Attribute.MAGENTA_BACK();
            break;
        case "GREEN":
            color = Attribute.GREEN_BACK();
            break;
        case "CYAN":
            color = Attribute.CYAN_BACK();
            break;
        case "BLUE":
            color = Attribute.BLUE_BACK();
            break;
        case "BLACK":
            color = Attribute.BLACK_BACK();
            break;
        default:
            color = Attribute.NONE();
            break;
        }
    }

}
