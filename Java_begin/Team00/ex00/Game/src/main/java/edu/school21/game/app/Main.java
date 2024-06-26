package edu.school21.game.app;

import java.util.Properties;

import edu.school21.chaselogic.status.PlayerStatus;
import edu.school21.game.logic.map.MapWithObjects;
import edu.school21.game.logic.mode.GameMode;
import edu.school21.game.logic.parameters.GameArguments;
import edu.school21.game.logic.parameters.PropertiesFromFile;
import edu.school21.game.logic.userinterface.Controller;
import edu.school21.game.logic.userinterface.MapPrinter;

public class Main {
    public static void main(String[] args) {
        try {
            GameArguments arguments = new GameArguments(args);
            Properties props = new PropertiesFromFile(arguments.getMode())
                    .read();
            MapWithObjects map = new MapWithObjects(arguments);
            MapPrinter printer = new MapPrinter(map, props);
            Controller controller = new Controller();

            printer.print();

            while (true) {
                if (map.player().isSurrounded()) {
                    PlayerStatus.setLose();
                }

                if (!PlayerStatus.isEnd()) {
                    map.player().go(controller.chooseAction());
                }

                if (PlayerStatus.get().equals(PlayerStatus.CONTINUE)) {
                    if (GameMode.get().equals(GameMode.PRODUCTION)) {
                        map.enemies().chase();
                    } else {
                        PlayerStatus.setWatching();
                        map.enemies().chaseWithContirmation(controller,
                                printer);
                    }
                }

                if (GameMode.get().equals(GameMode.PRODUCTION)) {
                    printer.print();
                }

                if (PlayerStatus.isEnd()) {
                    break;
                }

            }

            if (GameMode.get().equals(GameMode.DEVELOP)) {
                printer.print();
            }

            System.out.println("You " + PlayerStatus.get());

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
