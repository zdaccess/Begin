package edu.school21.ui;

import edu.school21.model.Shoot;
import edu.school21.model.Tank;
import edu.school21.net.ConnectionManager;
import javafx.animation.AnimationTimer;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import lombok.Setter;

import java.io.IOException;
import java.util.EnumMap;
import java.util.Map;
import java.util.Objects;

public class MainWindow {
    private static final double WINDOW_WIDTH = 751;
    private static final double WINDOW_HEIGHT = 751;
    private static final int STEP = 3;
    private static final String YELLOW_TANK_PNG = "yellow_tank.png";
    private static final String PURPLE_TANK_PNG = "purple_tank.png";

    private final Map<KeyCode, Boolean> keys = new EnumMap<>(KeyCode.class);

    @Setter
    private static ConnectionManager connectionManager;
    private Stage mainStage;
    private Tank player;
    private Tank enemy;
    private GraphicsContext gc;
    private AnimationTimer gameLoop;
    private String lastKey = " ";
    private Canvas canvas;

    private static final boolean DEBUG = true;

    public static void sendCommandToServer(String command) {
        if (DEBUG) System.out.println("Sending command: " + command);
        connectionManager.getOut().println(command);
    }

    public void show(Stage stage) {
        mainStage = stage;
        var group = setupStage(stage);
        setupKeyEvents(stage.getScene());

        player = new Tank(YELLOW_TANK_PNG, 347, 630, group, 15, 720, 13);
        enemy = new Tank(PURPLE_TANK_PNG, 347, 50, group, 586, 15, -13);
        renderGame();

        stage.show();

        gameLoop = createGameLoop();
        gameLoop.start();
    }

    private Group setupStage(Stage stage) {
        stage.setTitle("Tanks!");
        stage.setResizable(false);
        Group root = new Group();
        Scene scene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);
        scene.getStylesheets().addAll(Objects.requireNonNull(this.getClass().getResource("/style.css")).toExternalForm());
        stage.setScene(scene);

