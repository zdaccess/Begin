package edu.school21.game.logic.userinterface;

import java.util.Scanner;

import edu.school21.chaselogic.objects.Player.Action;
import edu.school21.chaselogic.status.PlayerStatus;
import edu.school21.game.logic.mode.GameMode;;

public class Controller {
    private Scanner scanner = new Scanner(System.in);

    public Action chooseAction() {
        Action action = Action.INVALID;

        while (action == Action.INVALID) {
            action = choose(scanChar());
        }

        return action;
    }

    private char scanChar() {
        char result = 0;
        String input = null;

        input = scanner.nextLine();

        if (input.length() > 0) {
            result = input.charAt(0);
        }

        return result;
    }

    private Action choose(char input) {
        Action action = null;

        switch (input) {
        case 'A':
        case 'a':
            action = Action.LEFT;
            break;
        case 'W':
        case 'w':
            action = Action.UP;
            break;
        case 'S':
        case 's':
            action = Action.DOWN;
            break;
        case 'D':
        case 'd':
            action = Action.RIGTH;
            break;
        case '8':
            action = Action.WATCH;
            if (GameMode.get().equals(GameMode.PRODUCTION)
                    || PlayerStatus.get().equals(PlayerStatus.CONTINUE)) {
                action = Action.INVALID;
            }
            break;
        case '9':
            PlayerStatus.setLose();
            action = Action.CLOSE;
            break;
        default:
            action = Action.INVALID;
            break;

        }

        return action;
    }

}
