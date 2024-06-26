package edu.school21.game.logic.enemies;

import java.util.ArrayList;

import edu.school21.chaselogic.chase.Chaser;
import edu.school21.chaselogic.map.GameMap;
import edu.school21.chaselogic.objects.Enemy;
import edu.school21.chaselogic.objects.Player;
import edu.school21.chaselogic.status.PlayerStatus;
import edu.school21.game.logic.userinterface.Controller;
import edu.school21.game.logic.userinterface.MapPrinter;

public class EnemiesArray {
    private ArrayList<Enemy> enemies = null;
    private Chaser chaser = null;

    public EnemiesArray(Integer count, GameMap map, Player player) {
        enemies = new ArrayList<>(count);
        chaser = new Chaser(map, player);
    }

    public void add(Enemy enemy) {
        enemies.add(enemy);
    }

    public void chase() {
        for (Enemy enemy : enemies) {
            enemy.go(chaser.chooseDestionation(enemy));
        }
    }

    public void chaseWithContirmation(Controller joystick, MapPrinter printer) {
        int counter = 0;

        printer.print();

        while (counter < enemies.size()) {

            if (joystick.chooseAction().equals(Player.Action.WATCH)) {
                Enemy enemy = enemies.get(counter);

                enemy.go(chaser.chooseDestionation(enemy));
                enemy.setActive();
                printer.print();
                enemy.setNonActive();
                ++counter;
            }

            if (PlayerStatus.get().equals(PlayerStatus.LOSE)) {
                break;
            }

        }

        if (!PlayerStatus.get().equals(PlayerStatus.LOSE)) {
            PlayerStatus.setContinue();
        }
    }

}