        canvas = new Canvas(WINDOW_WIDTH, WINDOW_HEIGHT);
        root.getChildren().add(canvas);
        gc = canvas.getGraphicsContext2D();
        gc.setFill(Color.DARKSLATEGRAY);
        return root;
    }

    private void setupKeyEvents(Scene scene) {
        scene.setOnKeyPressed(event -> {
            String code = event.getCode().toString();
            if (!code.equals("SPACE") || !lastKey.equals("SPACE")) {
                keys.put(event.getCode(), true);
            }
            lastKey = code;
        });
        scene.setOnKeyReleased(event -> keys.put(event.getCode(), false));
    }

    private AnimationTimer createGameLoop() {
        return new AnimationTimer() {
            private boolean canStartGame = false;

            @Override
            public void handle(long now) {
                if (connectionManager.isConnected()) canStartGame = true;
                if (canStartGame) updateGame();
            }

            private void updateGame() {
                try {
                    updatePlayer();
                    updateEnemy();
                    updateBullet();
                    checkGameOver();
                    renderGame();

                    Thread.sleep(20);
                } catch (InterruptedException | IOException e) {
                    System.err.println("Error in game loop: " + e.getMessage());
                }
            }
        };
    }

    private void renderGame() {
        gc.clearRect(0, 0, WINDOW_WIDTH, WINDOW_HEIGHT);
        gc.drawImage(new Image("field.jpg"), 0, 0);
        player.render(gc);
        enemy.render(gc);
    }

    private void updateEnemy() throws IOException {
        if (connectionManager.isConnected()) {
            String read = connectionManager.getIn().readLine();

            if (DEBUG) System.out.println("UpdateEnemy from server: " + read);

            if ("r".contains(read)) {
                enemy.moveLeft(STEP);
            } else if ("l".contains(read)) {
                enemy.moveRight(STEP);
            } else if ("shot".contains(read)) {
                shoot(enemy, player, enemy.getBoundary().getHeight());
            } else if ("enemy_left".contains(read)) {
                enemy.kill();
            }
        }
    }

    private void updateBullet() {
        if (keys.containsKey(KeyCode.SPACE)) {
            outShoot(player, enemy);
            keys.remove(KeyCode.SPACE);
            sendCommandToServer("shot");
        }
    }

    private void shoot(Tank shooter, Tank target, double correction) {
        new Shoot(shooter, "purple_bullet.png", target, gc, correction).start();
    }

    private void outShoot(Tank player1, Tank player2) {
        new Shoot(player1, "yellow_bullet.png", player2, gc, 0).start();
    }

    private void updatePlayer() {
        if (isPressed(KeyCode.D) || isPressed(KeyCode.RIGHT)) {
            player.moveRight(STEP);
            sendCommandToServer("r");
        } else if (isPressed(KeyCode.A) || isPressed(KeyCode.LEFT)) {
            player.moveLeft(STEP);
            sendCommandToServer("l");
        }
    }

    private boolean isPressed(KeyCode keyCode) {
        return keys.getOrDefault(keyCode, false);
    }

    private void checkGameOver() throws IOException {
        if (enemy.checkLeaveGame()) {
            gameOver("Enemy got scared and left the game!");
        } else if (enemy.isDead()) {
            gameOver("You win!");
        }

        if (player.isDead()) {
            gameOver("You lose!");
        }
    }

    private void gameOver(String resultText) throws IOException {
        gameLoop.stop();
        Stage gameOverStage = createGameOverStage();
        sendCommandToServer("game_over");
        displayGameStatistics(resultText);
        gameOverStage.show();
        connectionManager.close();
    }

    private void displayGameStatistics(String text) throws IOException {
        var in = connectionManager.getIn();
        var stats = in.readLine();

        while (!stats.contains("stat")) {
            stats = in.readLine();
        }

        if (DEBUG) System.out.println("Stat from server: " + stats);

        var info = stats.split(":");

        var endGc = canvas.getGraphicsContext2D();

        endGc.setFill(Color.WHITESMOKE);
        endGc.fillRect(0, 0, WINDOW_HEIGHT, WINDOW_WIDTH);

        DropShadow shadow = new DropShadow();
        shadow.setColor(Color.GRAY);
        shadow.setRadius(10);
        endGc.applyEffect(shadow);

        endGc.setFont(new Font("Verdana", 40));
        endGc.setFill(Color.BLACK);
        endGc.setTextAlign(TextAlignment.CENTER);
        endGc.fillText(text, WINDOW_HEIGHT / 2, 100);

        endGc.setFont(new Font("Arial", 24));
        double startX = 100;
        double startY = 200;
        double cellWidth = 200;
        double cellHeight = 80;

        String[] rowHeaders = {"Shots", "Hits", "Misses"};
        String[] columnHeaders = {"You", "Enemy"};

        endGc.setFill(Color.LIGHTGRAY);

        for (int i = 0; i <= rowHeaders.length; i++) {
            for (int j = 0; j <= columnHeaders.length; j++) {
                double x = startX + j * cellWidth;
                double y = startY + i * cellHeight;

                endGc.setFill(Color.LIGHTGRAY);
                endGc.fillRoundRect(x, y, cellWidth, cellHeight, 20, 20);
                endGc.setStroke(Color.DARKGRAY);
                endGc.strokeRoundRect(x, y, cellWidth, cellHeight, 20, 20);

                if (i == 0 && j > 0) {
                    endGc.setFill(Color.BLACK);
                    endGc.fillText(columnHeaders[j - 1], x + 90, y + 50);
                } else if (j == 0 && i > 0) {
                    endGc.setFill(Color.BLACK);
                    endGc.fillText(rowHeaders[i - 1], x + 70, y + 50);
                }
            }
        }

        if (info.length >= 6) {
            endGc.setFill(Color.BLACK);
            endGc.fillText(info[1], startX + cellWidth + 100, startY + 2 * cellHeight - 30);
            endGc.fillText(info[2], startX + cellWidth + 100, startY + 3 * cellHeight - 30);
            endGc.fillText(info[3], startX + cellWidth + 100, startY + 4 * cellHeight - 30);

            endGc.fillText(info[4], startX + 2 * cellWidth + 100, startY + 2 * cellHeight - 30);
            endGc.fillText(info[5], startX + 2 * cellWidth + 100, startY + 3 * cellHeight - 30);
            endGc.fillText(info[6], startX + 2 * cellWidth + 100, startY + 4 * cellHeight - 30);
        }
    }

    private Stage createGameOverStage() {
        Stage stage = new Stage();
        stage.setTitle("Tanks!");
        stage.setX(mainStage.getX());
        stage.setY(mainStage.getY());
        stage.setResizable(false);
        mainStage.close();

        Group root = new Group();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        canvas = new Canvas(WINDOW_WIDTH, WINDOW_HEIGHT);
        root.getChildren().add(canvas);
        return stage;
    }
}
